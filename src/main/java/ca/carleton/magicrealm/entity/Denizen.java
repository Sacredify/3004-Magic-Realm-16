package ca.carleton.magicrealm.entity;

import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.weapon.AbstractWeapon;

import java.util.Random;

/**
 * An AI character within the magic realm.
 * <p/>
 * Monsters, natives and ghosts.
 * <p/>
 * Date: 23/02/15
 * Time: 5:36 AM
 */
public abstract class Denizen extends Entity {

    protected static final Random RANDOM = new Random();

    private static final long serialVersionUID = 2673653273387082308L;

    protected boolean prowling;

    protected Clearing startingClearing;

    protected Clearing currentClearing;

    protected Harm moveStrength;

    protected AbstractWeapon weapon;

    protected boolean isArmored;

    protected long random = RANDOM.nextLong();

    /**
     * The weapon the native is using.
     *
     * @return the weapon.
     */
    public AbstractWeapon getWeapon() {
        return this.weapon;
    }

    public boolean isProwling() {
        return this.prowling;
    }

    public void setProwling(final boolean prowling) {
        this.prowling = prowling;
    }

    public Clearing getStartingClearing() {
        return this.startingClearing;
    }

    public void setStartingClearing(final Clearing startingClearing) {
        this.startingClearing = startingClearing;
    }

    public Clearing getCurrentClearing() {
        return this.currentClearing;
    }

    public void setCurrentClearing(Clearing currentClearing) {
        this.currentClearing = currentClearing;
    }

    public Harm getMoveStrength() {
        return this.moveStrength;
    }

    public boolean isArmored() {
        return isArmored;
    }

    @Override
    public boolean equals(final Object rhs) {
        return rhs instanceof Denizen && this.getEntityInformation() == ((Denizen) rhs).getEntityInformation() && this.random == ((Denizen) rhs).random;
    }

}
