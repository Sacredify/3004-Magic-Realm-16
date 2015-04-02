import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.control.Daylight;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.SpellPhase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * Date: 02/04/2015
 * Time: 2:52 AM
 */
public class SpellPhaseTest {

    @Test
    public void canEnchantAndDisenchantTile() {

        // Create the board and player
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        boardModel.getStartingLocation().addEntity(player.getCharacter());

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final SpellPhase enchant = new SpellPhase();
        final SpellPhase disenchant = new SpellPhase();

        // Enchant first.
        phases.add(enchant);
        Daylight.doDaylight(boardModel, player, phases);
        assertThat(boardModel.getStartingLocation().getParentTile().isEnchanted(), is(true));

        // Then try and disenchant.
        phases.add(disenchant);
        Daylight.doDaylight(boardModel, player, phases);
        assertThat(boardModel.getStartingLocation().getParentTile().isEnchanted(), is(false));
    }
}
