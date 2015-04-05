package ca.carleton.magicrealm.GUI.board.infoDialogs;

import ca.carleton.magicrealm.GUI.board.BoardServices;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.item.Item;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Tony on 03/04/2015.
 */
public class GameInfoDialog extends JDialog {
    private static final String DIALOG_NAME = "Game Information";

    private static final String INVENTORY_TITLE = "Inventory";

    private static final int GAME_INFO_DIALOG_WIDTH = 760;
    private static final int GAME_INFO_DIALOG_HEIGHT = 600;

    private static final int INFO_LABEL_WIDTH = 460;
    private static final int INFO_LABEL_HEIGHT = 500;

    private static final int INVENTORY_LABEL_WIDTH = 200;
    private static final int INVENTORY_LABEL_HEIGHT = 200;

    private static final int TITLE_INT_SIZE = 25;

    public GameInfoDialog(final String infoString, final AbstractCharacter character) {
        BoardServices boardServices = new BoardServices();

        this.setLayout(null);
        this.setTitle(DIALOG_NAME);
        this.setSize(GAME_INFO_DIALOG_WIDTH, GAME_INFO_DIALOG_HEIGHT);

        JLabel infoStringLabel = new JLabel(infoString);
        infoStringLabel.setSize(INFO_LABEL_WIDTH, INFO_LABEL_HEIGHT);
        this.add(infoStringLabel);

        JLabel inventoryTitleLabel = new JLabel(INVENTORY_TITLE);
        inventoryTitleLabel.setSize(200, 25);
        inventoryTitleLabel.setLocation(500, 50);
        inventoryTitleLabel.setFont(new Font("Arial", Font.BOLD, TITLE_INT_SIZE));
        this.add(inventoryTitleLabel);

        for (Item item : character.getItems()) {
            JLabel inventoryLabel = new JLabel();
            inventoryLabel.setText(item.toString());
            inventoryLabel.setIcon(boardServices.createImageIcon(item.getItemInformation().getActivePath()));
            inventoryLabel.setSize(INVENTORY_LABEL_WIDTH, INVENTORY_LABEL_HEIGHT);
            inventoryLabel.setLocation(500, 100 * character.getItems().indexOf(item) + 30);
            this.add(inventoryLabel);
        }

        this.setVisible(true);
    }
}
