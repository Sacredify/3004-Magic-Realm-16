package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardWindow;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;

import java.util.ArrayList;

/**
 * Created by Tony on 19/02/2015.
 */
public class GameController {

    ArrayList<AbstractCharacter> characters;

    BoardWindow board = new BoardWindow();

    public GameController() {
        board = new BoardWindow();

        characters = new ArrayList<>();

    }


}
