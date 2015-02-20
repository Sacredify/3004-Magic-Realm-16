package ca.carleton.magicrealm.Networking;

import java.io.*;
import java.net.Socket;


public class ServerThread extends Thread {
    private int ID = -1;
    private Socket socket = null;
    private AppServer server = null;
    private int gameNumber = -1;
    private ObjectOutputStream objOutStream = null;
    private ObjectInputStream objInputStream = null;
    private boolean done = false;

    public ServerThread(AppServer server, Socket socket) {
        super();
        this.server = server;
        this.socket = socket;
        this.ID = socket.getPort();
    }



    public void send(Object msg) {
        try {
            objOutStream.writeObject(msg);
            objOutStream.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void run() {

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
                server.handle(ID,obj);
                obj = objInputStream.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (obj == null) {
                System.out.println("Cliend dropped");
                break;
            }
        }
    }


    public void open() throws IOException {
        System.out.println(ID + ":Opening buffer streams");
        objOutStream= new ObjectOutputStream(socket.getOutputStream());
        objInputStream = new ObjectInputStream(socket.getInputStream());
        objOutStream.flush();
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

    public int getID() {
        // TODO Auto-generated method stub
        return ID;
    }

}
