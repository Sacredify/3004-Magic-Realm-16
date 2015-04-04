package ca.carleton.magicrealm.entity.monster;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.Harm;

/**
 * Created by Tony on 31/03/2015.
 */
public class Spider extends AbstractMonster {

    public Spider() {
        this.vulnerability = Harm.HEAVY;
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.SPIDER;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        player.getCharacter().addFame(3);
        player.getCharacter().addNotoriety(3);
    }
}
