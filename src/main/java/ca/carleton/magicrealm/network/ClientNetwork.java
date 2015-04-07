package ca.carleton.magicrealm.network;

import ca.carleton.magicrealm.control.ClientController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientNetwork implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ClientNetwork.class);

    private int ID = 0;

    private Socket socket = null;

    private Thread thread = null;

    private ObjectOutputStream objOutStream = null;

    private ObjectInputStream objInputStream = null;

    private ClientController clientController;

    private boolean done;

    public ClientNetwork(final String serverName, final int serverPort) {

        try {
            this.socket = new Socket(serverName, serverPort);
            this.ID = this.socket.getLocalPort();
            LOG.info("Successfully connected to the server. Details: {}.", this.socket);
            this.open();
            this.start();
        } catch (IOException ioe) {
            LOG.error("Could not connect to the server. Please ensure the server is available. Reason --> {}.", ioe.getMessage());
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
        LOG.info("Creating game controller and UI.");
        this.clientController = new ClientController();
        this.clientController.setNetworkConnection(this);
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

    public void sendMessage(String messageType, Object payload) {
        Message m = new Message(this.ID, messageType, payload);
        this.write(m);
        LOG.info("[ID {}] Sent {} message to the server.", this.ID, messageType);
    }

    public void open() throws IOException {
        LOG.info("Opening object streams...");
        this.objOutStream = new ObjectOutputStream(this.socket.getOutputStream());
        this.objInputStream = new ObjectInputStream(this.socket.getInputStream());
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
                this.clientController.handleMessage((Message) this.objInputStream.readObject());
            } catch (final IOException exception) {
                LOG.error("Error reading input from the server - Server may have terminated. Message --> {}", exception.getMessage());
                this.done = true;
            } catch (ClassNotFoundException e) {
                LOG.error("If this happens, I'll eat my hat.", e);
            }
        }
        this.clientController.shutDown();
        LOG.info("Client networking thread completed.");
    }
}
