package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Vulnerability;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.item.weapon.ShortSword;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/02/15
 * Time: 12:46 PM
 */
public class Assassin extends AbstractNative {

    public Assassin(final NativeType nativeType) {
        this.vulnerability = Vulnerability.MEDIUM;
        this.weapon = new ShortSword();
        this.basicGoldWage = 1;

        this.faction = nativeType;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        player.getVictoryCondition().addGold(1);
        player.getVictoryCondition().addNotoriety(2);
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.NATIVE_ASSASSIN;
    }
}
