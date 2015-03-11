import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.control.Daylight;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.MovePhase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test the phases within the game.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 20/02/15
 * Time: 3:20 PM
 */
public class MovePhaseTest {

    @Test
    public void canMoveToAdjacentLocation() {

        // Create the board and player
        final BoardGUIModel boardGUIModel = new BoardGUIModel();
        ChitBuilder.placeChits(boardGUIModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        // Set starting location.
        boardGUIModel.getStartingLocation().addEntity(player.getCharacter());

        // Take an adjacent value
        final Clearing moveTarget = boardGUIModel.getStartingLocation().getAdjacentClearings().get(0);

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final MovePhase movePhase = new MovePhase();
        movePhase.setOrigin(boardGUIModel.getClearingForPlayer(player));
        movePhase.setMoveTarget(moveTarget);

        phases.add(movePhase);

        Daylight.processPhasesForPlayer(boardGUIModel, player, phases);
        assertThat(boardGUIModel.getClearingForPlayer(player), is(moveTarget));

    }

    @Test
    public void canIgnoreMoveToInvalidLocation() {
        // Create the board and player
        final BoardGUIModel boardGUIModel = new BoardGUIModel();
        ChitBuilder.placeChits(boardGUIModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        // Set starting location to the first clearing.
        final Clearing startLocation = boardGUIModel.getAllTiles().get(0).getClearings()[0];
        startLocation.addEntity(player.getCharacter());

        // Take a value nowhere near that.
        final Clearing moveTarget = boardGUIModel.getAllTiles().get(boardGUIModel.getAllTiles().size() -1).getClearings()[0];

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final MovePhase movePhase = new MovePhase();
        movePhase.setOrigin(boardGUIModel.getClearingForPlayer(player));
        movePhase.setMoveTarget(moveTarget);

        phases.add(movePhase);

        Daylight.processPhasesForPlayer(boardGUIModel, player, phases);
        assertThat(boardGUIModel.getClearingForPlayer(player), is(startLocation));

    }

}
