import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.control.Daylight;
import ca.carleton.magicrealm.control.Sunrise;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.entity.monster.AbstractMonster;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Tony on 06/04/2015.
 */
public class SunriseTest {

    @Test
    public void canMoveDenizensBackAtEndOfWeek() {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        boardModel.getStartingLocation().addEntity(player.getCharacter());
        // Do first day to set monsters up
        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        Daylight.doDaylight(boardModel, player, phases);
        Sunrise.doSunrise(boardModel, 1);

        AbstractMonster monster = boardModel.getAbstractMonsters().get(0);
        Clearing startingClearing = monster.getStartingClearing();
        Clearing newClearing = monster.getStartingClearing().getAdjacentPaths().get(0).getToClearing();
        monster.getCurrentClearing().getEntities().remove(monster);
        monster.setCurrentClearing(newClearing);
        newClearing.getEntities().add(monster);

        Sunrise.doSunrise(boardModel, 7);

        assertThat(monster.getCurrentClearing(), is(startingClearing));
    }
}
