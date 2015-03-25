package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 25/03/2015
 * Time: 2:59 PM
 */
public class Dagger extends AbstractWeapon {

    public Dagger() {
        this.strength = Harm.NEGLIGIBLE;
        this.attackType = AttackType.STRIKING;
        this.length = 0;
        this.unAlertedSharpness = 1;
        this.alertedSharpness =1;
    }

    @Override
    public ItemInformation getItemInformation() {
        return null;
    }
}
