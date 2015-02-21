package ca.carleton.magicrealm.Networking;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AppServer implements Runnable {
    int clientCount = 0;
    private final int MAX_PLAYERS = 6;
    int gameCount = 0;
    private Thread thread = null;
    private ServerSocket server = null;
    private ArrayList<ServerThread> clients = null;
    private TurnController turnController = null;

    public AppServer(int port) {
        try {
            server = new ServerSocket(port);
            server.setReuseAddress(true);
            clients = new ArrayList<ServerThread>();
            this.turnController = new TurnController();
            start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    //Begins a new round of turns(DAYLIGHT)
    public void beginPhase(){
        turnController.createNewTurn(clients);
        alertPlayerNextTurn();
    }

    //This function is called every time a Message.TURN_FINISHED is recieved
    //STILL NEED TO IMPLEMENT SWORDSMAN FUNCTIONALITY
    public void alertPlayerNextTurn(){
        int nextID;
        ServerThread nextClient;
        nextID = turnController.getNextPlayer();
        broadcastMessage(nextID,"TURN_PLAYER:"+nextID);
        nextClient = getClientWithID(nextID);
        nextClient.send(Message.TURN_ALERT);
    }
    
    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    //Handles input from the server threads
    public synchronized void handle(int ID, Object obj) {
        if("ca.carleton.magicrealm.Networking.Message" == obj.getClass().getName()) {
            System.out.println("AppServer:This is a Message Object");
            Message m = (Message)obj;
            System.out.println("This is a :"+ m.messageType + " message");
            handleMessage(m,ID,obj);
        }
        else if("java.lang.String" == obj.getClass().getName()){
            System.out.println("This is a string");
            System.out.println("Message String Contents: " + obj);
        }
    }

    public void handleMessage(Message m,int ID, Object obj){
        broadcastMessage(ID, obj);
        switch(m.getMessageType()){
            case(Message.SELECT_CHARACTER):

                if(turnController.incrementTurnCount()==6){
                    Message newMessage = new Message(0,Message.ALL_PARTICIPATED,obj);
                    broadcastMessage(0, newMessage);
                }
                break;

            default:
                break;
        }


    }





    private ServerThread getClientWithID(int ID) {

        for (int i = 0; i < clients.size(); i++) {
            if (ID == clients.get(i).getID()) {
                return clients.get(i);
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
                Socket clientSocket = server.accept();
                //If there is room in the game add client socket to the list of clients
                if (clientCount < MAX_PLAYERS) {
                    clients.add(new ServerThread(this, clientSocket));
                    clients.get(clientCount).open();
                    clients.get(clientCount).start();
                    clientCount++;
                }

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    //Broadcasts a message to all of the clients that did not send it
    public void broadcastMessage(int ID, Object message) {
        Message m = (Message)message;
        for (int i = 0; i < clients.size(); i++) {
            System.out.print("This is the message being broadcasted:"+ m.getMessageType());
            if (clients.get(i).getID() != ID)
                clients.get(i).send(message);

        }

    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        AppServer srvr = new AppServer(Config.DEFAULT_HOST_PORT);
        srvr.start();
    }

}
