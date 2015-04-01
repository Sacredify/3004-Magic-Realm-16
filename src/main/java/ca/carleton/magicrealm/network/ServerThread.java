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

    private AppServer server = null;

    private ObjectOutputStream objOutStream = null;

    private ObjectInputStream objInputStream = null;

    private Player player;

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
        LOG.info("Opening buffer streams for client {}", this.ID);
        this.objOutStream = new ObjectOutputStream(this.socket.getOutputStream());
        this.objInputStream = new ObjectInputStream(this.socket.getInputStream());
        this.objOutStream.flush();
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
