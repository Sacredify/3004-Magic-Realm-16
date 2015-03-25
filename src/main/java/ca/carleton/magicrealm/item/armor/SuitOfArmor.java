package ca.carleton.magicrealm.item.armor;

import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:33 AM
 */
public class SuitOfArmor extends AbstractArmor {

    public SuitOfArmor() {
        this.goldValue = 17;
        this.protectsAgainst = ProtectionType.ALL_DIRECTIONS;
        this.weight = Harm.HEAVY;
    }

    public ItemInformation getItemInformation() {
        return ItemInformation.SUIT_OF_ARMOR;
    }
}
