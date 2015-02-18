package ca.carleton.magicrealm.GUI.charactercreate;

import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;

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

    public MenuModel(final Player player, final List<CharacterType> availableCharacters) {
        this.player = player;
        this.availableCharacters = availableCharacters;
    }

}
