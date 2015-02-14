package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:14 AM
 */
public class Staff extends AbstractWeapon {

    public Staff() {
        this.goldValue = 1;
        this.length = 9;
        this.attackType = AttackType.STRIKING;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.STAFF;
    }
}
