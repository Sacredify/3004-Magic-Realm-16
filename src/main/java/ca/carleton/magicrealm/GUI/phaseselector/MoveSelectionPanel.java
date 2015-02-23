package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.GUI.tile.Clearing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Tony on 22/02/2015.
 */
public class MoveSelectionPanel extends JPanel{
    public static final String CONFIRM_TEXT = "Move";

    private JList<Clearing> clearingJList;

    private JButton confirmButton;

    public MoveSelectionPanel() {
        this.setLayout(new FlowLayout());
        this.setSize(MoveSelectionMenu.WINDOW_WIDTH, MoveSelectionMenu.WINDOW_HEIGHT);

        this.clearingJList = new JList<>();
        this.clearingJList.setSize(250, 150);
        this.add(clearingJList);

        this.confirmButton = new JButton(CONFIRM_TEXT);
        this.confirmButton.setSize(40,20);
        this.add(confirmButton);
    }

    public void setupClearingList(final Clearing clearing) {
        this.clearingJList.setListData(clearing.getAdjacentClearings().toArray(new Clearing[clearing.getAdjacentClearings().size()]));
    }

    public JList<Clearing> getClearingJList() {
        return clearingJList;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }
}
