package ca.carleton.magicrealm.game.phase.strategy;

import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.game.phase.impl.MovePhase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:34 PM
 */
public class MovePhaseStrategy implements PhaseStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(MovePhaseStrategy.class);

    @Override
    public boolean appliesTo(final AbstractPhase phase) {
        return phase.getPhaseType() == PhaseType.MOVE;
    }

    @Override
    public void doPhase(final Player player, final AbstractPhase phase) {
        Clearing temp = player.getCurrentClearing();


        if (temp.getAdjacentClearings().contains(((MovePhase) phase).getMoveTarget())) {
            player.getCurrentClearing().removeEntity(player.getCharacter());
            player.setCurrentClearing(((MovePhase) phase).getMoveTarget());
            player.getCurrentClearing().addEntity(player.getCharacter());
            LOG.info("Moved player from {} to {}.", temp, player.getCurrentClearing());
        }  else {
            LOG.info("Played entered invalid location for his move phase. Not executed.");
        }
    }
}
