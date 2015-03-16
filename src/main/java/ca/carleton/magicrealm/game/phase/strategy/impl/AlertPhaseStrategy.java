package ca.carleton.magicrealm.game.phase.strategy.impl;

import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.game.phase.impl.AlertPhase;
import ca.carleton.magicrealm.game.phase.strategy.PhaseStrategy;

/**
 * Created with IntelliJ IDEA.
 * Date: 15/03/15
 * Time: 11:59 PM
 */
public class AlertPhaseStrategy implements PhaseStrategy {

    @Override
    public boolean appliesTo(final AbstractPhase phase) {
        return phase.getPhaseType() == PhaseType.ALERT;
    }

    @Override
    public void doPhase(final Player player, final AbstractPhase phase) {
        final AlertPhase alert = (AlertPhase) phase;

        alert.getWeapon().setAlert(true);

    }
}
