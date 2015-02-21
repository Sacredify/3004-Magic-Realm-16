package ca.carleton.magicrealm.Networking;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.impl.AwfulValley;
import ca.carleton.magicrealm.control.GameController;

import java.io.*;
import java.net.Socket;


public class AppClient implements Runnable {

    private int ID = 0;
    private Socket socket = null;
    private Thread thread = null;
    private ObjectOutputStream objOutStream = null;
    private ObjectInputStream objInputStream = null;
    private AppClient clnt = null;
    private GameController gameController;



    public AppClient(String serverName, int serverPort,GameController gameController) {

        try {
            this.socket = new Socket(serverName, serverPort);
            this.ID = socket.getLocalPort();
            this.gameController = gameController;
            System.out.println(ID + "Connected to Server:" + socket.getInetAddress());
            this.open();
            this.start();
        } catch (IOException ioe) {
            System.out.println("Could not connect to serever");
        }
    }


    public void write(Object msg) {
        try {
            objOutStream.writeObject(msg);
            objOutStream.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Object read() {
        Object obj = null;

        try {
            obj = objInputStream.read();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return obj;
    }

    private void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void sendMessage(String messageType,Object messageObject){

        System.out.print("MESSAGE TYPE BEING SENT: "+messageType + " SENT FROM ID:" + this.ID);
        Message m = new Message(this.ID,messageType,messageObject);
        write(m);
    }

    public void open() throws IOException {
        System.out.println(ID + ":Opening buffer streams");
        objOutStream = new ObjectOutputStream(socket.getOutputStream());
        objInputStream = new ObjectInputStream(socket.getInputStream());
        if (objInputStream == null) {
            System.out.println("Unable to Open Object Input Stream on Thread:" + ID);
        }
        else{
            System.out.println("Able to Open Object Input Stream on Thread:" + ID);
        }
        if (objOutStream == null) {
            System.out.println("Unable to Open Object Output Stream on Thread:" + ID);
        }
        else{
            System.out.println("Able to Open Object Output Stream on Thread:" + ID);
        }
    }


    @Override
    public void run() {
        System.out.println(ID + ": Client Started");
        Object obj = null;
        try {
            obj  = objInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (obj != null) {
            try {
                gameController.handleMessage(obj);
                obj = objInputStream.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (obj == null) {
                System.out.println("Disconnected From Server");
                break;
            }
        }
    }

    public int getId(){
        return ID;
    }

/*
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        AppClient clnt = new AppClient(Config.DEFAULT_HOST, Config.DEFAULT_HOST_PORT);
        AwfulValley a = new AwfulValley();
        Clearing c;
        c = new Clearing(a, 1);
        clnt.write(c);
        clnt.write("Elo dalin");

    }*/

}
