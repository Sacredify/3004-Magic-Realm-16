package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:22 AM
 */
public class Mace extends AbstractWeapon {

    public Mace() {
        this.goldValue = 5;
        this.length = 1;
        this.attackType = AttackType.STRIKING;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.MACE;
    }
}
