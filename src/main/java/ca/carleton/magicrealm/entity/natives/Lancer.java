package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Vulnerability;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.item.weapon.Spear;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/02/15
 * Time: 12:44 PM
 */
public class Lancer extends AbstractNative {

    public Lancer(final NativeType nativeType) {
        this.vulnerability = Vulnerability.LIGHT;
        this.weapon = new Spear();
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
        return EntityInformation.NATIVE_LANCER;
    }
}

