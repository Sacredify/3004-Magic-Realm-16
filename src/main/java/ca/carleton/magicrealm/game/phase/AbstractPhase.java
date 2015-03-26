package ca.carleton.magicrealm.game.phase;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.game.Player;

/**
 * Contains data for executing phases.
 *
 * Created with IntelliJ IDEA.
 * Date: 20/02/15
 * Time: 3:13 PM
 */
public abstract class AbstractPhase {

    public abstract PhaseType getPhaseType();

    public abstract void updateFromBoard(final Player player, final BoardModel board);

}
