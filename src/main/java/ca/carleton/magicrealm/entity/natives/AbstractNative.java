package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.item.Item;

/**
 * Represents one of the many natives within the magic Realm.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 10/02/15
 * Time: 8:42 AM
 */
public abstract class AbstractNative extends Entity {

    protected NativeType faction;

    protected int basicGoldWage;

    protected Item weapon;

    /**
     * The faction this native belongs to.
     *
     * @return their faction.
     */
    public NativeType getFaction() {
        return this.faction;
    }

    /**
     * Adds the native's bounty value to the player's total.
     *
     * @param player the player who defeated the native.
     */
    public abstract void addBountyToPlayer(final Player player);

}
