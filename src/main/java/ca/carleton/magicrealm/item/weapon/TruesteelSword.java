package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:18 AM
 */
public class TruesteelSword extends AbstractWeapon {

    public TruesteelSword() {
        this.goldValue = 25;
        this.length = 7;
        this.attackType = AttackType.STRIKING;
        this.strength = Harm.MEDIUM;
        this.unAlertedSharpness = 2;
        this.alertedSharpness = 2;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.TRUESTEEL_SWORD;
    }
}
