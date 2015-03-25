package ca.carleton.magicrealm.item.armor;

import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.Item;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:28 AM
 */
public abstract class AbstractArmor extends Item {

    protected boolean damaged = false;

    protected ProtectionType protectsAgainst;

    protected Harm weight;

    public boolean isDamaged() {
        return this.damaged;
    }

    public void setDamaged(final boolean damaged) {
        this.damaged = damaged;
    }

    public ProtectionType getProtectsAgainst() {
        return this.protectsAgainst;
    }

    public Harm getWeight() {
        return this.weight;
    }
}
