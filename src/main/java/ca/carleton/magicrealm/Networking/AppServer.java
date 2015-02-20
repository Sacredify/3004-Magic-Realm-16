package ca.carleton.magicrealm.Networking;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AppServer implements Runnable {
    int clientCount = 0;
    private final int MAX_PLAYERS = 3;
    int gameCount = 0;
    private Thread thread = null;
    private ServerSocket server = null;
    private ArrayList<ServerThread> clients = null;

    public AppServer(int port) {
        try {
            server = new ServerSocket(port);
            server.setReuseAddress(true);
            clients = new ArrayList<ServerThread>();
            start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    //Handles input from the server threads
    public synchronized void handle(int ID, Object obj) {
        if("ca.carleton.magicrealm.GUI.tile.Clearing" == obj.getClass().getName()) {
            System.out.println("This is a clearing");
        }
        else if("java.lang.String" == obj.getClass().getName()){
            System.out.println("This is a string");
        }
        broadcastMessage(ID, obj);
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
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getID() != ID) {
                clients.get(i).send(message);
            }
        }

    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        AppServer srvr = new AppServer(Config.DEFAULT_HOST_PORT);
        srvr.start();
    }

}
