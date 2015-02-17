package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Vulnerability;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.item.weapon.GreatAxe;
import ca.carleton.magicrealm.item.weapon.Spear;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/02/15
 * Time: 12:42 PM
 */
public class Pikeman extends AbstractNative {

    protected Pikeman(final NativeFaction nativeType) {
        this.vulnerability = Vulnerability.MEDIUM;
        this.weapon = new Spear();
        this.basicGoldWage = 2;

        this.faction = nativeType;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        player.getVictoryCondition().addGold(2);
        player.getVictoryCondition().addNotoriety(3);
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.NATIVE_PIKEMAN;
    }
}

