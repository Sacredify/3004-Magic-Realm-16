package ca.carleton.magicrealm.entity;

import ca.carleton.magicrealm.game.Player;

/**
 * Entities implementing this interface have a bounty for the player when killed.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 16/02/15
 * Time: 12:52 PM
 */
public interface BountyCarrier {

    /**
     * Adds the denizen's bounty value to the player's total.
     *
     * @param player the player who defeated the native.
     */
    public void addBountyToPlayer(final Player player);
}
