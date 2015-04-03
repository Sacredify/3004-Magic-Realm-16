package ca.carleton.magicrealm.network;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.GUI.board.EntityBuilder;
import ca.carleton.magicrealm.Launcher;
import ca.carleton.magicrealm.control.Combat;
import ca.carleton.magicrealm.control.Sunrise;
import ca.carleton.magicrealm.control.Sunset;
import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.MeleeSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppServer implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(AppServer.class);

    public static final int MAX_ROUNDS = 28;

    public static final int MAX_PLAYERS = 1;

    private static final int SERVER_ID = 0;

    private int clientCount = 0;

    private Thread thread = null;

    private ServerSocket server = null;

    private ArrayList<ServerThread> clients = null;

    private TurnController turnController = null;

    private BoardModel boardModel;

    /**
     * The number of days passed in the game. 28 is the max.
     */
    private int currentDay = 1;

    public AppServer(int port) {
        try {
            this.server = new ServerSocket(port);
            this.server.setReuseAddress(true);
            this.clients = new ArrayList<ServerThread>();
            LOG.info("Starting game...");
            this.turnController = new TurnController();
            this.buildMap();
            this.start();
        } catch (final Exception exception) {
            LOG.error("Exception during server initialization.", exception);
            System.exit(-1);
        }
    }

    /**
     * Builds the map for this session.
     */
    private void buildMap() {
        LOG.info("Beginning map build process.");
        this.boardModel = new BoardModel();

        if (Launcher.CHEAT_MODE) {
            LOG.info("Starting cheat build.");
            ChitBuilder.cheatMode(this.boardModel);
        } else {
            ChitBuilder.placeChits(this.boardModel);
        }
        EntityBuilder.placeEntities(this.boardModel);
        LOG.info("Finished map build process.");
    }

    public void start() {
        if (this.thread == null) {
            this.thread = new Thread(this);
            this.thread.start();
            LOG.info("Game started.");
        }
    }

    //Handles input from the server threads
    public synchronized void handle(int ID, Object obj) {
        if (obj instanceof Message) {
            Message m = (Message) obj;
            LOG.info("Received a {} message from ID {}.", m.getMessageType(), ID);
            this.handleMessage(m);
        } else if (obj instanceof String) {
            LOG.info("Receive a string - {}.", obj);
        }
    }

    /**
     * Handles the game flow and logic between client and server.
     *
     * @param message the message to handle.
     */
    public void handleMessage(Message message) {
        switch (message.getMessageType()) {

            // Clients send the SELECT_CHARACTER message when they are done creating their character.
            case (Message.SELECT_CHARACTER):
                // Add the player to the board and create a new melee sheet for them.
                Player player = (Player) message.getPayload();
                this.getClientWithID(message.getSenderID()).setPlayer(player);
                this.boardModel.getClearingOfDwelling(player.getStartingLocation()).addEntity(player.getCharacter());
                this.boardModel.addPlayer(player);
                this.boardModel.createNewMeleeSheet(player);
                LOG.info("Added new player to the board.");
                // If all players have sent their characters, send the message to start birdsong. Else, forward
                // the message to other players so they know who picked what.
                if (this.turnController.incrementTurnCount() == MAX_PLAYERS) {
                    LOG.info("Starting SUNRISE phase [server only].");
                    Sunrise.doSunrise(this.boardModel, this.currentDay);
                    LOG.info("Starting BIRDSONG phase.");
                    this.broadcastMessage(SERVER_ID, new Message(SERVER_ID, Message.BIRDSONG_START, this.boardModel));
                } else {
                    this.broadcastMessage(SERVER_ID, message);
                }
                break;
            // Clients send the BIRDSONG_DONE message when they are done with entering their actions for daylight.
            case (Message.BIRDSONG_DONE):
                // If all players have sent the message, send the message to the first randomly picked player to start
                // daylight (execution of their phases).
                if (this.turnController.incrementTurnCount() == MAX_PLAYERS) {
                    LOG.info("Starting DAYLIGHT phase.");
                    this.turnController.createNewTurnOrder(this.clients);
                    final ServerThread nextClient = this.getClientWithID(this.turnController.getNextPlayer());
                    nextClient.send(new Message(SERVER_ID, Message.DAYLIGHT_START, this.boardModel));
                }
                break;
            // Clients send the DAYLIGHT_DONE message when they are done executing their phases client-side.
            case (Message.DAYLIGHT_DONE):
                // Update the board.
                this.boardModel = (BoardModel) message.getPayload();
                this.synchronize();
                // If all players have sent the message, start filling out melee sheets in clearings.
                if (this.turnController.incrementTurnCount() == MAX_PLAYERS) {
                    LOG.info("Starting SUNSET phase [server only].");
                    Sunset.doSunset(this.boardModel);
                    this.broadcastMessage(SERVER_ID, new Message(SERVER_ID, Message.SUNSET_UPDATE, this.boardModel));
                    LOG.info("Sent clients updated sunset map.");
                    LOG.info("Starting COMBAT phase.");
                    this.turnController.createNewTurnOrder(this.clients);
                    final ServerThread nextClient = this.getClientWithID(this.turnController.getNextPlayer());
                    nextClient.send(new Message(SERVER_ID, Message.COMBAT_FILL_OUT_MELEE_SHEET, this.boardModel));
                } else {
                    // Send the next client that it is their turn to go.
                    final ServerThread nextClient = this.getClientWithID(this.turnController.getNextPlayer());
                    nextClient.send(new Message(SERVER_ID, Message.DAYLIGHT_START, this.boardModel));
                }
                break;
            // Clients send the COMBAT_SEND_MELEE_SHEET when they are done filling out their melee sheets.
            case (Message.COMBAT_SEND_MELEE_SHEET):
                this.boardModel = (BoardModel) message.getPayload();
                this.synchronize();
                if (this.turnController.incrementTurnCount() == MAX_PLAYERS) {
                    LOG.info("Starting COMBAT_RESOLUTION phase.");
                    this.processCombatsForPlayers();
                    LOG.info("Starting FATIGUE_STEP phase.");
                    this.turnController.createNewTurnOrder(this.clients);
                    final ServerThread nextClient = this.getClientWithID(this.turnController.getNextPlayer());
                    // Only send the message if the player actually needs to.
                    if (nextClient.getPlayer().getCharacter().isWounded() || nextClient.getPlayer().getCharacter().isFatigued()) {
                        LOG.info("Player was wounded and must be sent fill out fatigue step.");
                        nextClient.send(new Message(SERVER_ID, Message.FATIGUE_FATIGUE_CHITS, this.boardModel));
                    } else {
                        // skip to next by forcing another call to this method
                        LOG.info("Skipping to next player, as the player wasn't wounded.");
                        this.handleMessage(new Message(SERVER_ID, Message.FATIGUE_SUBMIT_UPDATED, this.boardModel));
                    }
                } else {
                    // Send the next client the message its their turn to do combat
                    final ServerThread nextClient = this.getClientWithID(this.turnController.getNextPlayer());
                    nextClient.send(new Message(SERVER_ID, Message.COMBAT_FILL_OUT_MELEE_SHEET, this.boardModel));
                }
                break;
            case (Message.FATIGUE_SUBMIT_UPDATED):
                this.boardModel = (BoardModel) message.getPayload();
                this.synchronize();
                if (this.turnController.incrementTurnCount() == MAX_PLAYERS) {
                    this.currentDay += 1;
                    LOG.info("Starting GAME_OVER phase.");
                    if (this.isGameOver()) {
                        LOG.info("A month has passed. Game over.");
                        final ServerThread winnerThread = this.calculateWinner();
                        // TODO do winner stuff here...
                        this.shutDown();
                    } else {
                        LOG.info("Game is not yet over. Starting day {}.", this.currentDay);
                        LOG.info("Starting SUNRISE phase.");
                        Sunrise.doSunrise(this.boardModel, this.currentDay);
                        LOG.info("Starting BIRDSONG phase.");
                        this.broadcastMessage(SERVER_ID, new Message(SERVER_ID, Message.BIRDSONG_START, this.boardModel));
                    }
                } else {
                    // Send the next client the message its their turn to do combat
                    final ServerThread nextClient = this.getClientWithID(this.turnController.getNextPlayer());
                    // Only send the message if the player actually needs to.
                    if (nextClient.getPlayer().getCharacter().isWounded() || nextClient.getPlayer().getCharacter().isFatigued()) {
                        LOG.info("Player was wounded and must be sent fill out fatigue step.");
                        nextClient.send(new Message(SERVER_ID, Message.FATIGUE_FATIGUE_CHITS, this.boardModel));
                    } else {
                        // skip to next by forcing another call to this method
                        LOG.info("Skipping to next player, as the player wasn't wounded.");
                        this.handleMessage(message);
                    }
                }
                break;
            default:
                LOG.error("No matching message type found... we done borked it good...");
                break;
        }
    }

    private void processCombatsForPlayers() {
        final List<Player> players = this.clients.stream().map(ServerThread::getPlayer).collect(Collectors.toList());

        try {
            for (final Player player : players) {
                LOG.info("Starting combat resolution for {}.", player.getCharacter());
                final MeleeSheet playerSheet = this.boardModel.getMeleeSheet(player);
                final Entity target = playerSheet.getTarget();
                if (target == null) {
                    LOG.info("Player opted to not fight. Skipping their combat.");
                    continue;
                }
                final MeleeSheet targetSheet = this.boardModel.getMeleeSheet(target);

                if (playerSheet.hasFoughtToday() || targetSheet.hasFoughtToday()) {
                    LOG.info("Assumption that people can't fight twice. Skipping because either {} or {} has fought today.", playerSheet.getOwner(), targetSheet.getOwner());
                    continue;
                }

                if (target instanceof AbstractCharacter) {
                    // Combat between two characters
                    final Player otherPlayer = this.boardModel.getPlayerForCharacter((AbstractCharacter) target);
                    Combat.doCombat(this.boardModel, player, otherPlayer);
                } else if (target instanceof Denizen) {
                    // Combat between a character and a native or monster.
                    Combat.doCombat(this.boardModel, player, (Denizen) target);
                }
            }
        } catch (final NullPointerException exception) {
            LOG.error("Error with combat resolution. Something may have not been set. Instead of failing, we'll continue and hope it works.", exception);
        }

        // After combat is done
        LOG.info("Resetting melee sheets and player wound statuses after combat.");
        this.boardModel.getAllSheets().stream().forEach(MeleeSheet::resetSheet);
        players.stream().forEach(Combat::cleanup);
    }

    /**
     * Update references to the current ones stored by the board (which may be scrambled through serialization. Object graphs are hard man).
     */

    private void synchronize() {
        for (final ServerThread client : this.clients) {
            this.boardModel.getPlayers().stream().filter(player -> client.getPlayer().equals(player)).forEach(player -> {
                client.setPlayer(player);
                LOG.info("Updated client thread player with board version [{}].", client.getPlayer().getCharacter());
            });
        }
        for (final MeleeSheet sheet : this.boardModel.getAllSheets()) {
            sheet.synchronize(this.boardModel);
        }
        LOG.debug("Successfully synchronized melee sheets from the board.");
    }

    private boolean isGameOver() {
        return this.currentDay == MAX_ROUNDS;
    }

    private ServerThread calculateWinner() {


        return null;
    }

    /**
     * End the current game gracefully.
     */
    private void shutDown() {

    }

    private ServerThread getClientWithID(int ID) {
        for (ServerThread client : this.clients) {
            if (ID == client.getID()) {
                return client;
            }
        }
        return null;
    }

    @Override
    public void run() {
        try {
            while (true) {
                //If there is room in the game add client socket to the list of clients
                if (this.clientCount < MAX_PLAYERS) {
                    Socket clientSocket = this.server.accept();
                    this.clients.add(new ServerThread(this, clientSocket));
                    this.clients.get(this.clientCount).open();
                    this.clients.get(this.clientCount).start();
                    this.clientCount++;
                } else {
                    this.server.close();
                    break;
                }
            }
        } catch (IOException e) {
            LOG.error("Exception during server accept process.", e);
        }
    }

    /**
     * Broad a message to other clients on behalf of the given ID. The ID does not receive this message.
     *
     * @param ID      the id of the sender (0 is server)..
     * @param message the message to send.
     */
    public void broadcastMessage(int ID, Object message) {
        Message m = (Message) message;
        LOG.info("Broadcasting message {} to connected clients from ID {}.", m.getMessageType(), ID);
        this.clients.stream().filter(client -> client.getID() != ID).forEach(client -> client.send(m));
    }

}
