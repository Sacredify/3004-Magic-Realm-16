package ca.carleton.magicrealm.network;

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

    private boolean done;

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
            this.objOutStream.reset();
            this.objOutStream.writeObject(msg);
            this.objOutStream.flush();
        } catch (final IOException exception) {
            LOG.error("Error writing to the stream.", exception);
        }
    }

    private void start() {
        if (this.thread == null) {
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    public void stop() {
        try {
            if (this.thread != null) {
                this.thread = null;
            }
            if (this.socket != null) {
                this.socket.close();
            }

            this.objInputStream = null;
            this.objOutStream = null;
            this.socket = null;
        } catch (final IOException exception) {
            LOG.error("Error closing connection...", exception);
        }
        this.done = true;
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

    /**
     * Continually waits for input from the server.
     */
    @Override
    public void run() {
        LOG.info("Client thread {} running.", this.socket.getLocalPort());
        while (!this.done) {
            try {
                this.gameController.handleMessage((Message) this.objInputStream.readObject());
            } catch (final IOException exception) {
                LOG.error("Listening error...", exception);
            } catch (ClassNotFoundException e) {
                LOG.error("If this happens, I'll eat my hat.", e);
            }
        }
        LOG.info("Client networking thread completed.");
    }
}
