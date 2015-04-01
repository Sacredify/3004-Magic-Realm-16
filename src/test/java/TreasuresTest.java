import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.PhaseCountBean;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.game.phase.PhaseUtils;
import ca.carleton.magicrealm.item.treasure.TreasureCollection;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Testing various treasures, their effects... etc.
 * Created with IntelliJ IDEA.
 * Date: 01/04/2015
 * Time: 6:24 PM
 */
public class TreasuresTest {

    @Test
    public void canGetBaseNumberOfPhasesWithNoTreasures() {

        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player playerOne = new Player();
        playerOne.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        boardModel.getStartingLocation().addEntity(playerOne.getCharacter());

        final Player playerTwo = new Player();
        playerTwo.setCharacter(CharacterFactory.createCharacter(CharacterType.DWARF));
        boardModel.getStartingLocation().addEntity(playerTwo.getCharacter());

        final PhaseCountBean phaseCountBeanAmazon = PhaseUtils.getNumberOfPhasesForPlayer(playerOne, boardModel);
        final PhaseCountBean phaseCountBeanDwarf = PhaseUtils.getNumberOfPhasesForPlayer(playerTwo, boardModel);

        // Amazon has 4 phases, and 1 extra move phase.
        assertThat(phaseCountBeanAmazon.getNumberOfPhasesFOrDay(), is(4));
        assertThat(phaseCountBeanAmazon.getExtraPhases().size() , is(1));
        assertThat(phaseCountBeanAmazon.getExtraPhases().contains(PhaseType.MOVE), is(true));

        // Dwarf has 2 phases, with no extras by default (he isn't in a cave).
        assertThat(phaseCountBeanDwarf.getNumberOfPhasesFOrDay(), is(2));
        assertThat(phaseCountBeanDwarf.getExtraPhases().size(), is(0));
    }

    @Test
    public void canGetNumberOfPhasesWithExtraTradePhase() {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        boardModel.getStartingLocation().addEntity(player.getCharacter());

        // For testing, we'll just make a new treasure collection
        TreasureCollection collection = new TreasureCollection();
        // Add regent of jewels.
        player.getCharacter().addItem(collection.treasures[11]);

        final PhaseCountBean phaseCountBeanAmazon = PhaseUtils.getNumberOfPhasesForPlayer(player, boardModel);

        assertThat(phaseCountBeanAmazon.getNumberOfPhasesFOrDay(), is(4));
        assertThat(phaseCountBeanAmazon.getExtraPhases().size() , is(2));
        assertThat(phaseCountBeanAmazon.getExtraPhases().contains(PhaseType.MOVE), is(true));
        assertThat(phaseCountBeanAmazon.getExtraPhases().contains(PhaseType.TRADE), is(true));
    }

}
