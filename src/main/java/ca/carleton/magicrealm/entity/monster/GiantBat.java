package ca.carleton.magicrealm.entity.monster;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.game.Player;

/**
 * Created by Tony on 31/03/2015.
 */
public class GiantBat extends AbstractMonster {
    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.GIANTBAT;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        //TODO implement.;
    }
}
