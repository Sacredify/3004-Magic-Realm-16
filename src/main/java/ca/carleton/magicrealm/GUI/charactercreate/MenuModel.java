package ca.carleton.magicrealm.GUI.charactercreate;

import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
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

    int pointsLeft = 5;

    private CharacterType selectedCharacter;

    private CharacterCreateMenu frame;

    public MenuModel(final CharacterCreateMenu frame, final Player player, final List<CharacterType> availableCharacters) {
        this.player = player;
        this.availableCharacters = availableCharacters;
        this.frame = frame;
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
        this.frame.disposeWindow();
    }

    public void setSelectedCharacter(final CharacterType type) {
        this.selectedCharacter = type;
    }

    private void checkButtonUpdate() {
        if (this.pointsLeft == 0) {
            this.frame.view.setIncrementKeysEnabled(false);
            this.frame.view.setDecrementKeysEnabled(true);
            this.frame.view.setFinishEnabled(true);
        } else if (this.pointsLeft == 5) {
            this.frame.view.setDecrementKeysEnabled(false);
            this.frame.view.setIncrementKeysEnabled(true);
        } else {
            this.frame.view.setFinishEnabled(false);
            this.frame.view.setDecrementKeysEnabled(true);
            this.frame.view.setIncrementKeysEnabled(true);
        }

        this.frame.view.checkIndividualButtons();
    }

}