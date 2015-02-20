package ca.carleton.magicrealm.game.phase.impl;

import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;

/**
 * Created with IntelliJ IDEA.
 * Date: 20/02/15
 * Time: 3:17 PM
 */
public class MovePhase extends AbstractPhase {

    private Clearing moveTarget;

    @Override
    public PhaseType getPhaseType() {
        return PhaseType.MOVE;
    }

    public Clearing getMoveTarget() {
        return this.moveTarget;
    }

    public void setMoveTarget(final Clearing moveTarget) {
        this.moveTarget = moveTarget;
    }
}
