package ca.carleton.magicrealm.GUI.phaseselector.detailwindows;

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

        JScrollPane scrollPane = new JScrollPane();
        this.clearingJList = new JList<>();
        scrollPane.setViewportView(this.clearingJList);
        this.add(scrollPane);

        this.confirmButton = new JButton(CONFIRM_TEXT);
        this.confirmButton.setSize(40,20);
        this.add(this.confirmButton);
    }

    public void setupClearingList(final java.util.List<Clearing> clearings) {
        this.clearingJList.setListData(clearings.toArray(new Clearing[clearings.size()]));
    }

    public JList<Clearing> getClearingJList() {
        return this.clearingJList;
    }

    public JButton getConfirmButton() {
        return this.confirmButton;
    }
}
