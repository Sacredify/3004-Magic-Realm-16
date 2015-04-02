package ca.carleton.magicrealm.game.phase.strategy.impl;

import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.Path;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.game.phase.impl.MovePhase;
import ca.carleton.magicrealm.game.phase.strategy.PhaseStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:34 PM
 */
public class MovePhaseStrategy implements PhaseStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(MovePhaseStrategy.class);

    @Override
    public boolean appliesTo(final AbstractPhase phase) {
        return phase.getPhaseType() == PhaseType.MOVE;
    }

    @Override
    public void doPhase(final Player player, final AbstractPhase phase) {

        final MovePhase move = (MovePhase) phase;
        final Clearing origin =  move.getBoard().getClearingForPlayer(player);

        for (Path path : origin.getAdjacentPaths()) {
            if (path.checkIfClearingIsConnectedToPath(((MovePhase) phase).getMoveTarget())) {
                origin.removeEntity(player.getCharacter());
                move.getMoveTarget().addEntity(player.getCharacter());
                LOG.info("Moved player from {} to {}.", origin, move.getMoveTarget());
                return;
            }
        }
        LOG.info("Played entered invalid location for his move phase. Not executed. (Tried {}).", ((MovePhase) phase).getMoveTarget());
    }
}
