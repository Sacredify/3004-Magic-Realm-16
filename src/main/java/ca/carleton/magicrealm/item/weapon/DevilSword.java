package ca.carleton.magicrealm.item.weapon;

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
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.DEVIL_SWORD;
    }
}
