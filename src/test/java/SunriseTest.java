import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.control.Daylight;
import ca.carleton.magicrealm.control.Sunset;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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



        Sunset.doSunset(boardModel);
    }
}
