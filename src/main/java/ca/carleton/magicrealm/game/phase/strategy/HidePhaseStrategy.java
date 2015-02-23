package ca.carleton.magicrealm.game.phase.strategy;

import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.game.phase.impl.HidePhase;

/**
 * Created with IntelliJ IDEA.
 * Date: 23/02/15
 * Time: 4:19 AM
 */
public class HidePhaseStrategy implements PhaseStrategy {

    @Override
    public boolean appliesTo(final AbstractPhase phase) {
        return phase.getPhaseType() == PhaseType.HIDE;
    }

    @Override
    public void doPhase(final Player player, final AbstractPhase phase) {

        /*
        HIDE TABLE
        1 - 5 : Hide successful.
        6 : Hide unsuccessful.
         */
        if (((HidePhase) phase).getRollResult() == 6) {
            player.getCharacter().setHidden(false);
        } else {
            player.getCharacter().setHidden(true);
        }
    }
}
