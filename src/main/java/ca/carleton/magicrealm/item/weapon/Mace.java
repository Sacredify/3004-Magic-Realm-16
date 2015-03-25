package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.game.combat.Harm;
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
        this.strength = Harm.MEDIUM;
        this.unAlertedSharpness = 0;
        this.alertedSharpness = 0;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.MACE;
    }
}
