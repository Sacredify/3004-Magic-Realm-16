import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.control.Daylight;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.HidePhase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * Date: 11/03/15
 * Time: 5:51 PM
 */
public class HidePhaseTest {

    @Test
    public void canHideSuccessfully() {

        // Create the board and player
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        boardModel.getStartingLocation().addEntity(player.getCharacter());

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final HidePhase hidePhase = new HidePhase();
        phases.add(hidePhase);

        Daylight.processPhasesForPlayer(boardModel, player, phases);

        // Only should fail on 6.
        if (hidePhase.getRollResult() == 6) {
            assertThat(player.getCharacter().isHidden(), is(false));
        } else {
            assertThat(player.getCharacter().isHidden(), is(true));
        }

    }
}
