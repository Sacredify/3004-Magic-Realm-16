import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.control.Sunset;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.entity.monster.AbstractMonster;
import ca.carleton.magicrealm.entity.monster.Dragon;
import ca.carleton.magicrealm.game.Player;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

/**
 * Created with IntelliJ IDEA.
 * Date: 05/04/2015
 * Time: 7:53 PM
 */
public class SunsetTest {

    @Test
    public void canMoveMonsterToPlayer() {

        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);

        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[0].addEntity(player.getCharacter());

        final AbstractMonster monster = new Dragon();
        boardModel.getStartingLocation().addEntity(monster);

        // Set location to the same tile, but diff clearing.
        boardModel.getAbstractMonsters().add(monster);
        boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[1].addEntity(monster);
        monster.setCurrentClearing(boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[1]);
        monster.setProwling(true);

        Sunset.doSunset(boardModel);

        assertThat(boardModel.getClearingForPlayer(player).getEntities().size(), is(2));
        assertThat(boardModel.getClearingForPlayer(player).getEntities(), containsInAnyOrder(player.getCharacter(), monster));

    }

}
