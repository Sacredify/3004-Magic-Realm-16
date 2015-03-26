package ca.carleton.magicrealm.Networking;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.GUI.board.EntityBuilder;
import ca.carleton.magicrealm.control.Sunrise;
import ca.carleton.magicrealm.game.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
        } catch (IOException e) {
            LOG.error("Exception during server initialization.", e);
        }
    }

    /**
     * Builds the map for this session.
     */
    private void buildMap() {
        LOG.info("Beginning map build process.");
        this.boardModel = new BoardModel();
        ChitBuilder.placeChits(this.boardModel);
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
                    Sunrise.doSunrise(this.boardModel, this.currentDay);
                    Message toSend = new Message(SERVER_ID, Message.BIRDSONG_START, this.boardModel);
                    this.broadcastMessage(SERVER_ID, toSend);
                } else {
                    this.broadcastMessage(SERVER_ID, message);
                }
                break;
            // Clients send the BIRDSONG_DONE message when they are done with entering their actions for daylight.
            case (Message.BIRDSONG_DONE):
                // If all players have sent the message, send the message to the first randomly picked player to start
                // daylight (execution of their phases).
                if (this.turnController.incrementTurnCount() == MAX_PLAYERS) {
                    this.turnController.createNewTurnOrder(this.clients);
                    int nextID = this.turnController.getNextPlayer();
                    ServerThread nextClient = this.getClientWithID(nextID);
                    Message toSend = new Message(SERVER_ID, Message.DAYLIGHT_START, this.boardModel);
                    nextClient.send(toSend);
                }
                break;
            // Clients send the DAYLIGHT_DONE message when they are done executing their phases client-side.
            case (Message.DAYLIGHT_DONE):
                // Update the board.
                this.boardModel = (BoardModel) message.getPayload();
                this.updateFromBoard();
                // If all players have sent the message, start COMBAT in clearings.
                if (this.turnController.incrementTurnCount() == MAX_PLAYERS) {
                    this.turnController.createNewTurnOrder(this.clients);
                    ServerThread nextClient = this.getClientWithID(this.turnController.getNextPlayer());
                    Message toSend = new Message(SERVER_ID, Message.START_COMBAT_IN_CLEARING, this.boardModel);
                    nextClient.send(toSend);
                } else {
                    // Send the next client to go that it is their turn to go.
                    int nextID = this.turnController.getNextPlayer();
                    ServerThread nextClient = this.getClientWithID(nextID);
                    Message msg = new Message(SERVER_ID, Message.DAYLIGHT_START, this.boardModel);
                    nextClient.send(msg);
                }
                break;
            // Clients send the DONE_COMBAT_IN_CLEARING message when they have resolved combat for their character in their clearing.
            case (Message.DONE_COMBAT_IN_CLEARING):
                // Update the board
                this.boardModel = (BoardModel) message.getPayload();
                this.updateFromBoard();
                // If all players have sent the message (done with combat), start a new day.
                if (this.turnController.incrementTurnCount() == MAX_PLAYERS) {
                    this.currentDay++;
                    Sunrise.doSunrise(this.boardModel, this.currentDay);
                    Message toSend = new Message(SERVER_ID, Message.BIRDSONG_START, this.boardModel);
                    this.broadcastMessage(SERVER_ID, toSend);
                } else {
                    // Send the next client to go that it is their turn to go.
                    int nextID = this.turnController.getNextPlayer();
                    ServerThread nextClient = this.getClientWithID(nextID);
                    Message msg = new Message(SERVER_ID, Message.START_COMBAT_IN_CLEARING, this.boardModel);
                    nextClient.send(msg);
                }
                break;
            default:
                LOG.error("No matching message type found... we done borked it good...");
                break;
        }
    }

    private void updateFromBoard() {
        for (final ServerThread client : this.clients) {
            this.boardModel.getPlayers().stream().filter(player -> client.getPlayer().equals(player)).forEach(player -> {
                client.setPlayer(player);
                LOG.info("Updated client thread player with board version [{}].", client.getPlayer().getCharacter());
            });
        }
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
                } else
                    this.server.close();

            }
        } catch (IOException e) {
            LOG.error("Exception during server accept process.", e);
        }
    }

    /**
     * Broad a message to other clients on behalf of the given ID. The ID does not receieve this message.
     *
     * @param ID      the id of the sender (0 is server)..
     * @param message the message to send.
     */
    public void broadcastMessage(int ID, Object message) {
        Message m = (Message) message;
        LOG.info("Broadcasting message {} to connected clients from ID {}.", m.getMessageType(), ID);
        for (ServerThread client : this.clients) {
            if (client.getID() != ID)
                client.send(m);
        }
    }

}
