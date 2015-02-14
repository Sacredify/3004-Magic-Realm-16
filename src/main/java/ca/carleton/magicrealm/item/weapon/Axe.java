package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:22 AM
 */
public class Axe extends AbstractWeapon {

    public Axe() {
        this.goldValue = 4;
        this.length = 2;
        this.attackType = AttackType.STRIKING;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.AXE;
    }
}
