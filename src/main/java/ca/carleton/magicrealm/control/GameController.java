package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import ca.carleton.magicrealm.GUI.board.BoardWindow;
import ca.carleton.magicrealm.GUI.charactercreate.CharacterCreateMenu;
import ca.carleton.magicrealm.GUI.phaseselector.PhaseSelectorMenu;
import ca.carleton.magicrealm.Networking.AppClient;
import ca.carleton.magicrealm.Networking.Message;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Tony on 19/02/2015.
 */
public class GameController {

    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

    private BoardWindow boardWindow;

    private BoardGUIModel boardModel;

    private CharacterCreateMenu characterCreateMenu;

    private Player currentPlayer;

    private AppClient networkConnection = null;

    private List<AbstractPhase> recordedPhasesForDay = new ArrayList<AbstractPhase>();

    private List<CharacterType> availableCharacters = new ArrayList<CharacterType>(Arrays.asList(CharacterType.values()));

    public GameController() {
        this.boardWindow = new BoardWindow();
        this.currentPlayer = new Player();
    }

    /**
     * Handles a message from the server.
     *
     * @param obj the message.
     */
    public void handleMessage(Object obj) {

        if (obj instanceof Message) {
            System.out.println("Game Controller:This is a Message Object");
            Message m = (Message) obj;
            System.out.println("This is a :" + m.getMessageType() + " Message Type");
            switch (m.getMessageType()) {
                case (Message.SELECT_CHARACTER):
                    this.removeFromAvailableCharacters(m.getMessageObject());
                    this.characterCreateMenu.updateAvailableCharacters();
                    break;
                case (Message.MOVE):
                    //Insert move character functionality here
                    this.handleMove((BoardGUIModel) m.getMessageObject());
                    break;
                case (Message.ALL_PARTICIPATED):
                    //Insert Stage incrementing functionality here
                    System.out.println("ALL PARTICIPATED MESSAGE RECEIVED");
                    //this.boardWindow.hideStatusLabel();
                    //TODO Update label with message saying waiting for board or some shit.
                    break;
                case (Message.SET_MAP):
                    //SETTING MAP MODEL
                    this.setBoardModel((BoardGUIModel) m.getMessageObject());
                    this.updateCurrentPlayer();
                    this.boardWindow.refresh(this.boardModel);
                    this.selectPhasesForDay();

                default:
                    break;
            }

        } else if (obj instanceof String) {
            System.out.println("This is a string");
        }
    }

    /**
     * Called by the Character Select Menu to indicate that the character has been set.
     * This also sends the message to the server that the character has been selected, along with the player object.
     */
    public void characterSelected() {
        System.out.println("CHARACTER SELECTED IN GAME CONTROLLER");
        this.networkConnection.sendMessage(Message.SELECT_CHARACTER, this.currentPlayer);
    }

    /**
     * Refreshes the board with the new model received.
     *
     * @param newBoardModel the model.
     */
    @Deprecated
    public void handleMove(final BoardGUIModel newBoardModel) {
        this.boardModel = newBoardModel;
        this.boardWindow.refresh(this.boardModel);
    }

    /**
     * Opens up phase selector dialog.
     */
    public void selectPhasesForDay() {
        new PhaseSelectorMenu(this.recordedPhasesForDay, 1, this);
    }

    /**
     * Called by the selector menu when done entering phases.
     */
    public void doneEnteringPhasesForDay() {
       /*
        LOGIC -
        1. This method is called, signifying this.recordedPhasesForDay has all the phases the client wishes to enter.
        2. The client will send a message to the server saying they are done.
        3. When the server receives all <x> number of clients saying they are done, the server starts DAYLIGHT.
        4. The server will pick a client, send them a message with the current board, and the client will use Daylight.processPhasesForDay() that
         will process the phases.
        5. The client will upload the updated data to the server.
        6. The server will then send the next message to the next person in turn, with the updated board.
        7. The next client will update their board as per the first, then execute the phases.
        8 And so on.
        9. After they are all done, server sends EVENING message.
        */
    }

    /**
     * Updates the current player status for use in the client's various methods after the board has been received from the server.
     */
    public void updateCurrentPlayer() {
        for (final Player player : this.boardModel.getPlayers()) {
            if (this.currentPlayer.getCharacter().getEntityInformation() == player.getCharacter().getEntityInformation()) {
                this.currentPlayer = player;
            }
        }
    }

    /**
     * Replaces the current player stored on the board with the updated one.
     * IMPORTANT This needs to be called before sending sending the map, as it updates the board.
     */
    public void updatePlayerInMap() {
        final Iterator<Player> iterator = this.boardModel.getPlayers().iterator();

        final int sanityCheck = this.boardModel.getPlayers().size();
        while (iterator.hasNext()) {
            if (iterator.next().getCharacter().getEntityInformation() == this.currentPlayer.getCharacter().getEntityInformation()) {
                iterator.remove();
                break;
            }
        }
        // Sanity check - check that we actually removed someone.
        assert (this.boardModel.getPlayers().size() == (sanityCheck - 1));

        this.boardModel.getPlayers().add(this.currentPlayer);
    }

    public void setNetworkConnection(AppClient nC) {
        this.networkConnection = nC;
        LOG.info("Set controller network connection succesfully. Showing character create for the user.");
        this.showCharacterCreate();
    }

    /**
     * Show the character create dialog.
     */
    private void showCharacterCreate() {
        this.characterCreateMenu = new CharacterCreateMenu(this.boardWindow, this.currentPlayer, this.availableCharacters, this);
        this.characterCreateMenu.displayWindow();
    }

    /**
     * Remove a character from the list of available characeters when choosing a character.
     *
     * @param player the player containing the character to remove.
     */
    private void removeFromAvailableCharacters(final Object player) {
        if (!(player instanceof Player)) {
            throw new IllegalArgumentException("Not a player object");
        }

        this.availableCharacters.remove(((Player) player).getCharacter().getEntityInformation().convertToCharacterType());
    }

    public void setBoardModel(BoardGUIModel model) {
        this.boardModel = model;
    }

    public void setStatusText(final String text) {
        this.boardWindow.setStatusText(text);
    }
}
