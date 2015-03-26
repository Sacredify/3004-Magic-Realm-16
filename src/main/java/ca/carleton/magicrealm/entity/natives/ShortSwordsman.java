package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.weapon.ShortSword;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/02/15
 * Time: 12:43 PM
 */
public class ShortSwordsman extends AbstractNative {

    protected ShortSwordsman(final NativeFaction nativeType) {
        this.vulnerability = Harm.MEDIUM;
        this.weapon = new ShortSword();
        this.basicGoldWage = 2;

        this.faction = nativeType;
        this.moveStrength = Harm.MEDIUM;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        player.getCharacter().addGold(2);
        player.getCharacter().addNotoriety(3);
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.NATIVE_SHORT_SWORDSMAN;
    }
}

