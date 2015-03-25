package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.Item;

/**
 * A weapon carried by entities or looted from the game board.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 8:53 AM
 */
public abstract class AbstractWeapon extends Item {

    private static final long serialVersionUID = 979038272753356393L;

    protected AttackType attackType;

    // The weight/attack.
    protected Harm strength;

    // The sharpness, alert/unAlert
    protected int unAlertedSharpness;

    protected int alertedSharpness;

    protected int length;

    protected boolean alert;

    public AttackType getAttackType() {
        return this.attackType;
    }

    public int getLength() {
        return this.length;
    }

    public boolean isAlert() {
        return this.alert;
    }

    public void setAlert(final boolean alert) {
        this.alert = alert;
    }

    public Harm getStrength() {
        return this.strength;
    }

    public int getSharpness() {
        return this.alert ? this.alertedSharpness : this.unAlertedSharpness;
    }

}
