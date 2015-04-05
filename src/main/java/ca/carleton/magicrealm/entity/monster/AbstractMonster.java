package ca.carleton.magicrealm.entity.monster;

import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.game.combat.Harm;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/02/15
 * Time: 12:56 PM
 */
public abstract class AbstractMonster extends Denizen {

    public Harm getMoveStrength() {
        return this.vulnerability;
    }

    /*
    public ActionChit getAttackChit() {
        return new ActionChit();
    }
     */
}
