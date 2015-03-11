package ca.carleton.magicrealm.game.phase.strategy.impl;

import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.game.phase.strategy.PhaseStrategy;

/**
 * Created with IntelliJ IDEA.
 * Date: 11/03/15
 * Time: 4:43 PM
 */
public class TradePhaseStrategy implements PhaseStrategy {

    @Override
    public boolean appliesTo(final AbstractPhase phase) {
        return phase.getPhaseType() == PhaseType.TRADE;
    }

    @Override
    public void doPhase(final Player player, final AbstractPhase phase) {
      // TODO implementation
    }
}
