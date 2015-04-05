import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.PhaseCountBean;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.game.phase.PhaseUtils;
import ca.carleton.magicrealm.item.treasure.TreasureCollection;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
        assertThat(phaseCountBeanAmazon.getExtraPhases().size(), is(1));
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
        assertThat(phaseCountBeanAmazon.getExtraPhases().size(), is(2));
        assertThat(phaseCountBeanAmazon.getExtraPhases().contains(PhaseType.MOVE), is(true));
        assertThat(phaseCountBeanAmazon.getExtraPhases().contains(PhaseType.TRADE), is(true));
    }

    @Test
    public void canAddAndRemoveWorthOfTreasureToCharacter() {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        boardModel.getStartingLocation().addEntity(player.getCharacter());

        // For testing, we'll just make a new treasure collection
        TreasureCollection collection = new TreasureCollection();
        // Add regent of jewels.
        player.getCharacter().addItem(collection.treasures[11]);

        final int goldValue = collection.treasures[11].getGoldValue();
        final int fameValue = collection.treasures[11].getFame();
        final int notorietyValue = collection.treasures[11].getNotoriety();
        final boolean great = collection.treasures[11].isGreatTreasure();

        assertThat(player.getCharacter().getCurrentGold(), is(10 + goldValue));
        assertThat(player.getCharacter().getCurrentFame(), is(fameValue));
        assertThat(player.getCharacter().getCurrentNotoriety(), is(notorietyValue));
        assertThat(player.getCharacter().getCurrentGreatTreasuresCount(), great ? is(1) : is(0));

        player.getCharacter().removeItem(collection.treasures[11]);
        assertThat(player.getCharacter().getCurrentGold(), is(10));
        assertThat(player.getCharacter().getCurrentFame(), is(0));
        assertThat(player.getCharacter().getCurrentNotoriety(), is(0));
        assertThat(player.getCharacter().getCurrentGreatTreasuresCount(), is(0));
    }

    @Test
    public void canTestTreasuresWithConditions() {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        boardModel.getTilesOfType(TileType.MOUNTAIN).get(0).getClearings()[0].addEntity(player.getCharacter());

        // For testing, we'll just make a new treasure collection
        TreasureCollection collection = new TreasureCollection();
        // Add ancient of telescope...only works in mountains.
        player.getCharacter().addItem(collection.treasures[8]);

        final PhaseCountBean phaseCountBeanAmazon = PhaseUtils.getNumberOfPhasesForPlayer(player, boardModel);
        assertThat(phaseCountBeanAmazon.getNumberOfPhasesFOrDay(), is(4));
        assertThat(phaseCountBeanAmazon.getExtraPhases().size(), is(2));
        assertThat(phaseCountBeanAmazon.getExtraPhases().contains(PhaseType.SEARCH), is(true));
        assertThat(phaseCountBeanAmazon.getExtraPhases().contains(PhaseType.MOVE), is(true));

        // Now put him somewhere not mountains. Since he will be in cave, only 2 phases (cave restriction).
        boardModel.getTilesOfType(TileType.MOUNTAIN).get(0).getClearings()[0].removeEntity(player.getCharacter());
        boardModel.getTilesOfType(TileType.CAVE).get(0).getClearings()[0].addEntity(player.getCharacter());

        final PhaseCountBean phaseCountBeanAmazon2 = PhaseUtils.getNumberOfPhasesForPlayer(player, boardModel);
        assertThat(phaseCountBeanAmazon2.getNumberOfPhasesFOrDay(), is(2));
        assertThat(phaseCountBeanAmazon2.getExtraPhases().size(), is(1));
        assertThat(phaseCountBeanAmazon2.getExtraPhases().contains(PhaseType.MOVE), is(true));
    }

}
