package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:21 AM
 */
public class LivingSword extends AbstractWeapon {

    public LivingSword() {
        this.goldValue = 25;
        this.length = 4;
        this.attackType = AttackType.STRIKING;
        this.strength = Harm.LIGHT;
        this.unAlertedSharpness = 1;
        this.alertedSharpness = 1;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.LIVING_SWORD;
    }
}
