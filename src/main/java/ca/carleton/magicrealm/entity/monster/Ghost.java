package ca.carleton.magicrealm.entity.monster;

import ca.carleton.magicrealm.entity.BountyCarrier;
import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.Harm;

/**
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 5:26 PM
 */
public class Ghost extends Denizen implements BountyCarrier {

    public Ghost() {
        this.setProwling(true);
        this.vulnerability = Harm.MEDIUM;
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.GHOST;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        player.getCharacter().addNotoriety(2);
    }
}
