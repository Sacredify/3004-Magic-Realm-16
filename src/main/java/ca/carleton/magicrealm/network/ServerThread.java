package ca.carleton.magicrealm.network;

import ca.carleton.magicrealm.game.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ServerThread extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(ServerThread.class);

    private int ID = -1;

    private Socket socket = null;

    private ServerNetwork server = null;

    private ObjectOutputStream objOutStream = null;

    private ObjectInputStream objInputStream = null;

    private Player player;

    private boolean done;

    public ServerThread(final ServerNetwork server, final Socket socket) {
        super();
        this.server = server;
        this.socket = socket;
        this.ID = socket.getPort();
    }

    public void send(Message msg) {
        LOG.info("Sending {} message {}.", this.ID, msg.getMessageType());
        try {
            this.objOutStream.reset();
            this.objOutStream.writeObject(msg);
            this.objOutStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the socket and marks work done.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        this.done = true;
        if (this.socket != null) {
            this.socket.close();
        }
        if (this.objInputStream != null) {
            this.objInputStream.close();
        }
        if (this.objOutStream != null) {
            this.objOutStream.close();
        }
    }

    @Override
    public void run() {
        LOG.info("Running network thread for client {}.", this.ID);
        while (!this.done) {
            try {
                this.server.handle(this.ID, this.objInputStream.readObject());
            } catch (final IOException exception) {
                LOG.warn("Client ID -- {}. Error reading input - connection closed. Message --> {}.", this.ID, exception.getMessage());
                this.server.closeFor(this);
                break;
            } catch (ClassNotFoundException e) {
                LOG.error("If we seriously ever get this, I'll eat my hat.");
            }
        }
        LOG.info("Networking thread for client {} completed.", this.ID);
    }

    public void open() throws IOException {
        LOG.info("Opening buffer streams for client {}", this.ID);
        this.objOutStream = new ObjectOutputStream(this.socket.getOutputStream());
        this.objInputStream = new ObjectInputStream(this.socket.getInputStream());
        this.objOutStream.flush();
        LOG.info("Accepted client {} successfully.", this.ID);
    }

    @Override
    public String toString() {
        return String.format("[%d] Thread for %s.", this.ID, this.player.getCharacter());
    }

    public int getID() {
        return this.ID;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }
}
