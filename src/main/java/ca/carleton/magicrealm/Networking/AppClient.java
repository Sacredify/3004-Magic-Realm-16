package ca.carleton.magicrealm.Networking;

import ca.carleton.magicrealm.control.GameController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class AppClient implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(AppClient.class);

    private int ID = 0;

    private Socket socket = null;

    private Thread thread = null;

    private ObjectOutputStream objOutStream = null;

    private ObjectInputStream objInputStream = null;

    private GameController gameController;

    public AppClient(String serverName, int serverPort, GameController gameController) {

        try {
            this.socket = new Socket(serverName, serverPort);
            this.ID = this.socket.getLocalPort();
            this.gameController = gameController;
            LOG.info("Successfully connected to the server. Details: {}.", this.socket.getInetAddress());
            this.open();
            this.start();
        } catch (IOException ioe) {
            LOG.error("Could not connect to the server. Please ensure the server is available. Reason --> {}.", ioe.getMessage());
            System.exit(0);
        }
    }

    public void write(Object msg) {
        try {
            this.objOutStream.writeObject(msg);
            this.objOutStream.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void start() {
        if (this.thread == null) {
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    public void sendMessage(String messageType, Object messageObject) {

        Message m = new Message(this.ID, messageType, messageObject);
        this.write(m);
        LOG.info("[ID {}] Sent {} message to the server.", this.ID, messageType);
    }

    public void open() throws IOException {
        LOG.info("Opening object streams...");
        this.objOutStream = new ObjectOutputStream(this.socket.getOutputStream());
        this.objInputStream = new ObjectInputStream(this.socket.getInputStream());
        if (this.objInputStream == null) {
            LOG.error("Error with opening the object input stream.");
        }
        if (this.objOutStream == null) {
            LOG.error("Error with opening the object output stream.");
        }
        LOG.info("Object streams opened.");
    }


    @Override
    public void run() {
        LOG.info("Client thread started.");
        Object obj = null;
        try {
            obj = this.objInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOG.error("Error with reading the object from the stream.", e);
        }
        while (obj != null) {
            try {
                this.gameController.handleMessage(obj);
                obj = this.objInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                LOG.error("Error with reading the object from the stream.", e);
                System.exit(-1);
            }
            if (obj == null) {
                LOG.info("Disconnected from the server.");
                break;
            }
        }
    }

    public int getId() {
        return this.ID;
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
