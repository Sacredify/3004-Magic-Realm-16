package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Vulnerability;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.item.weapon.Crossbow;
import ca.carleton.magicrealm.item.weapon.Spear;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/02/15
 * Time: 12:43 PM
 */
public class Crossbowman extends AbstractNative {

    public Crossbowman(final NativeType nativeType) {
        this.vulnerability = Vulnerability.MEDIUM;
        this.weapon = new Crossbow();
        this.basicGoldWage = 2;

        this.faction = nativeType;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        player.getVictoryCondition().addGold(2);
        player.getVictoryCondition().addNotoriety(4);
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.NATIVE_CROSSBOW_MAN;
    }
}

