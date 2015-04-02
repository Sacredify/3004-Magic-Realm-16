package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.BoardWindow;
import ca.carleton.magicrealm.GUI.charactercreate.CharacterCreateMenu;
import ca.carleton.magicrealm.GUI.phaseselector.PhaseSelectorMenu;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseUtils;
import ca.carleton.magicrealm.item.treasure.Treasure;
import ca.carleton.magicrealm.log.LogWriter;
import ca.carleton.magicrealm.network.AppClient;
import ca.carleton.magicrealm.network.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Main logic class for the client application. Contains the main game loop and supporting methods.
 * <p>
 * Created by Tony on 19/02/2015.
 */
public class GameController {

    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

    private final BoardWindow boardWindow;

    private BoardModel boardModel;

    private CharacterCreateMenu characterCreateMenu;

    private Player currentPlayer;

    private AppClient networkConnection = null;

    private final List<AbstractPhase> recordedPhasesForDay = new ArrayList<AbstractPhase>();

    private final List<CharacterType> availableCharacters = new ArrayList<CharacterType>(Arrays.asList(CharacterType.values()));

    public GameController() {
        this.boardWindow = new BoardWindow();
        this.currentPlayer = new Player();

        JFrame newFrame = new JFrame();
        newFrame.setPreferredSize(new Dimension(550, 1000));
        JTextArea area = new JTextArea();
        area.setSize(new Dimension(550, 1000));
        area.setEditable(false);
        area.setVisible(true);
        newFrame.add(area);
        newFrame.pack();

        LogWriter writer = new LogWriter();

        Timer timer = new Timer(100, e -> writer.output(area));
        timer.start();

        newFrame.setVisible(true);
    }

