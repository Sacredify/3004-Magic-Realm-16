package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.MovePhase;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 22/02/15
 * Time: 7:02 PM
 */
public class PhaseSelectorModel {

    private final List<AbstractPhase> phases;

    public PhaseSelectorModel(final List<AbstractPhase> phases) {
        this.phases = phases;
    }

    public void addMovementPhase(final Clearing clearing) {
        final MovePhase movePhase = new MovePhase();
        movePhase.setMoveTarget(clearing);
        this.phases.add(movePhase);
    }

}
