package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.chit.ColoredChit;
import ca.carleton.magicrealm.entity.monster.Ghost;
import ca.carleton.magicrealm.entity.monster.Giant;
import ca.carleton.magicrealm.entity.natives.AbstractNative;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Random;

/**
 * Groupings of methods to handle sunrise operations.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 23/02/15
 * Time: 5:48 AM
 */
public class Sunrise {

    private static final Logger LOG = LoggerFactory.getLogger(Sunrise.class);

    /**
     * Execute sunrise for the day.
     * <p/>
     * 1. Mark which natives are now prowling.
     * 2. Reset position of all prowling and un-hired natives and monsters (even if killed)) are returned to where they started if 7th day of week.
     *
     * @param boardModel the model to update.
     * @param currentDay the current day.
     */
    public static void doSunrise(final BoardGUIModel boardModel, final int currentDay) {
        /** day 1 initialize all the chits **/
        if (currentDay == 1) {
            for (final AbstractTile tile : boardModel.getTilesOfType(TileType.MOUNTAIN)) {
                final Iterator<ColoredChit> iter = tile.getChits().iterator();
                while (iter.hasNext()) {
                    final ColoredChit chit = iter.next();
                    switch (chit.getDescription()) {
                        case "BONES":
                            final Clearing startForGiants = tile.getClearings()[tile.getClearings().length - 1];
                            final Giant giant = new Giant();
                            giant.setStartingClearing(startForGiants);
                            startForGiants.addEntity(giant);
                            boardModel.getDenizens().add(giant);
                            break;
                    }
                }
            }
        }

        for (Denizen denizen: boardModel.getDenizens()) {
            /** Reset prowling monsters/natives **/
            if (currentDay % 7 == 0) {
                denizen.setCurrentClearing(denizen.getStartingClearing());
            }
            /** determine which monsters are prowling **/
            Random monsterDice = new Random();
            int monsterRoll = monsterDice.nextInt(6) + 1;

            // Denizens that weren't rolled are not prowling by default
            denizen.setProwling(false);
            if (monsterRoll == 1) {

            }
            else if (monsterRoll == 2) {

            }
            else if (monsterRoll == 3) {

            }
            else if (monsterRoll == 4) {
                if (denizen.getEntityInformation().equals(EntityInformation.GIANT)) {
                    denizen.setProwling(true);
                }
            }
            else if (monsterRoll == 5) {

            }
            else if (monsterRoll == 6) {

            }
        }
    }

}
