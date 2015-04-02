import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.GUI.phaseselector.detailwindows.SearchSelectionPanel;
import ca.carleton.magicrealm.GUI.tile.Path;
import ca.carleton.magicrealm.control.Daylight;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.SearchPhase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Tony on 02/04/2015.
 */
public class SearchPhaseTest {

    @Test
    public void canSearchPathByLocate() {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        boardModel.getStartingLocation().addEntity(player.getCharacter());

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final SearchPhase searchPhase = new SearchPhase();

        Path path = boardModel.getStartingLocation().getAdjacentPaths().get(0);
        path.setHidden(true);

        searchPhase.setAction(SearchSelectionPanel.LOCATE_TEXT);

        phases.add(searchPhase);

        Daylight.doDaylight(boardModel, player, phases);

        if (searchPhase.getRoll() == 1 || searchPhase.getRoll() == 2 || searchPhase.getRoll() == 3)
            assertThat(player.getDiscoveredThings().get(0), is(path));
    }
}
