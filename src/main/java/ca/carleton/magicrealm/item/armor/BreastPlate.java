package ca.carleton.magicrealm.item.armor;

import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:40 AM
 */
public class BreastPlate extends AbstractArmor {

    public BreastPlate() {
        this.goldValue = 9;
        this.protectsAgainst = ProtectionType.THRUST_AND_SWING;
        this.weight = Harm.MEDIUM;
    }

    public ItemInformation getItemInformation() {
        return ItemInformation.BREASTPLATE;
    }
}
