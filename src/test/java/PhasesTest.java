import ca.carleton.magicrealm.GUI.tile.impl.BadValley;
import ca.carleton.magicrealm.GUI.tile.impl.Crag;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.impl.MovePhase;
import ca.carleton.magicrealm.control.Daylight;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test the phases within the game.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 20/02/15
 * Time: 3:20 PM
 */
public class PhasesTest {

    @Test
    public void canMovePhase() {

        final Player player = new Player();

        final BadValley badValley = new BadValley();
        final Crag crag = new Crag();

        player.setCurrentClearing(badValley.getClearingAt(1));

        // A move phases
        final MovePhase movePhaseChosen = new MovePhase();
        movePhaseChosen.setMoveTarget(crag.getClearingAt(2));


        // Execute phase
        Daylight.processPhaseForPlayer(player, movePhaseChosen);

        assertThat(player.getCurrentClearing(), is(crag.getClearingAt(2)));

    }

}
