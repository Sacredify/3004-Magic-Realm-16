package ca.carleton.magicrealm.game.phase.impl;

import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
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

    public int getRollResult() {
        return DiceRoller.rollTwiceTakeHigher();
    }

    @Override
    public PhaseType getPhaseType() {
        return PhaseType.HIDE;
    }

    @Override
    public void updateFromBoard(final Player player, final BoardGUIModel board) {
        // do nothing, we don't need to here.
    }

}
