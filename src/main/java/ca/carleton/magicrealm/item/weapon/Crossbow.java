package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 8:58 AM
 */
public class Crossbow extends AbstractWeapon {

    public Crossbow() {
        this.goldValue = 10;
        this.length = 12;
        this.attackType = AttackType.MISSILE;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.CROSSBOW;
    }
}
