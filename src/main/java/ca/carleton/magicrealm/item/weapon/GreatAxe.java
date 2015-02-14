package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:19 AM
 */
public class GreatAxe extends AbstractWeapon {

    public GreatAxe() {
        this.goldValue = 8;
        this.length = 5;
        this.attackType = AttackType.STRIKING;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.GREAT_AXE;
    }
}

