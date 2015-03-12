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

    private final BoardWindow boardWindow;

    private BoardGUIModel boardModel;

    private CharacterCreateMenu characterCreateMenu;

    private Player currentPlayer;

    private AppClient networkConnection = null;

    private final List<AbstractPhase> recordedPhasesForDay = new ArrayList<AbstractPhase>();

    private final List<CharacterType> availableCharacters = new ArrayList<CharacterType>(Arrays.asList(CharacterType.values()));

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
            Message m = (Message) obj;
            LOG.info("Received {} message from server. Payload: {}.", m.getMessageType(), m.getMessageObject());
            switch (m.getMessageType()) {
                case (Message.SELECT_CHARACTER):
                    this.removeFromAvailableCharacters(m.getMessageObject());
                    this.characterCreateMenu.updateAvailableCharacters();
                    break;
                case (Message.SET_MAP):
                    this.setBoardModel((BoardGUIModel) m.getMessageObject());
                    this.updateCurrentPlayer();
                    this.refreshBoard();
                case (Message.MOVE):
                    //Insert move character functionality here
                    this.handleMove((BoardGUIModel) m.getMessageObject());
                    break;
                case (Message.BIRDSONG_START):
                    // Set new data
                    this.setBoardModel(((BoardGUIModel) m.getMessageObject()));
                    this.updateCurrentPlayer();
                    this.refreshBoard();
                    // Process birdsong
                    this.selectPhasesForDay();
                    break;
                case (Message.DAYLIGHT_START):
                    // Set new data
                    this.setBoardModel(((BoardGUIModel) m.getMessageObject()));
                    this.updateCurrentPlayer();
                    // Process daylight
                    this.processUpdatedPhasesFromBoard();
                    this.processDaylight();
                    break;
                case (Message.SUNSET_START):
                    this.setBoardModel(((BoardGUIModel) m.getMessageObject()));
                    this.updateCurrentPlayer();
                    this.refreshBoard();
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
        LOG.info("User selected {} as his/her character.", this.currentPlayer.getCharacter().getEntityInformation());
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
        this.boardWindow.refresh(this.boardModel, this.currentPlayer.getCharacter());
    }

    /**
     * Opens up phase selector dialog.
     */
    public void selectPhasesForDay() {
        LOG.info("Displayed birdsong action menu.");
        new PhaseSelectorMenu(this.currentPlayer, this.recordedPhasesForDay, 1, this).setVisible(true);
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

        LOG.info("User finished entering phase data. Sending server message...");
        this.networkConnection.sendMessage(Message.BIRDSONG_DONE, null);
    }

    /**
     * Because we send the entire board, we need to update the phases, since the references are now garbled.
     */
    public void processUpdatedPhasesFromBoard() {
        for (final AbstractPhase phase : this.recordedPhasesForDay) {
            phase.updateFromBoard(this.currentPlayer, this.boardModel);
        }
        LOG.info("Updated {} phases' data from the board before beginning daylight.", this.recordedPhasesForDay.size());
    }

    /**
     * Processes the daylight phases for this client.
     */
    public void processDaylight() {
        Daylight.processPhasesForPlayer(this.boardModel, this.currentPlayer, this.recordedPhasesForDay);
        this.updatePlayerInMap();
        this.refreshBoard();
        LOG.info("Executed daylight phase for player.");
        this.networkConnection.sendMessage(Message.DAYLIGHT_DONE, this.boardModel);
    }

    /**
     * Updates the current player status for use in the client's various methods after the board has been received from the server.
     */
    public void updateCurrentPlayer() {
        for (final Player player : this.boardModel.getPlayers()) {
            if (this.currentPlayer.getCharacter().getEntityInformation() == player.getCharacter().getEntityInformation()) {
                this.currentPlayer = player;
                break;
            }
        }

        LOG.info("Current player information has been updated from the server.");
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

        LOG.info("Model has been updated with client player information.");
    }

    public void setNetworkConnection(AppClient nC) {
        this.networkConnection = nC;
        LOG.info("Set controller network connection successfully.");
        this.showCharacterCreate();
    }

    /**
     * Show the character create dialog.
     */
    private void showCharacterCreate() {
        this.characterCreateMenu = new CharacterCreateMenu(this.boardWindow, this.currentPlayer, this.availableCharacters, this);
        this.characterCreateMenu.displayWindow();
        LOG.info("Displayed character create.");
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
        final CharacterType removed = ((Player) player).getCharacter().getEntityInformation().convertToCharacterType();
        this.availableCharacters.remove(removed);
        LOG.info("Removed {} from the list of available characters for character select, another user has chosen it.", removed);
    }

    public void refreshBoard() {
        this.boardWindow.refresh(this.boardModel, this.currentPlayer.getCharacter());
        this.boardWindow.setGameInfoText(this.createGameInfoString());
        LOG.info("Board refreshed.");
    }

    private String createGameInfoString() {
        String gameInfoText = "<html>";
        gameInfoText = gameInfoText.concat("Character: " + this.currentPlayer.getCharacter().toString() + "<br/>" +
                "Current gold: " + this.currentPlayer.getCharacter().getCurrentGold() + "<br/>" +
                "Current notoriety: " + this.currentPlayer.getCharacter().getCurrentNotoriety() + "<br/>" +
                "Current fame: " + this.currentPlayer.getCharacter().getCurrentFame() + "<br/>" +
                "Current spell count: " + this.currentPlayer.getCharacter().getCurrentSpellsCount() + "<br/>" +
                "Current great treasures count: " + this.currentPlayer.getCharacter().getCurrentGreatTreasuresCount() + "<br/>" +
                "Needed gold: " + this.currentPlayer.getVictoryCondition().getGold() + "<br/>" +
                "Needed notoriety: " + this.currentPlayer.getVictoryCondition().getNotoriety() + "<br/>" +
                "Needed fame: " + this.currentPlayer.getVictoryCondition().getFame() + "<br/>" +
                "Needed spell count: " + this.currentPlayer.getVictoryCondition().getSpellsCount() + "<br/>" +
                "Needed great treasures count: " + this.currentPlayer.getVictoryCondition().getGreatTreasuresCount() + "<br/>");
        gameInfoText = gameInfoText.concat("</html>");

        return gameInfoText;
    }

    public void setBoardModel(BoardGUIModel model) {
        this.boardModel = model;
    }

    public void setStatusText(final String text) {
        this.boardWindow.setStatusText(text);
    }

    public BoardWindow getParentWindow() {
        return this.boardWindow;
    }

    public BoardGUIModel getBoardModel() {
        return this.boardModel;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
}
