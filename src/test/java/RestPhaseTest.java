import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.control.Daylight;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.game.combat.chit.ActionType;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.RestPhase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * Date: 30/03/2015
 * Time: 4:57 PM
 */
public class RestPhaseTest {

    @Test
    public void canRestAChitFromWoundedToFatigued() {

        // Create the board and player
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        boardModel.getStartingLocation().addEntity(player.getCharacter());

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final RestPhase restPhase = new RestPhase();

        final ActionChit theChit = new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(1).withStrength(Harm.MEDIUM).withTime(4).build();
        theChit.setWounded(true);

        restPhase.setSelectedChit(theChit);
        phases.add(restPhase);

        Daylight.processPhasesForPlayer(boardModel, player, phases);
        assertThat(theChit.isFatigued(), is(true));
        assertThat(theChit.isWounded(), is(false));
    }

    @Test
    public void canRestAChitFromFatiguedToNormal() {

        // Create the board and player
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        boardModel.getStartingLocation().addEntity(player.getCharacter());

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final RestPhase restPhase = new RestPhase();

        final ActionChit theChit = new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(1).withStrength(Harm.MEDIUM).withTime(4).build();
        theChit.setFatigued(true);

        restPhase.setSelectedChit(theChit);
        phases.add(restPhase);

        Daylight.processPhasesForPlayer(boardModel, player, phases);
        assertThat(theChit.isFatigued(), is(false));
        assertThat(theChit.isWounded(), is(false));
    }

}
