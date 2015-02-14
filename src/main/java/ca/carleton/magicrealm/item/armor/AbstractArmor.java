package ca.carleton.magicrealm.item.armor;

import ca.carleton.magicrealm.item.Item;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:28 AM
 */
public abstract class AbstractArmor extends Item {

    protected boolean damaged = false;

    protected ProtectionType[] protectsAgainst;

    public boolean isDamaged() {
        return this.damaged;
    }

    public ProtectionType[] getProtectsAgainst() {
        return this.protectsAgainst;
    }
}
