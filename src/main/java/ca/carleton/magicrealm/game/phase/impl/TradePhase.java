package ca.carleton.magicrealm.game.phase.impl;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 11/03/15
 * Time: 4:32 PM
 */
public class TradePhase extends AbstractPhase {

    private static final Logger LOG = LoggerFactory.getLogger(TradePhase.class);

    private Entity tradeTarget;

    private Item itemToTrade;

    private boolean selling;

    private boolean drinksBought;

    private Clearing currentClearing;

    /**
     * For cheating. Allows overriding the default behaviour with the roll we want.
     */
    public int override = 0;

    @Override
    public PhaseType getPhaseType() {
        return PhaseType.TRADE;
    }

    @Override
    public void updateFromBoard(final Player player, final BoardModel board) {
        // Update the entity
        for (final AbstractTile tile : board.getAllTiles()) {
            for (final Clearing clearing : tile.getClearings()) {
                for (final Entity entity : clearing.getEntities()) {
                    if (entity.equals(this.tradeTarget)) {
                        this.tradeTarget = entity;
                        LOG.info("Updated trade target.");
                        break;
                    }
                }
            }
        }

        // Update the clearing
        for (final AbstractTile tile : board.getAllTiles()) {
            for (final Clearing clearing : tile.getClearings()) {
                if (this.currentClearing.equals(clearing)) {
                    this.currentClearing = clearing;
                    LOG.info("Updated current clearing.");
                    break;
                }
            }
        }

        if (this.selling) {
            // If selling, update from player.
            for (final Item item : player.getCharacter().getItems()) {
                if (item.equals(this.itemToTrade)) {
                    this.itemToTrade = item;
                    LOG.info("Updated item to trade.");
                    break;
                }
            }
        } else {
            for (final Item item : this.tradeTarget.getItems()) {
                if (item.equals(this.itemToTrade)) {
                    this.itemToTrade = item;
                    LOG.info("Updated item to trade.");
                    break;
                }
            }
        }

    }

    public boolean isSelling() {
        return this.selling;
    }

    public void setSelling(final boolean selling) {
        this.selling = selling;
    }

    public Entity getTradeTarget() {
        return this.tradeTarget;
    }

    public void setTradeTarget(final Entity tradeTarget) {
        this.tradeTarget = tradeTarget;
    }

    public Item getItemToTrade() {
        return this.itemToTrade;
    }

    public void setItemToTrade(final Item itemToTrade) {
        this.itemToTrade = itemToTrade;
    }

    public boolean isDrinksBought() {
        return this.drinksBought;
    }

    public void setDrinksBought(final boolean drinksBought) {
        this.drinksBought = drinksBought;
    }

    public Clearing getCurrentClearing() {
        return this.currentClearing;
    }

    public void setCurrentClearing(final Clearing currentClearing) {
        this.currentClearing = currentClearing;
    }
}
