package ca.carleton.magicrealm.item.weapon;

import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 02/04/2015
 * Time: 4:09 AM
 */
public class MonsterWeapon extends AbstractWeapon {

    private static final long serialVersionUID = -9022158245449896024L;

    public MonsterWeapon(final Harm strength) {
        this.attackType = AttackType.STRIKING;
        this.length = 0;
        this.strength = strength;
    }

    @Override
    public ItemInformation getItemInformation() {
        return ItemInformation.MONSTER;
    }
}
