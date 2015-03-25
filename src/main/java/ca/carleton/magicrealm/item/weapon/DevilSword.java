package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:17 AM
 */
public class DevilSword extends AbstractWeapon {

    public DevilSword() {
        this.goldValue = 20;
        this.length = 7;
        this.attackType = AttackType.STRIKING;
        this.strength = Harm.HEAVY;
        this.unAlertedSharpness = 1;
        this.alertedSharpness = 1;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.DEVIL_SWORD;
    }
}
