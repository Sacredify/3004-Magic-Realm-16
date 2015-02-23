package ca.carleton.magicrealm.Networking;

import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
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

    private int clientCount = 0;

    private Thread thread = null;

    private ServerSocket server = null;

    private ArrayList<ServerThread> clients = null;

    private TurnController turnController = null;

    private BoardGUIModel boardModel;

    /**
     * The number of days passed in the game. 28 is the max.
     */
    private int currentDay = 1;

    public AppServer(int port) {
        try {
            this.server = new ServerSocket(port);
            this.server.setReuseAddress(true);
            this.clients = new ArrayList<ServerThread>();
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
        this.boardModel = new BoardGUIModel();
        ChitBuilder.placeChits(this.boardModel);
    }

    /**
     * Broadcast the map to the clients.
     */
    private void sendMap() {
        Message m = new Message(0, Message.SET_MAP, this.boardModel);
        this.broadcastMessage(0, m);
    }

    public void start() {
        if (this.thread == null) {
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    //Handles input from the server threads
    public synchronized void handle(int ID, Object obj) {
        if (obj instanceof Message) {
            Message m = (Message) obj;
            LOG.info("Received a {} message from ID {}.", m.getMessageType(), ID);
            this.handleMessage(m, ID, obj);
        } else if (obj instanceof String) {
            LOG.info("Receive a string - {}.", obj);
        }
    }

    public void handleMessage(Message m, int ID, Object obj) {
        switch (m.getMessageType()) {
            case (Message.SELECT_CHARACTER):
                Player player = (Player) m.getMessageObject();
                player.setCurrentClearing(this.boardModel.getStartingLocation());
                this.boardModel.addPlayer(player);
                this.broadcastMessage(0, m);
                if (this.turnController.incrementTurnCount() == MAX_PLAYERS) {
                    // Start birdsong + send map as payload.
                    this.broadcastMessage(0, new Message(0, Message.BIRDSONG_START, this.boardModel));
                }
                break;
            case (Message.BIRDSONG_DONE):
                if (this.turnController.incrementTurnCount() == MAX_PLAYERS) {
                    this.turnController.createNewTurnOrder(this.clients);
                    int nextID = this.turnController.getNextPlayer();
                    ServerThread nextClient = this.getClientWithID(nextID);
                    Message msg = new Message(0, Message.DAYLIGHT_START, this.boardModel);
                    nextClient.send(msg);
                }
                break;
            case (Message.DAYLIGHT_DONE):
                this.boardModel = (BoardGUIModel) m.getMessageObject();
                if (this.turnController.incrementTurnCount() == MAX_PLAYERS) {
                    /*  For testing, and for now. Is sunset for all players simultaneously? Idk.

                    this.turnController.createNewTurn();
                    int nextID = this.turnController.getNextPlayer();
                    ServerThread nextClient = this.getClientWithID(nextID);
                    Message msg = new Message(0, Message.SUNSET_START, this.boardModel);
                    nextClient.send(msg);
                    this.turnController.incrementTurnCount();
                    */
                    //TODO Temporary loop to see if we can get it to keep moving.
                    this.currentDay++;
                    this.broadcastMessage(0, new Message(0, Message.BIRDSONG_START, this.boardModel));

                } else {
                    // Send the next client to go that it is their turn to go.
                    int nextID = this.turnController.getNextPlayer();
                    ServerThread nextClient = this.getClientWithID(nextID);
                    Message msg = new Message(0, Message.DAYLIGHT_START, this.boardModel);
                    nextClient.send(msg);
                }
                break;
            case (Message.MOVE):
                this.handleMoveMessage(m, ID);
                break;
            default:
                // this.broadcastMessage(ID, obj);
                break;
        }
    }


    public void handleMoveMessage(Message m, int ID) {
        this.boardModel = (BoardGUIModel) m.getMessageObject();
        this.broadcastMessage(ID, this.boardModel);
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
                }
                else
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
                client.send(message);
        }
    }

    public static void main(String[] args) {
        AppServer server = new AppServer(Config.DEFAULT_HOST_PORT);
        server.start();
    }

}
