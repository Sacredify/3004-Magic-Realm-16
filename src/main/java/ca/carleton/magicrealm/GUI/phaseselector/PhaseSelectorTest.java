package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.GUI.board.EntityBuilder;
import ca.carleton.magicrealm.control.GameController;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.entity.chit.Dwelling;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Date: 23/02/15
 * Time: 2:54 AM
 */
public class PhaseSelectorTest {

    public static void main(final String[] args) {

        final GameController controller = new GameController();

        controller.setBoardModel(new BoardGUIModel());
        ChitBuilder.placeChits(controller.getBoardModel());
        EntityBuilder.placeEntities(controller.getBoardModel());

        final Player player = new Player();
        player.setStartingLocation(Dwelling.INN);
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        new PhaseSelectorMenu(player, new ArrayList<AbstractPhase>(), 4, controller).setVisible(true);
    }

}
