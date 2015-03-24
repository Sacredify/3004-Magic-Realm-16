package ca.carleton.magicrealm.game.combat;

import ca.carleton.magicrealm.game.Player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 24/03/2015
 * Time: 5:55 PM
 */
public class MeleeSheets implements Serializable {

    private static final long serialVersionUID = 1945181772117961369L;

    private final Map<Player, MeleeSheet> meleeSheets = new HashMap<Player, MeleeSheet>();

    /**
     * Create a new MeleeSheet used during combat for the given player.
     *
     * @param player the player.
     */
    public void createNewMeleeSheetForPlayer(final Player player) {
        this.meleeSheets.putIfAbsent(player, new MeleeSheet(player));
    }

    /**
     * Return the melee sheet used by the player.
     *
     * @param player the player.
     * @return the melee sheet.
     */
    public MeleeSheet getMeleeSheetForPlayer(final Player player) {
        return this.meleeSheets.get(player);
    }

}
