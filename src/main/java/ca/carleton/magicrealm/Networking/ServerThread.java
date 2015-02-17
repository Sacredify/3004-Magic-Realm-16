package ca.carleton.magicrealm.Networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class ServerThread extends Thread {
    private int ID = -1;
    private Socket socket = null;
    private AppServer server = null;
    private int gameNumber = -1;
    private BufferedReader streamIn = null;
    private BufferedWriter streamOut = null;

    private boolean done = false;

    public ServerThread(AppServer server, Socket socket) {
        super();
        this.server = server;
        this.socket = socket;
        this.ID = socket.getPort();
    }


    public void send(String msg) {
        try {
            streamOut.write(msg);
            streamOut.newLine();
            streamOut.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void run() {
        try {
            String line = streamIn.readLine();
            while (line != null) {
                System.out.println(line);
                server.handle(ID, line);
                line = streamIn.readLine();
                if (line == null) {
                    System.out.println("Cliend dropped");
                    break;
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }


    public void open() throws IOException {
        System.out.println(ID + ":Opening buffer streams");
        streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        streamOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        if (streamIn != null && streamOut != null) {
            System.out.println("Buffered streams opened on thread:" + ID);
        }
    }

    public int getID() {
        // TODO Auto-generated method stub
        return ID;
    }

}
