package ca.carleton.magicrealm.GUI.phaseselector.detailwindows;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.game.Player;

import javax.swing.*;
import java.util.List;

/**
 * Created by Tony on 11/03/2015.
 */
public class TradeSelectionMenu extends JDialog{

    private static final long serialVersionUID = 5904435545349887190L;

    public final String TRADE_SELECTION_WINDOW_TITLE = "What would you like to trade";

    public static final int WINDOW_WIDTH = 600;

    public static final int WINDOW_HEIGHT = 350;

    TradeSelectionPanel tradeSelectionPanel;

    public TradeSelectionMenu(Player player, List<Entity> tradeableEntities) {
        this.setTitle(this.TRADE_SELECTION_WINDOW_TITLE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        this.tradeSelectionPanel = new TradeSelectionPanel(player, tradeableEntities);
        this.add(this.tradeSelectionPanel);
        this.setVisible(true);
    }

    public TradeSelectionPanel getTradeSelectionPanel() {
        return tradeSelectionPanel;
    }
}
