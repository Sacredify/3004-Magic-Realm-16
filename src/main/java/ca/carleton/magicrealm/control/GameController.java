package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardWindow;
import ca.carleton.magicrealm.GUI.tile.BoardModel;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;

import java.util.ArrayList;

/**
 * Created by Tony on 19/02/2015.
 */
public class GameController {

    private ArrayList<AbstractCharacter> characters;

    private BoardWindow boardWindow;

    private BoardModel boardModel;

    private AbstractCharacter currentPlayer;

    public GameController() {
        boardWindow = new BoardWindow();

        characters = new ArrayList<>();

        boardModel = new BoardModel();

    }

    /*

        DO NOT do it this way guys. We need to support multiple phases written down and executed simultaneously, not one
        at a time by the user.

        See ca.carleton.magicrealm.game.phase.abstractPhase


    // TODO: Do hard implementation of this when characters are defined
    public void movePlayerToClearing(Clearing clearing) {
        currentPlayer.setCurrentClearingLocation(clearing);

        // TODO: Send message to server player moved to this clearing
    }

    public ArrayList<JButton> getMoveButtonsForClearing(Clearing clearing) {
        ArrayList<JButton> buttons = new ArrayList<>();

        for (Clearing adjacentClearing: clearing.getAdjacentClearings()) {
            JButton newButton = new JButton();
            newButton.setSize(30,30);
            newButton.setLocation(adjacentClearing.getX(), adjacentClearing.getY());

            newButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    movePlayerToClearing(adjacentClearing);
                }
            });
            buttons.add(newButton);
        }
        return buttons;
    }

    */

}
