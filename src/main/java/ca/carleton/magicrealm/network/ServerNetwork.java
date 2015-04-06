package ca.carleton.magicrealm.network;

import ca.carleton.magicrealm.control.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerNetwork implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ServerNetwork.class);

    private static final int SERVER_ID = 0;

    private volatile int clientCount = 0;

    private Thread thread;

    private ServerSocket server;

    private final ArrayList<ServerThread> clients = new ArrayList<ServerThread>();

    private ServerController serverController;

    public ServerNetwork(final int port, final int maxPlayers, final int maxDays) {
        try {
            this.server = new ServerSocket(port);
            this.server.setReuseAddress(true);
            this.serverController = new ServerController(this, maxPlayers, maxDays);
            this.start();
        } catch (final Exception exception) {
            LOG.error("Exception during server initialization. Error --> {}.", exception.getMessage());
            LOG.error("", exception);
            System.exit(0);
        }
    }

    /**
     * Called by client threads to handle messages.
     *
     * @param ID     the id of the sender.
     * @param object the message.
     */
    public synchronized void handle(int ID, Object object) {
        final Message message = (Message) object;
        LOG.info("Received a {} message from ID {}.", message.getMessageType(), ID);
        this.serverController.handleMessage(message);
    }

    /**
     * Start running the wait-loop for connections.
     */
    public void start() {
        if (this.thread == null) {
            this.thread = new Thread(this);
            this.thread.start();
            LOG.info("Game started. - Waiting for {} connections.", this.serverController.getMaxPlayers());
        }
    }

    /**
     * Accept clients into the game.
     */
    @Override
    public void run() {
        try {
            while (true) {
                //If there is room in the game add client socket to the list of clients
                if (this.clientCount < this.serverController.getMaxPlayers()) {
                    Socket clientSocket = this.server.accept();
                    this.clients.add(new ServerThread(this, clientSocket));
                    this.clients.get(this.clientCount).open();
                    this.clients.get(this.clientCount).start();
                    this.clientCount++;
                    LOG.info("Client-accept thread added new client.");
                } else {
                    this.server.close();
                    LOG.info("Max client count reached.");
                    break;
                }
            }
        } catch (IOException e) {
            LOG.error("Exception during server accept process.", e);
        }
        LOG.info("Sending initial character select.");
        this.broadcastMessage(SERVER_ID, new Message(SERVER_ID, Message.START_GAME, null));
        LOG.info("Done running client-accept thread.");
    }

    /**
     * Broad a message to other clients on behalf of the given ID. The ID does not receive this message.
     *
     * @param ID      the id of the sender (0 is server)..
     * @param message the message to send.
     */
    public void broadcastMessage(int ID, Object message) {
        Message m = (Message) message;
        LOG.info("Broadcasting message {} to connected clients from ID {}.", m.getMessageType(), ID);
        this.clients.stream().filter(client -> client.getID() != ID).forEach(client -> client.send(m));
    }

    /**
     * Remove a client from the game.
     *
     * @param serverThread the client thread.
     */
    public synchronized void closeFor(final ServerThread serverThread) {
        LOG.info("A player disconnected. Removing thread {}.", serverThread.getID());
        final int index = this.findClientIndex(serverThread.getID());
        this.clients.remove(index);
        this.serverController.cleanUpPlayer(serverThread.getPlayer());
        this.clientCount--;

        try {
            serverThread.close();
        } catch (final IOException exception) {
            LOG.error("Error closing thread!", exception);
        }
        LOG.info("Removed thread. Remaining players: {}", this.clientCount);
    }

    /**
     * Return the thread for the client with the given ID.
     *
     * @param ID the id.
     * @return the client thread.
     */
    public ServerThread getClientWithID(int ID) {
        for (ServerThread client : this.clients) {
            if (ID == client.getID()) {
                return client;
            }
        }
        return null;
    }

    /**
     * Find the index of the client in the list of clients.
     *
     * @param id the client ID.
     * @return the index.
     */
    private synchronized int findClientIndex(final int id) {
        int index = 0;
        for (final ServerThread thread : this.clients) {
            if (thread.getID() == id) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int getClientCount() {
        return this.clientCount;
    }

    public ArrayList<ServerThread> getClients() {
        return this.clients;
    }
}