    /**
     * Handles a message from the server.
     *
     * @param obj the message.
     */
    public void handleMessage(Object obj) {

        if (obj instanceof Message) {
            Message m = (Message) obj;
            LOG.info("Received {} message from server. Payload: {}.", m.getMessageType(), m.getPayload());
            switch (m.getMessageType()) {
                case (Message.SELECT_CHARACTER):
                    this.removeFromAvailableCharacters(m.getPayload());
                    this.characterCreateMenu.updateAvailableCharacters();
                    break;
                case (Message.BIRDSONG_START):
                    this.updateFromServer(m.getPayload());
                    this.refreshBoard();
                    // Process birdsong
                    this.selectPhasesForDay();
                    break;
                case (Message.DAYLIGHT_START):
                    this.updateFromServer(m.getPayload());
                    // Process daylight
                    this.processDaylight();
                    break;
                case (Message.COMBAT_FILL_OUT_MELEE_SHEET):
                    // Set new data
                    this.updateFromServer(m.getPayload());
                    this.refreshBoard();
                    this.selectOptionsForCombat();
                    break;
                case (Message.FATIGUE_FATIGUE_CHITS):
                    // Set new data
                    this.updateFromServer(m.getPayload());
                    this.refreshBoard();
                    this.selectChitsToFatigue();
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
     * Opens up phase selector dialog.
     */
    private void selectPhasesForDay() {
        LOG.info("Displayed birdsong action menu.");
        new PhaseSelectorMenu(this.currentPlayer, this.recordedPhasesForDay, PhaseUtils.getNumberOfPhasesForPlayer(this.currentPlayer, this.boardModel), this).setVisible(true);

    }

    /**
     * Called by the selector menu when done entering phases.
     */
    public void doneEnteringPhasesForDay() {
        LOG.info("User finished entering phase data. Sending server message...");
        this.networkConnection.sendMessage(Message.BIRDSONG_DONE, null);
    }

    /**
     * Processes the daylight phases for this client.
     */
    private void processDaylight() {
        Daylight.doDaylight(this.boardModel, this.currentPlayer, this.recordedPhasesForDay);
        this.updatePlayerInMap();
        this.refreshBoard();
        LOG.info("Executed daylight phase for player.");
        this.networkConnection.sendMessage(Message.DAYLIGHT_DONE, this.boardModel);
    }

    /**
     * Fills out the melee sheet for the user for combat.
     */
    private void selectOptionsForCombat() {
        LOG.info("Starting combat melee sheet step.");
        Combat.fillOutMeleeSheet(this.boardModel, this.currentPlayer, this.boardWindow);
        this.updatePlayerInMap();
        this.networkConnection.sendMessage(Message.COMBAT_SEND_MELEE_SHEET, this.boardModel);
    }

    /**
     * Figure out what chits to fatigue or wound.
     */
    private void selectChitsToFatigue() {
        LOG.info("Starting combat fatigue melee sheet step.");
        Combat.doFatigueStep(this.boardModel, this.currentPlayer, this.boardWindow);
        this.updatePlayerInMap();
        this.networkConnection.sendMessage(Message.FATIGUE_SUBMIT_UPDATED, this.boardModel);
    }

    /**
     * Perform updates necessary when receiving data from the board.
     *
     * @param boardModel the board.
     */
    private void updateFromServer(final Object boardModel) {
        this.boardModel = (BoardModel) boardModel;
        this.updateCurrentPlayer();
    }

    /**
     * Updates the current player status for use in the client's various methods after the board has been received from the server.
     */
    private void updateCurrentPlayer() {
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
    private void updatePlayerInMap() {
        final Iterator<Player> iterator = this.boardModel.getPlayers().iterator();

        final int sanityCheck = this.boardModel.getPlayers().size();
        while (iterator.hasNext()) {
            if (iterator.next().getCharacter().getEntityInformation() == this.currentPlayer.getCharacter().getEntityInformation()) {
                iterator.remove();
                break;
            }
        }
        // Sanity check - check that we actually removed someone.
        assertThat(this.boardModel.getPlayers().size(), is(sanityCheck - 1));

        this.boardModel.getPlayers().add(this.currentPlayer);

        LOG.info("Model has been updated with client player information.");
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

    /**
     * Refresh the board (re-draw).
     */
    private void refreshBoard() {
        this.boardWindow.refresh(this.boardModel, this.currentPlayer.getCharacter());
        this.boardWindow.setGameInfoText(this.createGameInfoString());
        LOG.info("Board refreshed.");
    }

    /**
     * Create the info display string.
     *
     * @return the string.
     */
    private String createGameInfoString() {

        int[] values = this.getVictoryConditionValues();

        String gameInfoText = "<html><br/>";
        gameInfoText = gameInfoText.concat("Character: " + this.currentPlayer.getCharacter().toString() + "<br/>" +
                "Vulnerability: " + this.currentPlayer.getCharacter().getVulnerability() + "<br/>" +
                "Current gold: " + this.currentPlayer.getCharacter().getCurrentGold() + "<br/>" +
                "Current notoriety: " + values[0] + "<br/>" +
                "Current fame: " + values[1] + "<br/>" +
                "Current spell count: " + this.currentPlayer.getCharacter().getCurrentSpellsCount() + "<br/>" +
                "Current great treasures count: " + this.currentPlayer.getCharacter().getCurrentGreatTreasuresCount() + "<br/>" +
                "Needed gold: " + this.currentPlayer.getVictoryCondition().getGold() * 30 + "<br/>" +
                "Needed notoriety: " + this.currentPlayer.getVictoryCondition().getNotoriety() * 20 + "<br/>" +
                "Needed fame: " + this.currentPlayer.getVictoryCondition().getFame() * 10 + "<br/>" +
                "Needed spell count: " + this.currentPlayer.getVictoryCondition().getSpellsCount() * 2 + "<br/>" +
                "Needed great treasures count: " + this.currentPlayer.getVictoryCondition().getGreatTreasuresCount() + "<br/><br/>" +
                "Action chits: <br/>");

        StringBuilder actionChits = new StringBuilder();
        for (final ActionChit chit : this.currentPlayer.getCharacter().getActionChits()) {
            actionChits.append(chit).append("<br/>");
        }
        gameInfoText = gameInfoText.concat(actionChits.toString());
        gameInfoText = gameInfoText.concat("</html>");

        return gameInfoText;
    }

    private int[] getVictoryConditionValues() {
        final int[] notorietyAndFame = {this.currentPlayer.getCharacter().getCurrentNotoriety(), this.currentPlayer.getCharacter().getCurrentFame()};

        this.currentPlayer.getCharacter().getItems().stream()
                .filter(item -> item instanceof Treasure)
                .forEach(treasure -> {
                    notorietyAndFame[0] += ((Treasure) treasure).getNotoriety();
                    notorietyAndFame[1] += ((Treasure) treasure).getFame();
                });

        return notorietyAndFame;
    }

    public void setBoardModel(BoardModel model) {
        this.boardModel = model;
    }

    public BoardWindow getParentWindow() {
        return this.boardWindow;
    }

    public BoardModel getBoardModel() {
        return this.boardModel;
    }

    public void setNetworkConnection(AppClient nC) {
        this.networkConnection = nC;
        LOG.info("Set controller network connection successfully.");
        this.showCharacterCreate();
    }
}
