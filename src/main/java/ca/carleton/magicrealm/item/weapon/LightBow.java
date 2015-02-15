package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 8:57 AM
 */
public class LightBow extends AbstractWeapon {

    public LightBow() {
        this.goldValue = 6;
        this.length = 14;
        this.attackType = AttackType.MISSILE;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.LIGHT_BOW;
    }
}
