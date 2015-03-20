package ca.carleton.magicrealm.Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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


    public void send(Message msg) {
        try {
            this.objOutStream.reset();
            this.objOutStream.writeObject(msg);
            this.objOutStream.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void run() {
        Object obj = null;
        do {
            try {
                obj = this.objInputStream.readObject();
                this.server.handle(this.ID, obj);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (obj != null);
    }


    public void open() throws IOException {
        System.out.println(this.ID + ":Opening buffer streams");
        this.objOutStream = new ObjectOutputStream(this.socket.getOutputStream());
        this.objInputStream = new ObjectInputStream(this.socket.getInputStream());
        this.objOutStream.flush();
        if (this.objInputStream == null) {
            System.out.println("Unable to Open Object Input Stream on Thread:" + this.ID);
        } else {
            System.out.println("Able to Open Object Input Stream on Thread:" + this.ID);
        }
        if (this.objOutStream == null) {
            System.out.println("Unable to Open Object Output Stream on Thread:" + this.ID);
        } else {
            System.out.println("Able to Open Object Output Stream on Thread:" + this.ID);
        }
    }

    public int getID() {
        // TODO Auto-generated method stub
        return this.ID;
    }

}
