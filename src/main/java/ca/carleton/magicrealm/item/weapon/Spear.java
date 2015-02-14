package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 8:58 AM
 */
public class Spear extends AbstractWeapon {

    public Spear() {
        this.goldValue = 10;
        this.length = 12;
        this.attackType = AttackType.STRIKING;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.SPEAR;
    }
}
