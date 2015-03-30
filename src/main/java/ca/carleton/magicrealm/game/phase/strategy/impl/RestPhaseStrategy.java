package ca.carleton.magicrealm.game.phase.strategy.impl;

import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.game.phase.impl.RestPhase;
import ca.carleton.magicrealm.game.phase.strategy.PhaseStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rest phase. ASSUMPTIONS: Always set WOUNDED --> FATIGUED or FATIGUED --> NORMAL.
 * Created with IntelliJ IDEA.
 * Date: 30/03/2015
 * Time: 9:04 AM
 */
public class RestPhaseStrategy implements PhaseStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(RestPhaseStrategy.class);

    @Override
    public boolean appliesTo(final AbstractPhase phase) {
        return phase.getPhaseType() == PhaseType.REST;
    }

    @Override
    public void doPhase(final Player player, final AbstractPhase phase) {
        final ActionChit theChit = ((RestPhase) phase).getSelectedChit();

        if (theChit.isWounded()) {
            LOG.info("Chit was wounded. Setting to fatigued.");
            theChit.setWounded(false);
            theChit.setFatigued(true);
        } else if (theChit.isFatigued()) {
            LOG.info("Chit was fatigued. Setting to normal.");
            theChit.setWounded(false);
            theChit.setFatigued(false);
        } else {
            LOG.info("No action to be done to the chit.");
        }
    }
}
