package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.game.combat.Harm;
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
        this.strength = Harm.HEAVY;
        this.unAlertedSharpness = 1;
        this.alertedSharpness = 1;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.GREAT_AXE;
    }
}

