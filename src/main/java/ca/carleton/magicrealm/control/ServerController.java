package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.GUI.board.EntityBuilder;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.Launcher;
import ca.carleton.magicrealm.game.GameResult;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.MeleeSheet;
import ca.carleton.magicrealm.network.ServerNetwork;
import ca.carleton.magicrealm.network.Message;
import ca.carleton.magicrealm.network.ServerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.stream.Collectors;

/**
 *  Main logic class for the Server.
 *
 * Created with IntelliJ IDEA.
 * Date: 06/04/2015
 * Time: 4:14 PM
 */
public class ServerController {

    private static final Logger LOG = LoggerFactory.getLogger(ServerController.class);

    public static final int DEFAULT_MAX_PLAYERS = 1;

    public static final int DEFAULT_GAME_LENGTH = 28;

    // For lack of a better option, include it here too.
    private static final int SERVER_ID = 0;

    private final ServerNetwork networkConnection;

    private final TurnController turnController;

    // DO NOT set this here. Set through command or with the default_max above.
    private final int maxPlayers;

    private final int maxDays;

    private BoardModel boardModel;


    /**
     * The number of days passed in the game. 28 is the max.
     */
    private int currentDay = 1;

    public ServerController(final ServerNetwork networkConnection, final int maxPlayers, final int maxDays) {
        LOG.info("Setting network connection...");
        this.networkConnection = networkConnection;
        LOG.info("Starting game setup...");
        this.maxPlayers = maxPlayers;
        this.maxDays = maxDays;
        this.turnController = new TurnController(this);
        this.buildMap();
        LOG.info("Starting game...");
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
                this.networkConnection.getClientWithID(message.getSenderID()).setPlayer(player);
                this.boardModel.getClearingOfDwelling(player.getStartingLocation()).addEntity(player.getCharacter());
                this.boardModel.addPlayer(player);
                this.boardModel.createNewMeleeSheet(player);
                LOG.info("Added new player to the board.");
                // If all players have sent their characters, send the message to start birdsong. Else, forward
                // the message to other players so they know who picked what.
                if (this.turnController.incrementTurnCount() == this.networkConnection.getClientCount()) {
                    LOG.info("Starting SUNRISE phase [server only].");
                    Sunrise.doSunrise(this.boardModel, this.currentDay);
                    LOG.info("Starting BIRDSONG phase.");
                    this.networkConnection.broadcastMessage(SERVER_ID, new Message(SERVER_ID, Message.BIRDSONG_START, this.boardModel));
                } else {
                    this.networkConnection.broadcastMessage(SERVER_ID, message);
                }
                break;
            // Clients send the BIRDSONG_DONE message when they are done with entering their actions for daylight.
            case (Message.BIRDSONG_DONE):
                // If all players have sent the message, send the message to the first randomly picked player to start
                // daylight (execution of their phases).
                if (this.turnController.incrementTurnCount() == this.networkConnection.getClientCount()) {
                    LOG.info("Starting DAYLIGHT phase.");
                    this.turnController.createNewTurnOrder(this.networkConnection.getClients(), "DAYLIGHT_EXECUTION");
                    final ServerThread nextClient = this.networkConnection.getClientWithID(this.turnController.getNextPlayer());
                    nextClient.send(new Message(SERVER_ID, Message.DAYLIGHT_START, this.boardModel));
                }
                break;
            // Clients send the DAYLIGHT_DONE message when they are done executing their phases client-side.
            case (Message.DAYLIGHT_DONE):
                // Update the board.
                this.boardModel = (BoardModel) message.getPayload();
                this.synchronize();
                // If all players have sent the message, start filling out melee sheets in clearings.
                if (this.turnController.incrementTurnCount() == this.networkConnection.getClientCount()) {
                    LOG.info("Starting SUNSET phase [server only].");
                    Sunset.doSunset(this.boardModel);
                    this.networkConnection.broadcastMessage(SERVER_ID, new Message(SERVER_ID, Message.SUNSET_UPDATE, this.boardModel));
                    LOG.info("Sent clients updated sunset map.");
                    LOG.info("Starting COMBAT phase.");
                    this.turnController.createNewTurnOrder(this.networkConnection.getClients(), "FILL_OUT_MELEE_SHEETS");
                    final ServerThread nextClient =this.networkConnection.getClientWithID(this.turnController.getNextPlayer());
                    nextClient.send(new Message(SERVER_ID, Message.COMBAT_FILL_OUT_MELEE_SHEET, this.boardModel));
                } else {
                    // Send the next client that it is their turn to go.
                    final ServerThread nextClient = this.networkConnection.getClientWithID(this.turnController.getNextPlayer());
                    nextClient.send(new Message(SERVER_ID, Message.DAYLIGHT_START, this.boardModel));
                }
                break;
            // Clients send the COMBAT_SEND_MELEE_SHEET when they are done filling out their melee sheets.
            case (Message.COMBAT_SEND_MELEE_SHEET):
                this.boardModel = (BoardModel) message.getPayload();
                this.synchronize();
                if (this.turnController.incrementTurnCount() == this.networkConnection.getClientCount()) {
                    LOG.info("Starting COMBAT_RESOLUTION phase.");
                    Combat.process(this.networkConnection.getClients().stream().map(ServerThread::getPlayer).collect(Collectors.toList()), this.boardModel);
                    LOG.info("Starting FATIGUE_STEP phase.");
                    this.turnController.createNewTurnOrder(this.networkConnection.getClients(), "FATIGUE_STEP");
                    final ServerThread nextClient = this.networkConnection.getClientWithID(this.turnController.getNextPlayer());
                    // Only send the message if the player actually needs to.
                    if (nextClient.getPlayer().getCharacter().isWounded() || nextClient.getPlayer().getCharacter().isFatigued()) {
                        LOG.info("Player was wounded and must be sent fill out fatigue step.");
                        nextClient.send(new Message(SERVER_ID, Message.FATIGUE_FATIGUE_CHITS, this.boardModel));
                    } else {
                        // skip to next by forcing another call to this method
                        LOG.info("Skipping to next player, as the player wasn't wounded or fatigued.");
                        this.handleMessage(new Message(SERVER_ID, Message.FATIGUE_SUBMIT_UPDATED, this.boardModel));
                    }
                } else {
                    // Send the next client the message its their turn to do combat
                    final ServerThread nextClient = this.networkConnection.getClientWithID(this.turnController.getNextPlayer());
                    nextClient.send(new Message(SERVER_ID, Message.COMBAT_FILL_OUT_MELEE_SHEET, this.boardModel));
                }
                break;
            case (Message.FATIGUE_SUBMIT_UPDATED):
                this.boardModel = (BoardModel) message.getPayload();
                this.synchronize();
                if (this.turnController.incrementTurnCount() == this.networkConnection.getClientCount()) {
                    LOG.info("Cleaning up combat data for this day.");
                    this.boardModel.getPlayers().stream().forEach(Combat::cleanup);
                    LOG.info("Starting GAME_OVER phase.");
                    if (this.isGameOver()) {
                        LOG.info("MAX_DAYS has been reached. Game over.");
                        final Map<ServerThread, GameResult> results = EndGame.calculateFinalScores(this.networkConnection.getClients());
                        for (final Map.Entry<ServerThread, GameResult> result : results.entrySet()) {
                            result.getKey().send(new Message(SERVER_ID, Message.GAME_OVER, result.getValue()));
                        }
                        LOG.info("Sent all game over messages.");
                        this.shutDown();
                    } else {
                        this.currentDay += 1;
                        LOG.info("Game is not yet over. Starting day {}.", this.currentDay);
                        LOG.info("Starting SUNRISE phase.");
                        Sunrise.doSunrise(this.boardModel, this.currentDay);
                        LOG.info("Starting BIRDSONG phase.");
                        this.networkConnection.broadcastMessage(SERVER_ID, new Message(SERVER_ID, Message.BIRDSONG_START, this.boardModel));
                    }
                } else {
                    // Send the next client the message its their turn to do combat
                    final ServerThread nextClient = this.networkConnection.getClientWithID(this.turnController.getNextPlayer());
                    // Only send the message if the player actually needs to.
                    if (nextClient.getPlayer().getCharacter().isWounded() || nextClient.getPlayer().getCharacter().isFatigued()) {
                        LOG.info("Player was wounded and must be sent fill out fatigue step.");
                        nextClient.send(new Message(SERVER_ID, Message.FATIGUE_FATIGUE_CHITS, this.boardModel));
                    } else {
                        // skip to next by forcing another call to this method
                        LOG.info("Skipping fatigue step to next player, as the player wasn't wounded or fatigued.");
                        this.handleMessage(message);
                    }
                }
                break;
            default:
                LOG.error("No matching message type found... we done borked it good...");
                break;
        }
    }

    /**
     * Update references to the current ones stored by the board (which may be scrambled through serialization. Object graphs are hard man).
     */

    private void synchronize() {
        for (final ServerThread client : this.networkConnection.getClients()) {
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
        return this.currentDay == this.maxDays;
    }

    /**
     * End the current game gracefully.
     */
    private void shutDown() {
        LOG.info("Starting shutdown procedure - server will shut down once all clients have disconnected.");
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

    /**
     * Cleanup resources for a player from the game, effectively removing them
     *
     * @param player the player.
     */
    public void cleanUpPlayer(final Player player) {
        if (player != null) {
            if (player.getCharacter() != null) {
                final Clearing clearing = this.boardModel.getClearingForPlayer(player);
                clearing.getEntities().remove(player.getCharacter());
                LOG.info("Removed player from their clearing.");
            }
            this.boardModel.getPlayers().remove(player);
            LOG.info("Removed player from the list of players. Cleanup done.");
        } else {
            LOG.info("No player to cleanup.");
        }
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public ServerNetwork getNetworkConnection() {
        return this.networkConnection;
    }
}
