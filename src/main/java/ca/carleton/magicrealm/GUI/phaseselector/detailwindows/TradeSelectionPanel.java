package ca.carleton.magicrealm.GUI.phaseselector.detailwindows;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.item.Item;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 11/03/2015.
 */
public class TradeSelectionPanel extends JPanel {
    private static final String SELL = "Sell";
    private static final String BUY = "Buy";
    private static final String CONFIRM = "Confirm";
    private static final String BUY_DRINKS_TEXT = "Buy drinks";

    private final JList<Entity> entitiesAvailableList;
    private JList<Item> itemsAvailableList;
    private final JScrollPane itemsScrollPane;

    private final JRadioButton buyRadioButton;
    private final JRadioButton sellRadioButton;

    private final JCheckBox buyDrinksCheckBox;

    private final JButton confirmTradeButton;

    private final ArrayList<Item> playerItems;

    public TradeSelectionPanel(final Player player, final List<Entity> tradeableEntities) {
        ArrayList<Entity> entitiesOnClearing = new ArrayList<>();

        this.entitiesAvailableList = new JList<>();
        this.entitiesAvailableList.setListData(tradeableEntities.toArray(new Entity[entitiesOnClearing.size()]));
        this.entitiesAvailableList.addListSelectionListener(this.entitiesListSelectionListener());

        JScrollPane entitiesScrollPane;
        entitiesScrollPane = new JScrollPane();
        entitiesScrollPane.setViewportView(this.entitiesAvailableList);
        this.add(entitiesScrollPane);

        /** Radio buttons **/
        this.buyRadioButton = new JRadioButton(BUY);
        this.buyRadioButton.addActionListener(this.radioButtonSelectedActionListener());
        this.buyRadioButton.setEnabled(false);
        this.add(this.buyRadioButton);
        this.sellRadioButton = new JRadioButton(SELL);
        this.sellRadioButton.addActionListener(this.radioButtonSelectedActionListener());
        this.sellRadioButton.setEnabled(false);
        this.add(this.sellRadioButton);

        ButtonGroup buyOrSellButtonGroup;
        buyOrSellButtonGroup = new ButtonGroup();
        buyOrSellButtonGroup.add(this.buyRadioButton);
        buyOrSellButtonGroup.add(this.sellRadioButton);

        this.playerItems = new ArrayList<>();
        this.playerItems.addAll(player.getCharacter().getItems());

        this.buyDrinksCheckBox = new JCheckBox();
        this.buyDrinksCheckBox.setText(BUY_DRINKS_TEXT);
        this.add(this.buyDrinksCheckBox);

        this.itemsAvailableList = new JList<>();
        this.itemsAvailableList.addListSelectionListener(this.itemListSelectionListener());
        this.itemsScrollPane = new JScrollPane();
        this.itemsScrollPane.setViewportView(this.itemsAvailableList);
        this.add(this.itemsScrollPane);

        this.confirmTradeButton = new JButton(CONFIRM);
        this.confirmTradeButton.setSize(40, 30);
        this.confirmTradeButton.setEnabled(false);
        this.add(this.confirmTradeButton);
    }

    private ListSelectionListener entitiesListSelectionListener() {
        return e -> {
            TradeSelectionPanel.this.buyRadioButton.setEnabled(true);
            TradeSelectionPanel.this.sellRadioButton.setEnabled(true);
        };
    }

    private ListSelectionListener itemListSelectionListener() {
        return e -> TradeSelectionPanel.this.confirmTradeButton.setEnabled(true);
    }

    private ActionListener radioButtonSelectedActionListener() {
        return e -> this.setupItemsToTradeList();
    }

    public void setupItemsToTradeList() {
        this.itemsAvailableList = new JList<>();
        if (this.buyRadioButton.isSelected()) {
            ArrayList<Item> itemList = new ArrayList<>();
            itemList.addAll(this.entitiesAvailableList.getSelectedValue().getItems());
            this.itemsAvailableList.setListData(itemList.toArray(new Item[itemList.size()]));
        }
        else if (this.sellRadioButton.isSelected()) {
            this.itemsAvailableList.setListData(this.playerItems.toArray(new Item[this.playerItems.size()]));
        }
        this.itemsAvailableList.addListSelectionListener(this.itemListSelectionListener());
        this.itemsScrollPane.setViewportView(this.itemsAvailableList);
    }

    public JList<Entity> getEntitiesAvailableList() {
        return this.entitiesAvailableList;
    }

    public JList<Item> getItemsAvailableList() {
        return this.itemsAvailableList;
    }

    public JRadioButton getSellRadioButton() {
        return this.sellRadioButton;
    }

    public JCheckBox getBuyDrinksCheckBox() {
        return this.buyDrinksCheckBox;
    }

    public JButton getConfirmTradeButton() {
        return this.confirmTradeButton;
    }
}
