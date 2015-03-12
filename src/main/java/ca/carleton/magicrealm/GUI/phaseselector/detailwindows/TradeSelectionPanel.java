package ca.carleton.magicrealm.GUI.phaseselector.detailwindows;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.item.Item;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 11/03/2015.
 */
public class TradeSelectionPanel extends JPanel {
    public final String SELL = "Sell";
    public final String BUY = "Buy";
    public final String CONFIRM = "Confirm";
    public final String BUY_DRINKS_TEXT = "Buy drinks";

    private JList<Entity> entitiesAvailableList;
    private JList<Item> itemsAvailableList;
    private JScrollPane itemsScrollPane;

    private JRadioButton buyRadioButton;
    private JRadioButton sellRadioButton;

    private JCheckBox buyDrinksCheckBox;

    private JButton confirmTradeButton;

    private ArrayList<Item> playerItems;

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
        this.add(buyRadioButton);
        this.sellRadioButton = new JRadioButton(SELL);
        this.sellRadioButton.addActionListener(this.radioButtonSelectedActionListener());
        this.sellRadioButton.setEnabled(false);
        this.add(sellRadioButton);

        ButtonGroup buyOrSellButtonGroup;
        buyOrSellButtonGroup = new ButtonGroup();
        buyOrSellButtonGroup.add(buyRadioButton);
        buyOrSellButtonGroup.add(sellRadioButton);

        this.playerItems = new ArrayList<>();
        this.playerItems.addAll(player.getCharacter().getItems());

        this.buyDrinksCheckBox = new JCheckBox();
        this.buyDrinksCheckBox.setText(BUY_DRINKS_TEXT);
        this.add(buyDrinksCheckBox);

        this.itemsAvailableList = new JList<>();
        this.itemsAvailableList.addListSelectionListener(this.itemListSelectionListener());
        this.itemsScrollPane = new JScrollPane();
        this.itemsScrollPane.setViewportView(this.itemsAvailableList);
        this.add(itemsScrollPane);

        this.confirmTradeButton = new JButton(CONFIRM);
        this.confirmTradeButton.setSize(40, 30);
        this.confirmTradeButton.setEnabled(false);
        this.add(confirmTradeButton);
    }

    private ListSelectionListener entitiesListSelectionListener() {
        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                buyRadioButton.setEnabled(true);
                sellRadioButton.setEnabled(true);
            }
        };
    }

    private ListSelectionListener itemListSelectionListener() {
        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                confirmTradeButton.setEnabled(true);
            }
        };
    }

    private ActionListener radioButtonSelectedActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupItemsToTradeList();
            }
        };
    }

    public void setupItemsToTradeList() {
        this.itemsAvailableList = new JList<>();
        if (buyRadioButton.isSelected()) {
            ArrayList<Item> itemList = new ArrayList<>();
            itemList.addAll(this.entitiesAvailableList.getSelectedValue().getItems());
            this.itemsAvailableList.setListData(itemList.toArray(new Item[itemList.size()]));
        }
        else if (sellRadioButton.isSelected()) {
            this.itemsAvailableList.setListData(this.playerItems.toArray(new Item[playerItems.size()]));
        }
        this.itemsAvailableList.addListSelectionListener(this.itemListSelectionListener());
        this.itemsScrollPane.setViewportView(this.itemsAvailableList);
    }

    public JList<Entity> getEntitiesAvailableList() {
        return entitiesAvailableList;
    }

    public JList<Item> getItemsAvailableList() {
        return itemsAvailableList;
    }

    public JRadioButton getSellRadioButton() {
        return sellRadioButton;
    }

    public JCheckBox getBuyDrinksCheckBox() {
        return buyDrinksCheckBox;
    }

    public JButton getConfirmTradeButton() {
        return confirmTradeButton;
    }
}
