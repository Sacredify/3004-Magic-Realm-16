package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.weapon.GreatAxe;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/02/15
 * Time: 12:41 PM
 */
public class GreatAxeman extends AbstractNative {

    protected GreatAxeman(final NativeFaction nativeType) {
        this.vulnerability = Harm.HEAVY;
        this.weapon = new GreatAxe();
        this.basicGoldWage = 4;

        this.faction = nativeType;
        this.moveStrength = Harm.HEAVY;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        player.getCharacter().addGold(4);
        player.getCharacter().addNotoriety(6);
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.NATIVE_GREAT_AXEMAN;
    }
}
