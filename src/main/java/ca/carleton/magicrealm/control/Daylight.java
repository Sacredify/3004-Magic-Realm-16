package ca.carleton.magicrealm.control;

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

    private static final List<PhaseStrategy> phaseStrategies = new ArrayList<PhaseStrategy>();

    /**
     * Initialize the strategies to use.
     */
    static {
        phaseStrategies.add(new MovePhaseStrategy());
    }

    public static void processPhaseForPlayer(final Player player, final AbstractPhase phaseToExecute) {
        for (final PhaseStrategy strategy : phaseStrategies) {
            if (strategy.appliesTo(phaseToExecute)) {
                strategy.doPhase(player, phaseToExecute);
                LOG.info("Executed phase {}.", phaseToExecute);
            }
        }
    }

}
