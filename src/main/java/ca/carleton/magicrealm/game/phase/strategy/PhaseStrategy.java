package ca.carleton.magicrealm.game.phase.strategy;

import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.Phase;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:27 PM
 */
public interface PhaseStrategy {

    /**
     * Tests whether or not the phase is applicable to the given situation.
     *
     * @param phase the phase the player wishes to execute.
     * @return true if applicable.
     */
    public boolean appliesTo(final Phase phase);

    /**
     * Execute the phase for the given player.
     *
     * @param player the player.
     * @param args   the arguments to the phase. See javadoc for each phase for details.
     */
    public void doPhase(final Player player, final Object... args);
}
