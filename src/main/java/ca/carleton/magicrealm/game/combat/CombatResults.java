package ca.carleton.magicrealm.game.combat;

import ca.carleton.magicrealm.game.Player;

/**
 * Created with IntelliJ IDEA.
 * Date: 25/03/2015
 * Time: 3:30 PM
 */
public class CombatResults {

    private final Player player;

    private final boolean mustWoundChit;

    private final boolean dead;

    private CombatResults(final Player player, final boolean dead, final boolean mustWoundChit) {
        this.player = player;
        this.mustWoundChit = mustWoundChit;
        this.dead = dead;
    }

    /**
     * Create a new set of results after a round of combat.
     *
     * @param player        the player.
     * @param dead          whether or not they died.
     * @param mustWoundChit whether or not they need to wound a chit.
     * @return the new results.
     */
    public static CombatResults createResultsFor(final Player player, final boolean dead, final boolean mustWoundChit) {
        return new CombatResults(player, mustWoundChit, dead);
    }

    public boolean isMustWoundChit() {
        return this.mustWoundChit;
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isDead() {
        return this.dead;
    }
}
