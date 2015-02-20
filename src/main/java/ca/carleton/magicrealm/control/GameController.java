package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import ca.carleton.magicrealm.GUI.board.BoardWindow;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.MovePhase;
import ca.carleton.magicrealm.game.turn.Daylight;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 19/02/2015.
 */
public class GameController {

    private ArrayList<AbstractCharacter> characters;

    private BoardWindow boardWindow;

    private BoardGUIModel boardModel;

    private Player currentPlayer;

    private List<AbstractPhase> recordedPhases;

    public GameController() {
        this.boardWindow = new BoardWindow();

        this.characters = new ArrayList<>();

        this.boardModel = new BoardGUIModel();

    }

    /**
     * Example method we can use when the user is done recording their phases.
     */
    public void doneWithBirdSong() {
        Daylight.processPhasesForPlayer(this.currentPlayer, this.recordedPhases);
    }

    public void movePlayerToClearing(Clearing clearing) {
        MovePhase movement = new MovePhase();
        movement.setMoveTarget(clearing);
        this.recordedPhases.add(movement);
    }

    public ArrayList<JButton> getMoveButtonsForClearing(Clearing clearing) {
        ArrayList<JButton> buttons = new ArrayList<>();

        for (final Clearing adjacentClearing: clearing.getAdjacentClearings()) {
            JButton newButton = new JButton();
            newButton.setSize(30,30);
            newButton.setLocation(adjacentClearing.getX(), adjacentClearing.getY());

            newButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GameController.this.movePlayerToClearing(adjacentClearing);
                }
            });
            buttons.add(newButton);
        }
        return buttons;
    }

}
