package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:15 AM
 */
public class GreatSword extends AbstractWeapon {

    public GreatSword() {
        this.goldValue = 10;
        this.length = 8;
        this.attackType = AttackType.STRIKING;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.GREAT_SWORD;
    }
}
