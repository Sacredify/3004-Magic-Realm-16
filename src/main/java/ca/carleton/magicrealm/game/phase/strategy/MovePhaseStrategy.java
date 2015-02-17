package ca.carleton.magicrealm.game.phase.strategy;

import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.Phase;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:34 PM
 */
public class MovePhaseStrategy implements PhaseStrategy {

    @Override
    public boolean appliesTo(final Phase phase) {
        return phase == Phase.MOVE;
    }

    @Override
    public void doPhase(final Player player) {
        player.setCurrentClearing(player.getMoveTarget());
        player.setMoveTarget(null);
    }
}
