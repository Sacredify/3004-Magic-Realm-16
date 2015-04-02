package ca.carleton.magicrealm.GUI.phaseselector.detailwindows;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.game.Player;

import javax.swing.*;

/**
 * Created by Tony on 01/04/2015.
 */
public class SearchSelectionPanel extends JPanel {

    public static final String CONFIRM_TEXT = "Confirm";

    public static final String LOCATE_TEXT = "Locate";

    public static final String PEER_TEXT = "Peer";

    public static final String LOOT_TEXT = "Loot";

    private JRadioButton locateRadioButton;

    private JRadioButton peerRadioButton;

    private JRadioButton lootRadioButton;

    private JButton confirmButton;

    public SearchSelectionPanel() {
        this.locateRadioButton = new JRadioButton(LOCATE_TEXT);
        this.locateRadioButton.setSelected(true);
        this.add(this.locateRadioButton);

        this.peerRadioButton = new JRadioButton(PEER_TEXT);
        this.add(this.peerRadioButton);

        this.lootRadioButton = new JRadioButton(LOOT_TEXT);
        this.add(this.lootRadioButton);

        ButtonGroup searchButtonsGroup = new ButtonGroup();
        searchButtonsGroup.add(this.locateRadioButton);
        searchButtonsGroup.add(this.peerRadioButton);
        searchButtonsGroup.add(this.lootRadioButton);

        this.confirmButton = new JButton(CONFIRM_TEXT);
        this.confirmButton.setSize(40,30);
        this.add(confirmButton);
    }

    public JRadioButton getLocateRadioButton() {
        return locateRadioButton;
    }

    public JRadioButton getPeerRadioButton() {
        return peerRadioButton;
    }

    public JRadioButton getLootRadioButton() {
        return lootRadioButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }
}
