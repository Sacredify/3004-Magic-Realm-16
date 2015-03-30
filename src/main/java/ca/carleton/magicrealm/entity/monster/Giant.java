package ca.carleton.magicrealm.entity.monster;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.game.Player;

/**
 * Created by Tony on 22/03/2015.
 */
public class Giant extends Denizen {
    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.GIANT;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        //TODO implement.;
    }
}
