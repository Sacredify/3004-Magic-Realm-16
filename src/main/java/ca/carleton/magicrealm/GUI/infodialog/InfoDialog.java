package ca.carleton.magicrealm.GUI.infodialog;

import ca.carleton.magicrealm.GUI.tile.Clearing;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 3:55 PM
 */
public class InfoDialog extends JDialog {

    public static final int WINDOW_WIDTH = 600;

    public static final int WINDOW_HEIGHT = 300;

    public InfoDialog(final Clearing clearingClicked) {
        this.initWindowSettings();
        this.setTitle("Clearing Information Dialog");
        this.add(new InfoPanel(clearingClicked));
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
