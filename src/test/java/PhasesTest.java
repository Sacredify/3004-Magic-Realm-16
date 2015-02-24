import ca.carleton.magicrealm.GUI.tile.impl.CurstValley;
import ca.carleton.magicrealm.control.Daylight;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.MovePhase;
import org.junit.Test;

import java.util.ArrayList;

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
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        final CurstValley valley = new CurstValley();

        player.setCurrentClearing(valley.getClearingAt(3));

        valley.getClearingAt(3).connectTo(valley.getClearingAt(1));

        // A move phases
        final MovePhase movePhaseChosen = new MovePhase();
        movePhaseChosen.setMoveTarget(valley.getClearingAt(1));

        final ArrayList<AbstractPhase> phases = new ArrayList<>();
        phases.add(movePhaseChosen);

        // Execute phase
        Daylight.processPhasesForPlayer(player, phases);

        assertThat(player.getCurrentClearing(), is(valley.getClearingAt(1)));

    }

}
