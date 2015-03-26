package ca.carleton.magicrealm.game.phase.impl;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.game.DiceRoller;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;

/**
 * Created with IntelliJ IDEA.
 * Date: 23/02/15
 * Time: 4:14 AM
 */
public class HidePhase extends AbstractPhase {

    private int rollResult = -1;

    public int getRollResult() {
        if (this.rollResult == -1) {
            this.rollResult = DiceRoller.rollTwiceTakeHigher();
        }
        return this.rollResult;
    }

    @Override
    public PhaseType getPhaseType() {
        return PhaseType.HIDE;
    }

    @Override
    public void updateFromBoard(final Player player, final BoardModel board) {
        // do nothing, we don't need to here.
    }

}
