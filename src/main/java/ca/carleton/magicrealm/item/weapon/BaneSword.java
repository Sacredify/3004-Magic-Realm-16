package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:16 AM
 */
public class BaneSword extends AbstractWeapon {

    public BaneSword() {
        this.goldValue = 20;
        this.length = 8;
        this.attackType = AttackType.STRIKING;
        this.strength = Harm.TREMENDOUS;
        this.unAlertedSharpness = 1;
        this.alertedSharpness = 1;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.BANE_SWORD;
    }
}
