package ca.carleton.magicrealm.Networking;

import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.game.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AppServer implements Runnable {

    int clientCount = 0;
    private static final int MAX_PLAYERS = 2;
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
        this.turnController.createNewTurn(this.clients);
        this.alertPlayerNextTurn();
    }

    //This function is called every time a Message.TURN_FINISHED is received
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
        this.broadcastMessage(ID, obj);
        switch (m.getMessageType()) {
            case (Message.SELECT_CHARACTER):

                final Player player = (Player) m.getMessageObject();
                player.setCurrentClearing(this.boardModel.getStartingLocation());
                this.boardModel.getPlayers().add(player);

                if (this.turnController.incrementTurnCount() == MAX_PLAYERS) {
                    Message newMessage = new Message(0, Message.ALL_PARTICIPATED, obj);
                    this.broadcastMessage(0, newMessage);
                    this.sendMap();
                    this.startBirdSong();
                }

                break;
            default:
                break;
        }
    }

    /**
     * Starts the birdsong phase for the clients.
     */
    private void startBirdSong() {
        this.broadcastMessage(0, new Message(0, Message.BIRDSONG, null));
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
                if (this.clientCount < MAX_PLAYERS) {
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


    /**
     * Send a message to the other clients.
     *
     * @param ID      the client sending the message.
     * @param message the message.
     */
    public void broadcastMessage(int ID, Object message) {
        Message m = (Message) message;
        for (int i = 0; i < this.clients.size(); i++) {
            System.out.println("This is the message being broad-casted: " + m.getMessageType());
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
