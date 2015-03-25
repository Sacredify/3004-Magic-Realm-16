package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:19 AM
 */
public class MorningStar extends AbstractWeapon {

    public MorningStar() {
        this.goldValue = 8;
        this.length = 6;
        this.attackType = AttackType.STRIKING;
        this.strength = Harm.HEAVY;
        this.unAlertedSharpness = 0;
        this.alertedSharpness = 0;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.MORNING_STAR;
    }
}

