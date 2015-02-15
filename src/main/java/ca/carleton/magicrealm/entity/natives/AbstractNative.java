package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.game.Player;

/**
 * Represents one of the many natives within the magic Realm.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 10/02/15
 * Time: 8:42 AM
 */
public abstract class AbstractNative extends Entity {

    /**
     * The faction this native belongs to.
     *
     * @return their faction.
     */
    public abstract NativeType getNativeType();

    /**
     * Adds the native's bounty value to the player's total.
     *
     * @param player the player who defeated the native.
     */
    public abstract void addBountyToPlayer(final Player player);

}
