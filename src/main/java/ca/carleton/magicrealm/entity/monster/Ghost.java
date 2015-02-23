package ca.carleton.magicrealm.entity.monster;

import ca.carleton.magicrealm.entity.*;
import ca.carleton.magicrealm.game.Player;

/**
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 5:26 PM
 */
public class Ghost extends Denizen implements BountyCarrier {

    public Ghost() {
        this.setProwling(true);
        this.vulnerability = Vulnerability.LIGHT;
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.GHOST;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        //TODO implement.;
    }
}
