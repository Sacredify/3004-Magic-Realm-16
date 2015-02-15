package ca.carleton.magicrealm.GUI;

import javax.swing.*;

/**
 * Created by Tony on 14/02/2015.
 */
public class BoardServices {

    public ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = this.getClass().getClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
