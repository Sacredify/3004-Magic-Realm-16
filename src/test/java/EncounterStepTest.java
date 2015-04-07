import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.GUI.board.EntityBuilder;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.Path;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.control.Combat;
import ca.carleton.magicrealm.control.Sunset;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.entity.monster.AbstractMonster;
import ca.carleton.magicrealm.entity.monster.Dragon;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.MeleeSheet;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

/**
 * Created with IntelliJ IDEA.
 * Date: 07/04/2015
 * Time: 12:04 PM
 */
public class EncounterStepTest {

    @Test
    public void canRunAway() {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        EntityBuilder.placeEntities(boardModel);

        final Player player = new Player();
        boardModel.addPlayer(player);
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        player.getCharacter().setHidden(false);
        boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[1].addEntity(player.getCharacter());

        boardModel.createNewMeleeSheet(player);
        final MeleeSheet theSheet = boardModel.getMeleeSheet(player);
        theSheet.setRunningAway(true);

        final AbstractMonster monster = new Dragon();
        boardModel.createNewMeleeSheet(monster);
        boardModel.getStartingLocation().addEntity(monster);

        // Set location to the same tile, but diff clearing.
        boardModel.createNewMeleeSheet(monster);
        boardModel.getAbstractMonsters().add(monster);
        boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[1].addEntity(monster);
        monster.setCurrentClearing(boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[1]);
        monster.setProwling(true);

        // Monster moves to clearing
        Sunset.doSunset(boardModel);

        final Clearing previousClearing = boardModel.getClearingForPlayer(player);

        // Combat, but we're running away so nothing resolves. Check that they actually moved to a new clearing.
        assertThat(Combat.process(Arrays.asList(player), boardModel), is(0));
        assertThat(previousClearing.getEntities().contains(player.getCharacter()), is(false));
        assertThat(boardModel.getClearingForPlayer(player), is(not(previousClearing)));

        // Check that they actually moved to an adjacent clearing.
        final List<Clearing> adjacentClearings = boardModel.getClearingForPlayer(player).getAdjacentPaths().stream()
                .map(Path::getToClearing).collect(Collectors.toList());

        assertThat(adjacentClearings.contains(previousClearing), is(true));

    }

}
