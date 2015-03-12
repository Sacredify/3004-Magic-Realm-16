package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.HidePhase;
import ca.carleton.magicrealm.game.phase.impl.MovePhase;
import ca.carleton.magicrealm.game.phase.impl.TradePhase;
import ca.carleton.magicrealm.item.Item;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 22/02/15
 * Time: 7:02 PM
 */
public class PhaseSelectorModel {

    private final List<AbstractPhase> phases;

    private final PhaseSelectorMenu menu;

    public PhaseSelectorModel(final PhaseSelectorMenu menu, final List<AbstractPhase> phases) {
        this.phases = phases;
        this.menu = menu;
    }

    public void addMovementPhase(final Clearing clearing, final Clearing origin) {
        final MovePhase movePhase = new MovePhase();
        movePhase.setMoveTarget(clearing);
        movePhase.setOrigin(origin);
        this.phases.add(movePhase);
    }

    public void addTradePhase(final Entity tradeTarget, final Item tradedItem, final boolean selling, final boolean isDrinksBought) {
        final TradePhase tradePhase = new TradePhase();
        tradePhase.setTradeTarget(tradeTarget);
        tradePhase.setItemToTrade(tradedItem);
        tradePhase.setSelling(selling);
        tradePhase.setDrinksBought(isDrinksBought);
        this.phases.add(tradePhase);
    }

    public void addHidePhase() {
        final HidePhase hidePhase = new HidePhase();
        this.phases.add(hidePhase);
    }

    public void done() {
        this.menu.disposeWindow();
    }

}
