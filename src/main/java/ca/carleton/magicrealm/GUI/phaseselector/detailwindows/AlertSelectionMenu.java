package ca.carleton.magicrealm.GUI.phaseselector.detailwindows;

import ca.carleton.magicrealm.game.Player;

import javax.swing.*;

/**
 * Created by Tony on 19/03/2015.
 */
public class AlertSelectionMenu extends JDialog{

    public static final String ALERT_SELECTION_WINDOW_NAME = "Where do you want to move to?";

    public static final int WINDOW_WIDTH = 300;

    public static final int WINDOW_HEIGHT = 200;

    private static final long serialVersionUID = -9178154483569437487L;

    AlertSelectionPanel alertSelectionPanel;

    public AlertSelectionMenu(Player player) {
        this.setTitle(ALERT_SELECTION_WINDOW_NAME);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        this.alertSelectionPanel = new AlertSelectionPanel(player);
        this.alertSelectionPanel.getAlertableWeapons().addListSelectionListener(this.alertSelectionPanel.weaponSelectedListListener());
        this.add(this.alertSelectionPanel);
        this.setVisible(true);
    }

    public AlertSelectionPanel getAlertSelectionPanel() {
        return this.alertSelectionPanel;
    }
}
