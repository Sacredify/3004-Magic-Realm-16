package ca.carleton.magicrealm.GUI.board.infoDialogs;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Tony on 03/04/2015.
 */
public class GameInfoDialog extends JDialog {
    private static final String DIALOG_NAME = "Game Information";

    private static final String GENERAL_INFO_TITLE = "General Information";
    private static final String INVENTORY_TITLE = "Inventory";

    private static final int GAME_INFO_DIALOG_WIDTH = 760;
    private static final int GAME_INFO_DIALOG_HEIGHT = 600;

    private static final int INFO_LABEL_WIDTH = 760;
    private static final int INFO_LABEL_HEIGHT = 500;

    private static final int TITLE_INT_SIZE = 25;

    public GameInfoDialog(final String infoString) {
        this.setTitle(DIALOG_NAME);
        this.setSize(GAME_INFO_DIALOG_WIDTH, GAME_INFO_DIALOG_HEIGHT);

        JLabel infoStringLabel = new JLabel(infoString);
        infoStringLabel.setSize(INFO_LABEL_WIDTH, INFO_LABEL_HEIGHT);
        this.add(infoStringLabel);

        JLabel inventoryTitleLabel = new JLabel(INVENTORY_TITLE);
        inventoryTitleLabel.setSize(50, 20);
        inventoryTitleLabel.setFont(new Font("Arial", Font.BOLD, TITLE_INT_SIZE));
        this.add(inventoryTitleLabel);



        this.setVisible(true);
    }
}
