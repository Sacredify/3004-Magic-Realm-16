package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.monster.AbstractMonster;
import ca.carleton.magicrealm.entity.monster.Ghost;
import ca.carleton.magicrealm.entity.natives.AbstractNative;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.strategy.MovePhaseStrategy;
import ca.carleton.magicrealm.game.phase.strategy.PhaseStrategy;
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

    private static final List<PhaseStrategy> phaseStrategies = new ArrayList<PhaseStrategy>();

    /**
     * Initialize the strategies to use.
     */
    static {
        phaseStrategies.add(new MovePhaseStrategy());
    }

    public static void processPhasesForPlayer(final Player player, final List<AbstractPhase> phasesToExecute) {
        LOG.info("Starting phase execution for player {}.", player);

        phaseLoop:
        for (final AbstractPhase phase : phasesToExecute) {
            for (final PhaseStrategy strategy : phaseStrategies) {
                if (strategy.appliesTo(phase)) {
                    strategy.doPhase(player, phase);
                    LOG.info("Executed phase {}.", phase);
                    LOG.info("Checking to see if the character is blocked...");
                    if (characterNowBlocked(player)) {
                        LOG.info("Player is now blocked. All remaining phases will not be executed.");
                        break phaseLoop;
                    } else {
                        LOG.info("Player is hidden/not blocked.");
                    }
                }
            }
        }
        LOG.info("Done executing phases.");

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
     * @return true if the player is now BLOCKED.
     */
    public static boolean characterNowBlocked(final Player player) {
        final Clearing playerClearing = player.getCurrentClearing();

        if (!player.getCharacter().isHidden()) {
            for (final Entity entity : playerClearing.getEntities()) {
                // Only natives, monsters and ghosts block for now.
                // TODO Add visitors and characters when required.
                if (entity instanceof AbstractNative || entity instanceof AbstractMonster || entity instanceof Ghost) {
                    return true;
                }
            }
        }

        return false;
    }

}
