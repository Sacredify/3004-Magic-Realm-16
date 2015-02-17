package ca.carleton.magicrealm.GUI;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.TileInformation;

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

    public JLabel createTileIcon(AbstractTile tile) {
        JLabel newTile = new JLabel();
        newTile.setIcon(createImageIcon(tile.getTileInformation().getPath()));
        return newTile;
    }
}
