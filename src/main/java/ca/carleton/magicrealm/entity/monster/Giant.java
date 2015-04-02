package ca.carleton.magicrealm.entity.monster;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.weapon.GreatSword;

/**
 * Created by Tony on 22/03/2015.
 */
public class Giant extends AbstractMonster {

    public Giant() {
        this.vulnerability = Harm.TREMENDOUS;
        this.weapon = new GreatSword();
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.GIANT;
    }

    @Override
    public void addBountyToPlayer(final Player player) {
        //TODO implement.;
    }
}
