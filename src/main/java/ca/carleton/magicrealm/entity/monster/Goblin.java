package ca.carleton.magicrealm.entity.monster;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.weapon.MonsterWeapon;

/**
 * Created by Tony on 03/04/2015.
 */
public class Goblin extends AbstractMonster {
    public Goblin() {
        this.vulnerability = Harm.MEDIUM;
        this.weapon = new MonsterWeapon(Harm.MEDIUM);
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.GOBLIN;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        //TODO implement.;
    }
}
