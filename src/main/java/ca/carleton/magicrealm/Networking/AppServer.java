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

    int clientCount = 0;
    public static final int MAX_PLAYERS = 2;
    int gameCount = 0;
    private Thread thread = null;
    private ServerSocket server = null;
    private ArrayList<ServerThread> clients = null;
    private TurnController turnController = null;
    private BoardGUIModel boardModel;

    public AppServer(int port) {
        try {
            this.server = new ServerSocket(port);
            this.server.setReuseAddress(true);
            this.clients = new ArrayList<ServerThread>();
            this.turnController = new TurnController();
            this.buildMap();

            this.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

    //Begins a new round of turns(DAYLIGHT)
    public void beginPhase() {
        this.turnController.createNewTurnOrder(this.clients);
        this.alertPlayerNextTurn();
    }

    //This function is called every time a Message.TURN_FINISHED is recieved
    //STILL NEED TO IMPLEMENT SWORDSMAN FUNCTIONALITY
    public void alertPlayerNextTurn() {
        int nextID;
        ServerThread nextClient;
        nextID = this.turnController.getNextPlayer();
        this.broadcastMessage(nextID, "TURN_PLAYER:" + nextID);
        nextClient = this.getClientWithID(nextID);
        nextClient.send(Message.TURN_ALERT);
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
            System.out.println("AppServer:This is a Message Object");
            Message m = (Message) obj;
            System.out.println("This is a :" + m.messageType + " message");
            this.handleMessage(m, ID, obj);
        } else if (obj instanceof String) {
            System.out.println("This is a string");
            System.out.println("Message String Contents: " + obj);
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
                    // Send map data, start birdsong.
                    this.sendMap();
                    this.broadcastMessage(0, new Message(0, Message.BIRDSONG_START, obj));
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
                    this.broadcastMessage(0, new Message(0, Message.SUNSET_START, this.boardModel));

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

        for (int i = 0; i < this.clients.size(); i++) {
            if (ID == this.clients.get(i).getID()) {
                return this.clients.get(i);
            }
        }

        return null;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            while (true) {
                //Accept client socket
                Socket clientSocket = this.server.accept();
                //If there is room in the game add client socket to the list of clients
                if (this.clientCount < this.MAX_PLAYERS) {
                    this.clients.add(new ServerThread(this, clientSocket));
                    this.clients.get(this.clientCount).open();
                    this.clients.get(this.clientCount).start();
                    this.clientCount++;
                }

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    //Broadcasts a message to all of the clients that did not send it
    public void broadcastMessage(int ID, Object message) {
        Message m = (Message) message;
        System.out.println("This is the message being broad-casted: " + m.getMessageType());
        for (int i = 0; i < this.clients.size(); i++) {
            if (this.clients.get(i).getID() != ID)
                this.clients.get(i).send(message);

        }

    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        AppServer srvr = new AppServer(Config.DEFAULT_HOST_PORT);
        srvr.start();
    }

}
