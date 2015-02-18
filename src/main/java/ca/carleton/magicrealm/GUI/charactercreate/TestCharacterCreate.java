package ca.carleton.magicrealm.GUI.charactercreate;

import ca.carleton.magicrealm.game.Player;

/**
 * Created with IntelliJ IDEA.
 * Date: 18/02/15
 * Time: 3:34 PM
 */
public class TestCharacterCreate {

    public static void main(String[] args) {
        new CharacterCreateMenu(new Player()).displayWindow();
    }
}
