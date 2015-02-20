package ca.carleton.magicrealm.GUI.charactercreate;

import ca.carleton.magicrealm.GUI.board.BoardWindow;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Date: 18/02/15
 * Time: 3:34 PM
 */
public class TestCharacterCreate {

    public static void main(String[] args) {
        new CharacterCreateMenu(new BoardWindow(), new Player(), Arrays.asList(CharacterType.values())).displayWindow();
    }
}
