package ca.carleton.magicrealm.entity.monster;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.weapon.MonsterWeapon;

/**
 * Created by Tony on 31/03/2015.
 */
public class Dragon extends AbstractMonster {

    public Dragon() {
        this.isArmored = true;
        this.vulnerability = Harm.TREMENDOUS;
        this.weapon = new MonsterWeapon(Harm.TREMENDOUS);
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.DRAGON;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        player.getCharacter().addFame(10);
        player.getCharacter().addNotoriety(10);
    }
}
