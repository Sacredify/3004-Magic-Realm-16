package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.GUI.tile.Clearing;

import javax.swing.*;

/**
 * Created by Tony on 22/02/2015.
 */
public class MoveSelectionMenu extends JDialog{
    public static final String MOVE_SELECTION_WINDOW_NAME = "Where do you want to move to?";
    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 400;

    private MoveSelectionPanel moveSelectionPanel;

    public MoveSelectionMenu(Clearing clearing) {
        this.setTitle(MOVE_SELECTION_WINDOW_NAME);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        this.moveSelectionPanel = new MoveSelectionPanel();
        this.moveSelectionPanel.setupClearingList(clearing);
        this.add(moveSelectionPanel);
        this.setVisible(true);
    }

    public MoveSelectionPanel getMoveSelectionPanel() {
        return moveSelectionPanel;
    }
}
