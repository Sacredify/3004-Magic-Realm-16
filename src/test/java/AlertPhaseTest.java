import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.control.Daylight;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.AlertPhase;
import ca.carleton.magicrealm.item.weapon.AbstractWeapon;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/03/15
 * Time: 12:17 AM
 */
public class AlertPhaseTest {

    @Test
    public void canAlertWeapon() {

        // Create the board and player
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        boardModel.getStartingLocation().addEntity(player.getCharacter());

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final AlertPhase alertPhase = new AlertPhase();

        final AbstractWeapon playerWeapon = (AbstractWeapon) CollectionUtils.find(player.getCharacter().getItems(), new Predicate() {
            @Override
            public boolean evaluate(final Object object) {
                return object instanceof AbstractWeapon;
            }
        });

        alertPhase.setWeapon(playerWeapon);
        phases.add(alertPhase);

        Daylight.processPhasesForPlayer(boardModel, player, phases);
        assertThat(playerWeapon.isAlert(), is(true));

    }
}
