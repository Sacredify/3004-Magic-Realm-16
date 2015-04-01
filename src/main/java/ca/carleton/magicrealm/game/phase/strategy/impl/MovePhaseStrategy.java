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
        final Clearing temp = move.getOrigin();

        for (Path path : temp.getAdjacentPaths()) {
            if (path.getFromClearing().equals(((MovePhase) phase).getMoveTarget())
                    || path.getToClearing().equals(((MovePhase) phase).getMoveTarget())) {
                temp.removeEntity(player.getCharacter());
                move.getMoveTarget().addEntity(player.getCharacter());
                LOG.info("Moved player from {} to {}.", temp, move.getMoveTarget());
            } else {
                LOG.info("Played entered invalid location for his move phase. Not executed. (Tried {}).", ((MovePhase) phase).getMoveTarget());
            }
        }
    }
}
