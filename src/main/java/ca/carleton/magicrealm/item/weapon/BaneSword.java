package ca.carleton.magicrealm.item.weapon;

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
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.BANE_SWORD;
    }
}
