package ca.carleton.magicrealm;

import ca.carleton.magicrealm.Networking.AppServer;
import ca.carleton.magicrealm.Networking.Config;
import ca.carleton.magicrealm.control.GameController;

import javax.swing.*;

/**
 * Created by anvargazizov on 2015-02-20.
 */
public class ServerMain {

    public static void main(String[] args) {
        AppServer srvr = new AppServer(Config.DEFAULT_HOST_PORT);
        srvr.start();
    }
}
