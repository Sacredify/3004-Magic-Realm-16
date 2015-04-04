package ca.carleton.magicrealm.entity.monster;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.Harm;

/**
 * Created by Tony on 31/03/2015.
 */
public class Troll extends AbstractMonster {

    public Troll() {
        this.isArmored = true;
        this.vulnerability = Harm.HEAVY;
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.TROLL;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        player.getCharacter().addFame(5);
        player.getCharacter().addNotoriety(5);
    }
}
