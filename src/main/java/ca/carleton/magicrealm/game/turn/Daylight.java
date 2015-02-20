package ca.carleton.magicrealm.game.turn;

import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.strategy.MovePhaseStrategy;
import ca.carleton.magicrealm.game.phase.strategy.PhaseStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Groupings of methods to handle the daylight part of a turn.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:50 PM
 */
public class Daylight {

    private static final Logger LOG = LoggerFactory.getLogger(Daylight.class);

    private final List<PhaseStrategy> phaseStrategies = new ArrayList<PhaseStrategy>();

    /**
     * Initialize the strategies to use.
     */ {
        this.phaseStrategies.add(new MovePhaseStrategy());
    }

    public void processPhasesForPlayer(final Player player, final List<AbstractPhase> phasesToExecute) {
        LOG.info("Starting phase execution for player {}.", player);
        for (final AbstractPhase phase : phasesToExecute) {
            for (final PhaseStrategy strategy : this.phaseStrategies) {
                if (strategy.appliesTo(phase)) {
                    strategy.doPhase(player, phase);
                    LOG.info("Executed phase {}.", phase);
                }
            }
        }
        LOG.info("Done executing phases.");

    }

}
