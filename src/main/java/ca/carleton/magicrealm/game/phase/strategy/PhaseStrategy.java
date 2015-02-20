package ca.carleton.magicrealm.game.phase.strategy;

import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;

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
    public boolean appliesTo(final AbstractPhase phase);

    /**
     * Execute the phase for the given player.
     *
     * @param player the player.
     * @param phase  the phase data.
     */
    public void doPhase(final Player player, final AbstractPhase phase);
}
