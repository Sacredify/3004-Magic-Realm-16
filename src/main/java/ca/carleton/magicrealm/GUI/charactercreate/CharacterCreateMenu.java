package ca.carleton.magicrealm.GUI.charactercreate;

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

    private static final int WINDOW_WIDTH = 500;

    private static final int WINDOW_HEIGHT = 500;

    private MenuPanel view;

    public CharacterCreateMenu() {
        super(WINDOW_NAME);
        this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.view = new MenuPanel();
        this.add(this.view);
    }


}
