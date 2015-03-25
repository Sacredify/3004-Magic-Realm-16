package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:15 AM
 */
public class GreatSword extends AbstractWeapon {

    public GreatSword() {
        this.goldValue = 10;
        this.length = 8;
        this.attackType = AttackType.STRIKING;
        this.strength = Harm.HEAVY;
        this.unAlertedSharpness = 1;
        this.alertedSharpness = 1;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.GREAT_SWORD;
    }
}
