package ca.carleton.magicrealm.GUI.charactercreate;

import ca.carleton.magicrealm.game.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog menu for choosing your character as well as assigning victory points.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 8:37 PM
 */
public class CharacterCreateMenu extends JFrame {

    private static final String WINDOW_NAME = "Character Creation";

    public static final int WINDOW_WIDTH = 500;

    public static final int WINDOW_HEIGHT = 350;

    private MenuPanel view;

    private MenuModel model;

    /**
     * Creates a new character create dialog.
     *
     * @param player the player this dialog is for.
     */
    public CharacterCreateMenu(final Player player) {
        super(WINDOW_NAME);
        this.initWindowSettings();
        this.model = new MenuModel(player);
        this.view = new MenuPanel(this.model);
        this.add(this.view);
    }

    /**
     * Displays the dialog on screen.
     */
    public void displayWindow() {
        this.setVisible(true);
    }

    /**
     * Closes the dialog once done with settings.
     */
    public void disposeWindow() {
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Initialize settings for this frame.
     */
    private void initWindowSettings() {
        this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }

}
