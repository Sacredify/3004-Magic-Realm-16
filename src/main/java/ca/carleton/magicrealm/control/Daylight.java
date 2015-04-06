package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.strategy.PhaseStrategy;
import ca.carleton.magicrealm.game.phase.strategy.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Groupings of methods to handle the daylight part of a turn.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:50 PM
 */
public class Daylight {

    private static final Logger LOG = LoggerFactory.getLogger(Daylight.class);

    private static final List<PhaseStrategy> phaseStrategies = new ArrayList<PhaseStrategy>() {
        {
            this.add(new MovePhaseStrategy());
            this.add(new HidePhaseStrategy());
            this.add(new TradePhaseStrategy());
            this.add(new AlertPhaseStrategy());
            this.add(new RestPhaseStrategy());
            this.add(new SpellPhaseStrategy());
            this.add(new SearchPhaseStrategy());
        }
    };

    public static void doDaylight(final BoardModel board, final Player player, final List<AbstractPhase> phasesToExecute) {
        LOG.info("Starting daylight process.");
        updateFromBoard(board, player, phasesToExecute);
        process(board, player, phasesToExecute);
    }

    private static void updateFromBoard(final BoardModel board, final Player player, final List<AbstractPhase> phasesToExecute) {
        phasesToExecute.stream().forEach(phase -> phase.updateFromBoard(player, board));
        LOG.info("Updated {} phases' data from the board before beginning daylight.", phasesToExecute);
    }

    private static void process(final BoardModel board, final Player player, final List<AbstractPhase> phasesToExecute) {
        LOG.info("Setting character status to unhidden/unblocked.");
        player.getCharacter().setHidden(false);
        player.getCharacter().setBlocked(false);
        LOG.info("Starting phase execution for player {}.", player.getCharacter());

        phaseLoop:
        for (final AbstractPhase phase : phasesToExecute) {
            for (final PhaseStrategy strategy : phaseStrategies) {
                if (strategy.appliesTo(phase)) {
                    LOG.info("Executing phase {}.", phase);
                    strategy.doPhase(player, phase);
                    LOG.info("Executed phase {}.", phase);
                    LOG.info("Checking to see if the character is blocked...");
                    if (characterNowBlocked(player, board)) {
                        LOG.info("Player is now blocked. All remaining phases will not be executed.");
                        break phaseLoop;
                    } else {
                        LOG.info("Player is hidden/not blocked.");
                    }
                }
            }
        }
        LOG.info("Done executing phases.");
        phasesToExecute.clear();
    }

    /**
     * BLOCKING
     * <p/>
     * Other entities on the map automatically block the player if they are unhidden and on the smae clearing at the end
     * of a phase.
     * <p/>
     * See the rules for details on character vs character blocking in the THIRD ENCOUNTER.
     *
     * @param player the player to check
     * @param board  the board.
     * @return true if the player is now BLOCKED.
     */
    private static boolean characterNowBlocked(final Player player, final BoardModel board) {
        final Clearing playerClearing = board.getClearingForPlayer(player);

        if (player.getCharacter().isBlocked()) {
            return true;
        }

        if (!player.getCharacter().isHidden()) {
            for (final Entity entity : playerClearing.getEntities()) {
                // ASSUMPTION automatic blocking by all entities., why the hell not.
                if (!player.getCharacter().equals(entity)) {
                    LOG.info("Blocked by {}.", entity.getEntityInformation());
                    return true;
                }
            }
        }

        return false;
    }

}
