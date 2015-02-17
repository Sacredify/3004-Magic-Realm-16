package ca.carleton.magicrealm.game.phase.strategy;

import ca.carleton.magicrealm.GUI.tile.Clearing;
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

    /**
     * @param player the player.
     * @param args   args[0] the clearing to move to.
     */
    @Override
    public void doPhase(final Player player, final Object... args) {
        if (!(args[0] instanceof Clearing)) {
            throw new ClassCastException("Incorrect argument supplied to move phase.");
        }
        final Clearing clearingToMoveTo = (Clearing) args[0];

        player.setCurrentClearing(clearingToMoveTo);
    }
}
