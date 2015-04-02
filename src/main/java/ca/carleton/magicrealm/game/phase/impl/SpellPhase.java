package ca.carleton.magicrealm.game.phase.impl;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 02/04/2015
 * Time: 2:30 AM
 */
public class SpellPhase extends AbstractPhase {

    private static final Logger LOG = LoggerFactory.getLogger(SpellPhase.class);

    private BoardModel board;

    @Override
    public PhaseType getPhaseType() {
        return PhaseType.SPELL;
    }

    @Override
    public void updateFromBoard(final Player player, final BoardModel board) {
        this.board = board;
        LOG.info("Updated board from server for enchant.");
    }

    public BoardModel getBoard() {
        return this.board;
    }

}
