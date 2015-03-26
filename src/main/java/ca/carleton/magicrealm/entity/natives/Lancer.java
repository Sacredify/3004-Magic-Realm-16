package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.weapon.Spear;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/02/15
 * Time: 12:44 PM
 */
public class Lancer extends AbstractNative {

    protected Lancer(final NativeFaction nativeType) {
        this.vulnerability = Harm.LIGHT;
        this.weapon = new Spear();
        this.basicGoldWage = 2;

        this.faction = nativeType;
        this.moveStrength = Harm.LIGHT;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        player.getCharacter().addGold(2);
        player.getCharacter().addNotoriety(4);
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.NATIVE_LANCER;
    }
}

