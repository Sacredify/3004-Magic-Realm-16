package ca.carleton.magicrealm.GUI.phaseselector.detailwindows;

import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.game.Player;

import javax.swing.*;
import java.util.List;

/**
 * Created by Tony on 11/03/2015.
 */
public class TradeSelectionMenu extends JDialog{

    public final String TRADE_SELECTION_WINDOW_TITLE = "What would you like to trade";

    public static final int WINDOW_WIDTH = 600;

    public static final int WINDOW_HEIGHT = 350;

    TradeSelectionPanel tradeSelectionPanel;

    public TradeSelectionMenu(Player player, List<Entity> tradableEntities) {
        this.setTitle(TRADE_SELECTION_WINDOW_TITLE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        this.tradeSelectionPanel = new TradeSelectionPanel(player, tradableEntities);
        this.add(tradeSelectionPanel);
        this.setVisible(true);
    }
}
