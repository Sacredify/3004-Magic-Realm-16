package ca.carleton.magicrealm.game.combat;

import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/03/15
 * Time: 3:52 PM
 */
public class MeleeSheet implements Serializable {

    private static final long serialVersionUID = 2502936258541118790L;

    private ActionChit encounterStepChit;

    private final Player player;

    MeleeSheet(final Player player) {
        this.player = player;
    }

    public void setEncounterStepChit(final ActionChit chit) {
        this.encounterStepChit = chit;
    }

    public ActionChit getEncounterStepChit() {
        return this.encounterStepChit;
    }

}
