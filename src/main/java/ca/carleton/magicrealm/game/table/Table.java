package ca.carleton.magicrealm.game.table;

import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.entity.character.BlackKnight;
import ca.carleton.magicrealm.entity.character.Dwarf;
import ca.carleton.magicrealm.game.DiceRoller;
import ca.carleton.magicrealm.game.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An attempt to separate dice rolling utility from game logic for rolling on the tables. Here we can check character special
 * values, as well as treasures that may modify these outcomes.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 16/03/15
 * Time: 5:14 PM
 */
public abstract class Table {

    private static final Logger LOG = LoggerFactory.getLogger(Table.class);

    public static class MeetingTable {

        /**
         * Rolls on the meeting table.
         * TODO Return meeting table results instead of the int (move logic to the meeting table).
         *
         * @param player   the player rolling.
         * @param location the location of the player.
         * @return the roll value.
         */
        public static int roll(final Player player, final Clearing location) {

            // If the character is the black knight, they only roll once.
            if (player.getCharacter() instanceof BlackKnight) {
                LOG.info("Black knight rolled once on the meeting table.");
                return DiceRoller.rollOnce();
            }

            // If the character is a dwarf and is in a cave, they only roll once.
            if (player.getCharacter() instanceof Dwarf && location.getParentTile().getTileType() == TileType.CAVE) {
                LOG.info("Dwarf was in a cave and rolled once on the meeting table.");
                return DiceRoller.rollOnce();
            }

            LOG.info("Rolled twice on the meeting table.") ;
            // The default is to roll twice for everything else.
            return DiceRoller.rollTwiceTakeHigher();
        }

    }

    public static class MissileTable {

    }

}
