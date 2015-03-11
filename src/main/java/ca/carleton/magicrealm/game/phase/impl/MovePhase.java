package ca.carleton.magicrealm.game.phase.impl;

import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 20/02/15
 * Time: 3:17 PM
 */
public class MovePhase extends AbstractPhase {

    public static final Logger LOG = LoggerFactory.getLogger(MovePhase.class);

    private Clearing moveTarget;

    private Clearing origin;

    @Override
    public PhaseType getPhaseType() {
        return PhaseType.MOVE;
    }

    @Override
    public void updateFromBoard(final Player player, final BoardGUIModel board) {
        for (final AbstractTile tile : board.getAllTiles()) {
            for (final Clearing clearing : tile.getClearings()) {
                if (this.moveTarget.equals(clearing)) {
                    this.moveTarget = clearing;
                    LOG.info("Updated move target from board.");
                }
                if (this.origin.equals(clearing)) {
                    this.origin = clearing;
                    LOG.info("Updated origin from board.");
                }
            }
        }
    }

    public Clearing getMoveTarget() {
        return this.moveTarget;
    }

    public void setMoveTarget(final Clearing moveTarget) {
        this.moveTarget = moveTarget;
    }

    public Clearing getOrigin() {
        return this.origin;
    }

    public void setOrigin(final Clearing origin) {
        this.origin = origin;
    }
}
