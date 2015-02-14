package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:16 AM
 */
public class BroadSword extends AbstractWeapon {

    public BroadSword() {
        this.goldValue = 8;
        this.length = 7;
        this.attackType = AttackType.STRIKING;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.BROAD_SWORD;
    }
}
