package ca.carleton.magicrealm.GUI.charactercreate;

import ca.carleton.magicrealm.control.ClientController;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.entity.chit.Dwelling;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.VictoryCondition;

import java.util.List;

/**
 * Model class for the character selection menu.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 18/02/15
 * Time: 3:27 PM
 */
public class MenuModel {

    List<CharacterType> availableCharacters;

    Player player;

    private final ClientController cont;

    int pointsLeft = 5;

    private CharacterType selectedCharacter;

    private Dwelling selectedStartLocation;

    private final CharacterCreateMenu frame;

    public MenuModel(final CharacterCreateMenu frame, final Player player, final List<CharacterType> availableCharacters,ClientController cnt) {
        this.player = player;
        this.availableCharacters = availableCharacters;
        this.frame = frame;
        this.cont = cnt;
    }

    /**
     * Adds to the victory condition by 1.
     *
     * @param condition the condition type to add.
     */
    public void increaseVictoryCondition(final VictoryCondition.Conditions condition) {
        if (this.pointsLeft > 0) {
            switch (condition) {
                case GOLD:
                    this.player.getVictoryCondition().addGold(1);
                    break;
                case NOTORIETY:
                    this.player.getVictoryCondition().addNotoriety(1);
                    break;
                case FAME:
                    this.player.getVictoryCondition().addFame(1);
                    break;
                case SPELLS_COUNT:
                    this.player.getVictoryCondition().addSpells(1);
                    break;
                case GREAT_TREASURES_COUNT:
                    this.player.getVictoryCondition().addGreatTreasures(1);
                    break;
                default:
                    break;
            }
            this.pointsLeft--;
            this.checkButtonUpdate();
            this.frame.view.updateText();
        }
    }

    public void decreaseVictoryCondition(final VictoryCondition.Conditions condition) {
        if (this.pointsLeft < 5) {
            switch (condition) {
                case GOLD:
                    this.player.getVictoryCondition().addGold(-1);
                    break;
                case NOTORIETY:
                    this.player.getVictoryCondition().addNotoriety(-1);
                    break;
                case FAME:
                    this.player.getVictoryCondition().addFame(-1);
                    break;
                case SPELLS_COUNT:
                    this.player.getVictoryCondition().addSpells(-1);
                    break;
                case GREAT_TREASURES_COUNT:
                    this.player.getVictoryCondition().addGreatTreasures(-1);
                    break;
                default:
                    break;
            }
            this.pointsLeft++;
            this.checkButtonUpdate();
            this.frame.view.updateText();
        }
    }

    /**
     * Called by the frame when the dialog is finished.
     */
    public void done() {
        this.player.setCharacter(CharacterFactory.createCharacter(this.selectedCharacter));
        this.player.setStartingLocation(this.selectedStartLocation);
        this.frame.disposeWindow();
        this.cont.characterSelected();
    }

    public void setSelectedCharacter(final CharacterType type) {
        this.selectedCharacter = type;
    }

    private void checkButtonUpdate() {
        if (this.pointsLeft == 0) {
            this.frame.view.setIncrementKeysEnabled(false);
            this.frame.view.setDecrementKeysEnabled(true);
            this.frame.view.setChooseDwellingEnabled(true);
        } else if (this.pointsLeft == 5) {
            this.frame.view.setDecrementKeysEnabled(false);
            this.frame.view.setIncrementKeysEnabled(true);
            this.frame.view.setChooseDwellingEnabled(false);
        } else {
            this.frame.view.setChooseDwellingEnabled(false);
            this.frame.view.setDecrementKeysEnabled(true);
            this.frame.view.setIncrementKeysEnabled(true);
        }

        this.frame.view.checkIndividualButtons();
    }

    public Dwelling getSelectedStartLocation() {
        return this.selectedStartLocation;
    }

    public void setSelectedStartLocation(final Dwelling selectedStartLocation) {
        this.selectedStartLocation = selectedStartLocation;
    }
}
