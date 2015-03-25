package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:20 AM
 */
public class ThrustingSword extends AbstractWeapon {

    public ThrustingSword() {
        this.goldValue = 6;
        this.length = 4;
        this.attackType = AttackType.STRIKING;
        this.strength = Harm.LIGHT;
        this.unAlertedSharpness = 1;
        this.alertedSharpness = 1;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.THRUSTING_SWORD;
    }
}
