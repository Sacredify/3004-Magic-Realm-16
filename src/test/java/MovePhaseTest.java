import ca.carleton.magicrealm.GUI.board.BoardModel;
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
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        // Set starting location.
        boardModel.getStartingLocation().addEntity(player.getCharacter());

        // Take an adjacent value
        final Clearing moveTarget = boardModel.getStartingLocation().getAdjacentPaths().get(0).getToClearing();

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final MovePhase movePhase = new MovePhase();
        movePhase.setOrigin(boardModel.getClearingForPlayer(player));
        movePhase.setMoveTarget(moveTarget);

        phases.add(movePhase);

        Daylight.processPhasesForPlayer(boardModel, player, phases);
        assertThat(boardModel.getClearingForPlayer(player), is(moveTarget));

    }

    @Test
    public void canIgnoreMoveToInvalidLocation() {

        // Create the board and player
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        // Set starting location to the first clearing.
        final Clearing startLocation = boardModel.getAllTiles().get(0).getClearings()[0];
        startLocation.addEntity(player.getCharacter());

        // Take a value nowhere near that.
        final Clearing moveTarget = boardModel.getAllTiles().get(boardModel.getAllTiles().size() -1).getClearings()[0];

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final MovePhase movePhase = new MovePhase();
        movePhase.setOrigin(boardModel.getClearingForPlayer(player));
        movePhase.setMoveTarget(moveTarget);

        phases.add(movePhase);

        Daylight.processPhasesForPlayer(boardModel, player, phases);
        assertThat(boardModel.getClearingForPlayer(player), is(startLocation));

    }

}
