package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import ca.carleton.magicrealm.GUI.board.BoardWindow;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.GUI.charactercreate.CharacterCreateMenu;
///import ca.carleton.magicrealm.GUI.phaseselector.PhaseSelectorMenu;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.Networking.AppClient;
import ca.carleton.magicrealm.Networking.Message;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.MovePhase;

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

    private ArrayList<Player> otherPlayers;

    private BoardWindow boardWindow;

    private BoardGUIModel boardModel;

    private CharacterCreateMenu characterCreateMenu;

    private Player currentPlayer;

    private AppClient networkConnection = null;

    private List<CharacterType> availableCharacters = new ArrayList<CharacterType>(Arrays.asList(CharacterType.values()));

    public GameController() {

        // Build the board data.
        this.boardModel = new BoardGUIModel();
        // Place chits.
        ChitBuilder.placeChits(this.boardModel);
        // Build window.
        this.boardWindow = new BoardWindow(this.boardModel);

        // Other players.
        this.otherPlayers = new ArrayList<>();
        // The current player.
        this.currentPlayer = new Player();

        this.showCharacterCreate();

    }

    /**
     * Execute a given phase.
     */
    public void executePhase(final AbstractPhase phase) {
        Daylight.processPhaseForPlayer(this.currentPlayer, phase);
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
                    //Insert move character functionality here
                    this.handleMove((Player)m.getMessageObject());
                    break;
                case(Message.ALL_PARTICIPATED):
                    //Insert Stage incrementing functionality here
                    System.out.println("ALL PARTICIPATED MESSAGE RECIEVED");
                    break;
                case(Message.SET_MAP):
                    //SETTING MAP MODEL
                    System.out.println("SETTING MAP MODEL");
                    this.setBoardModel((BoardGUIModel) m.getMessageObject());


                default:
                    break;
            }


        } else if (obj instanceof String) {
            System.out.println("This is a string");
        }
    }

    public void setBoardModel(BoardGUIModel model){
        this.boardModel = model;
        //UpdateGUI
    }

    public void characterSelected() {
        System.out.println("CHARACTER SELECTED IN GAME CONTROLLER");
        this.networkConnection.sendMessage(Message.SELECT_CHARACTER, this.currentPlayer);
    }

    public void  handleMove(Player p){

        //Get co-ordinates of the clearing to move to
        Clearing c = p.getCurrentClearing();


        switch(p.getCharacter().getEntityInformation().convertToCharacterType()){
            case AMAZON:
                System.out.print("HANDLE AMAZON MOVE");
                break;
            case BLACK_KNIGHT:
                System.out.print("HANDLE BLACK_KNIGHT MOVE");
                break;
            case CAPTAIN:
                System.out.print("HANDLE CAPTAIN MOVE");
                break;
            case DWARF:
                System.out.print("HANDLE DWARF MOVE");
                break;
            case ELF:
                System.out.print("HANDLE ELF MOVE");
                break;
            case SWORDSMAN:
                System.out.print("HANDLE SWORDSMAN MOVE");
                break;
        }

    }


    /** Methods to set up a move phase **/
    private void setupMovePhaseForPlayer() {
        boardWindow.setupMoveButtons(this.createMoveButtonsForClearing(currentPlayer.getCurrentClearing())); // display move buttons now? or later
    }

    public void movePlayerToClearing(Clearing clearing) {
        MovePhase movement = new MovePhase();
        movement.setMoveTarget(clearing);
        this.executePhase(movement);
        Message m = new Message(networkConnection.getId(),Message.MOVE,this.currentPlayer);
        networkConnection.sendMessage(Message.MOVE,m);
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

    /** Methods to set up the window to select a player's phase for the day **/
    public void setupPhaseSelection() {
       // PhaseSelectorMenu phaseSelectorMenu = new PhaseSelectorMenu();

        JButton confirmFirstPhaseButton = new JButton("ENTER"); // TODO: make unhardcoded later

        confirmFirstPhaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //  String selectedPhase = (String)phaseSelectorMenu.getPhaseSelectorPanel().getFirstPhaseBox().getSelectedItem();

              /*  if (selectedPhase.equals("Move")) {
                    setupMovePhaseForPlayer();
                }*/
            }
        });
    }

    /**
     * Method to set the characters' icons on the game board once they have been created
     */
    public void setupCharacterIcons() {
        this.boardWindow.setupCharacterIcons(otherPlayers);
    }

}
