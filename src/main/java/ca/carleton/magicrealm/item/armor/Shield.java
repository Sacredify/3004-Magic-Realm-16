package ca.carleton.magicrealm.item.armor;

import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:41 AM
 */
public class Shield extends AbstractArmor {

    public Shield() {
        this.goldValue = 7;
        this.protectsAgainst = ProtectionType.ONE_DIRECTION;
        this.weight = Harm.MEDIUM;
    }

    public ItemInformation getItemInformation() {
        return ItemInformation.SHIELD;
    }
}
