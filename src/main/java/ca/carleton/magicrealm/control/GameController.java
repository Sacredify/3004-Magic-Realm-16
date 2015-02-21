package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import ca.carleton.magicrealm.GUI.board.BoardWindow;
import ca.carleton.magicrealm.GUI.charactercreate.CharacterCreateMenu;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.Networking.AppClient;
import ca.carleton.magicrealm.Networking.Message;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.MovePhase;
import ca.carleton.magicrealm.game.turn.Daylight;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tony on 19/02/2015.
 */
public class GameController {

    private ArrayList<AbstractCharacter> characters;

    private BoardWindow boardWindow;

    private BoardGUIModel boardModel;

    private CharacterCreateMenu characterCreateMenu;

    private Player currentPlayer;

    private AppClient networkConnection = null;

    private List<AbstractPhase> recordedPhases;

    private List<CharacterType> availableCharacters = new ArrayList<CharacterType>(Arrays.asList(CharacterType.values()));

    public GameController() {
        this.boardWindow = new BoardWindow();

        this.characters = new ArrayList<>();

        this.boardModel = new BoardGUIModel();

        this.currentPlayer = new Player();

        showCharacterCreate();

    }

    /**
     * Example method we can use when the user is done recording their phases.
     */
    public void doneWithBirdSong() {
        Daylight.processPhasesForPlayer(this.currentPlayer, this.recordedPhases);
        // Update server
    }

    public void handleMessage(Object obj) {

        if (obj instanceof Message) {
            System.out.println("Game Controller:This is a Message Object");
            Message m = (Message) obj;
            System.out.println("This is a :" + m.getMessageType() + " Message Type");
            switch(m.getMessageType()){
                case(Message.SELECT_CHARACTER):
                    this.removeFromAvailableCharacters(m.getMessageObject());
                    this.characterCreateMenu.updateAvailableCharacters();
                break;
                case(Message.MOVE):
                    //Insert move character functionality here.
                    break;
                case(Message.ALL_PARTICIPATED):
                    //Insert Stage incrementing functionality here
                    System.out.println("ALL PARTICIPATED MESSAGE RECIEVED");
                    break;
                default:
                    break;
            }


        } else if (obj instanceof String) {
            System.out.println("This is a string");
        }
    }

    public void characterSelected() {
        System.out.println("CHARACTER SELECTED IN GAME CONTROLLER");
        this.networkConnection.sendMessage(Message.SELECT_CHARACTER, this.currentPlayer);
    }

    /** Methods to set up a move phase **/
    private void setupMovePhaseForPlayer() {
        boardWindow.setupMoveButtons(createMoveButtonsForClearing(currentPlayer.getCurrentClearing())); // display move buttons now? or later
        recordedPhases.add(new MovePhase());
    }

    public void movePlayerToClearing(Clearing clearing) {
        MovePhase movement = new MovePhase();
        movement.setMoveTarget(clearing);
        this.recordedPhases.add(movement);
    }

    public ArrayList<JButton> createMoveButtonsForClearing(Clearing clearing) {
        ArrayList<JButton> buttons = new ArrayList<>();

        for (final Clearing adjacentClearing : clearing.getAdjacentClearings()) {
            JButton newButton = new JButton();
            newButton.setSize(30, 30);
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

    public void setNetworkConnection(AppClient nC) {
        this.networkConnection = nC;
    }

    private void showCharacterCreate() {
        this.characterCreateMenu = new CharacterCreateMenu(this.boardWindow, this.currentPlayer, this.availableCharacters, this);
        this.characterCreateMenu.displayWindow();
    }

    private void removeFromAvailableCharacters(final Object player) {
        if (!(player instanceof Player)) {
            throw new IllegalArgumentException("Not a player object");
        }

        this.availableCharacters.remove(((Player) player).getCharacter().getEntityInformation().convertToCharacterType());
    }

    /*
    Methods to set up the window to select a player's phase for the day
    public void setupPhaseSelection() {
        PhaseSelectorMenu phaseSelectorMenu = new PhaseSelectorMenu();

        JButton confirmFirstPhaseButton = new JButton("ENTER"); // TODO: make unhardcoded later

        confirmFirstPhaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPhase = (String)phaseSelectorMenu.getPhaseSelectorPanel().getFirstPhaseBox().getSelectedItem();

                if (selectedPhase.equals("Move")) {
                    setupMovePhaseForPlayer();
                }
            }
        });
    }
    */
}
