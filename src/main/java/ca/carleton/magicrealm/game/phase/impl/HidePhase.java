package ca.carleton.magicrealm.game.phase.impl;

import ca.carleton.magicrealm.game.DiceRoller;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;

/**
 * Created with IntelliJ IDEA.
 * Date: 23/02/15
 * Time: 4:14 AM
 */
public class HidePhase extends AbstractPhase {

    public int getRollResult() {
        return DiceRoller.rollOnce();
    }

    @Override
    public PhaseType getPhaseType() {
        return PhaseType.HIDE;
    }
}
