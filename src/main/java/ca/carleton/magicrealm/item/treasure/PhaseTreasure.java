package ca.carleton.magicrealm.item.treasure;

import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.item.ItemInformation;

public class PhaseTreasure extends Treasure {

    private static final long serialVersionUID = 2765585768861640566L;

    private final PhaseType phaseAffected;

    public PhaseTreasure(final PhaseType phaseAffected, final ItemInformation treasureInformation) {
        super(treasureInformation);
        this.phaseAffected = phaseAffected;
    }

    public PhaseType getPhaseAffected() {
        return this.phaseAffected;
    }

}
