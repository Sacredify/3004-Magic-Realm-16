package ca.carleton.magicrealm.game.phase.impl;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.game.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 23/02/15
 * Time: 4:14 AM
 */
public class HidePhase extends AbstractPhase {

    private static final Logger LOG = LoggerFactory.getLogger(HidePhase.class);

    private int rollResult = -1;

    private Player player;

    public int getRollResult() {
        if (this.rollResult == -1) {
            this.rollResult = Table.HideTable.roll(this.player);
        }
        return this.rollResult;
    }

    @Override
    public PhaseType getPhaseType() {
        return PhaseType.HIDE;
    }

    @Override
    public void updateFromBoard(final Player player, final BoardModel board) {
       this.player = player;
        LOG.info("Updated player hiding.");
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }
}
