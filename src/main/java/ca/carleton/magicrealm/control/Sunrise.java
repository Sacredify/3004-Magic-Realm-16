package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.chit.ColoredChit;
import ca.carleton.magicrealm.entity.monster.*;
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
    public static void doSunrise(final BoardModel boardModel, final int currentDay) {
        /** day 1 initialize all the chits **/
        if (currentDay == 1) {
            for (final AbstractTile tile : boardModel.getTilesOfType(TileType.MOUNTAIN)) {
                final Iterator<ColoredChit> iter = tile.getChits().iterator();
                while (iter.hasNext()) {
                    final ColoredChit chit = iter.next();
                    switch (chit.getDescription()) {
                        case "BONES":
                            if (tile.getTileType().equals(TileType.MOUNTAIN)) {
                                final Clearing startForGiants = tile.getClearings()[tile.getClearings().length - 1];
                                final Giant giant = new Giant();
                                giant.setStartingClearing(startForGiants);
                                giant.setCurrentClearing(startForGiants);
                                startForGiants.addEntity(giant);
                                boardModel.getAbstractMonsters().add(giant);
                                break;
                            }
                            else if (tile.getTileType().equals(TileType.CAVE)) {
                                final Clearing startForTrolls = tile.getClearings()[tile.getClearings().length - 1];
                                final Troll troll = new Troll();
                                troll.setStartingClearing(startForTrolls);
                                troll.setCurrentClearing(startForTrolls);
                                startForTrolls.addEntity(troll);
                                boardModel.getAbstractMonsters().add(troll);
                                break;
                            }
                        case "DANK":
                            if (tile.getTileType().equals(TileType.MOUNTAIN)) {
                                final Clearing startForSpiders = tile.getClearings()[tile.getClearings().length - 1];
                                final Spider spider = new Spider();
                                spider.setStartingClearing(startForSpiders);
                                spider.setCurrentClearing(startForSpiders);
                                startForSpiders.addEntity(spider);
                                boardModel.getAbstractMonsters().add(spider);
                                break;
                            }
                            else if (tile.getTileType().equals(TileType.CAVE)) {
                                final Clearing startForSerpents = tile.getClearings()[tile.getClearings().length - 1];
                                final Serpent serpent = new Serpent();
                                serpent.setStartingClearing(startForSerpents);
                                serpent.setCurrentClearing(startForSerpents);
                                startForSerpents.addEntity(serpent);
                                boardModel.getAbstractMonsters().add(serpent);
                                break;
                            }
                        case "SLITHER":
                            final Clearing startForDragons = tile.getClearings()[tile.getClearings().length - 1];
                            final Dragon dragon = new Dragon();
                            dragon.setStartingClearing(startForDragons);
                            dragon.setCurrentClearing(startForDragons);
                            startForDragons.addEntity(dragon);
                            boardModel.getAbstractMonsters().add(dragon);

                            final Clearing startForSerpents = tile.getClearings()[tile.getClearings().length - 1];
                            final Serpent serpent = new Serpent();
                            serpent.setStartingClearing(startForSerpents);
                            serpent.setCurrentClearing(startForSerpents);
                            startForSerpents.addEntity(serpent);
                            boardModel.getAbstractMonsters().add(serpent);
                            break;
                        case "RUINS":
                            final Clearing startForBats = tile.getClearings()[tile.getClearings().length - 1];
                            final GiantBat giantBat = new GiantBat();
                            giantBat.setStartingClearing(startForBats);
                            giantBat.setCurrentClearing(startForBats);
                            startForBats.addEntity(giantBat);
                            boardModel.getAbstractMonsters().add(giantBat);
                    }
                }
            }
        }

        for (Denizen denizen: boardModel.getAbstractMonsters()) {
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
