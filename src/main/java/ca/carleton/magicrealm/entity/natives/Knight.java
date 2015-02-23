package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Vulnerability;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.item.weapon.BroadSword;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 7:15 PM
 */
public class Knight extends AbstractNative {

    protected Knight(final NativeFaction nativeType) {
        this.vulnerability = Vulnerability.TREMENDOUS;
        this.weapon = new BroadSword();
        this.basicGoldWage = 8;

        this.faction = nativeType;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        player.getCharacter().addGold(8);
        player.getCharacter().addNotoriety(12);
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.NATIVE_KNIGHT;
    }
}
