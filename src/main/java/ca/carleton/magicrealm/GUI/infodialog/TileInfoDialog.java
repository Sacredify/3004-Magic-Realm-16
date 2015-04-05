package ca.carleton.magicrealm.GUI.infodialog;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 3:55 PM
 */
public class TileInfoDialog extends JDialog {

    public static final int WINDOW_WIDTH = 860;

    public static final int WINDOW_HEIGHT = 300;

    public TileInfoDialog(final AbstractTile tileClicked) {
        this.initWindowSettings();
        this.setTitle("Tile Information Dialog");
        this.add(new InfoPanel(tileClicked));
    }

    public void displayWindow() {
        this.setVisible(true);
    }

    private void initWindowSettings() {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setModal(true);
        this.setResizable(false);
    }

}
