package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.chit.ColoredChit;
import ca.carleton.magicrealm.entity.monster.*;
import ca.carleton.magicrealm.game.DiceRoller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

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
                                LOG.debug("Giant created in Mountain from Bones chit");
                                final Clearing startForGiants = tile.getClearings()[tile.getClearings().length - 1];
                                final Giant giant = new Giant();
                                giant.setStartingClearing(startForGiants);
                                giant.setCurrentClearing(startForGiants);
                                startForGiants.addEntity(giant);
                                boardModel.getAbstractMonsters().add(giant);
                                boardModel.createNewMeleeSheet(giant);
                                break;
                            }
                            else if (tile.getTileType().equals(TileType.CAVE)) {
                                LOG.debug("Troll created in Cave from Bones chit");
                                final Clearing startForTrolls = tile.getClearings()[tile.getClearings().length - 1];
                                final Troll troll = new Troll();
                                troll.setStartingClearing(startForTrolls);
                                troll.setCurrentClearing(startForTrolls);
                                startForTrolls.addEntity(troll);
                                boardModel.getAbstractMonsters().add(troll);
                                boardModel.createNewMeleeSheet(troll);
                                break;
                            }
                        case "DANK":
                            if (tile.getTileType().equals(TileType.MOUNTAIN)) {
                                LOG.debug("Spider created in Mountain from Dank chit");
                                final Clearing startForSpiders = tile.getClearings()[tile.getClearings().length - 1];
                                final Spider spider = new Spider();
                                spider.setStartingClearing(startForSpiders);
                                spider.setCurrentClearing(startForSpiders);
                                startForSpiders.addEntity(spider);
                                boardModel.getAbstractMonsters().add(spider);
                                boardModel.createNewMeleeSheet(spider);
                                break;
                            }
                            else if (tile.getTileType().equals(TileType.CAVE)) {
                                LOG.debug("Serpent created in Cave from Dank chit");
                                final Clearing startForSerpents = tile.getClearings()[tile.getClearings().length - 1];
                                final Serpent serpent = new Serpent();
                                serpent.setStartingClearing(startForSerpents);
                                serpent.setCurrentClearing(startForSerpents);
                                startForSerpents.addEntity(serpent);
                                boardModel.getAbstractMonsters().add(serpent);
                                boardModel.createNewMeleeSheet(serpent);
                                break;
                            }
                        case "SLITHER":
                            LOG.debug("Dragon created from Slither chit");
                            final Clearing startForDragons = tile.getClearings()[tile.getClearings().length - 1];
                            final Dragon dragon = new Dragon();
                            dragon.setStartingClearing(startForDragons);
                            dragon.setCurrentClearing(startForDragons);
                            startForDragons.addEntity(dragon);
                            boardModel.getAbstractMonsters().add(dragon);
                            boardModel.createNewMeleeSheet(dragon);

                            LOG.debug("Serpent created from Slither chit");
                            final Clearing startForSerpents = tile.getClearings()[tile.getClearings().length - 1];
                            final Serpent serpent = new Serpent();
                            serpent.setStartingClearing(startForSerpents);
                            serpent.setCurrentClearing(startForSerpents);
                            startForSerpents.addEntity(serpent);
                            boardModel.getAbstractMonsters().add(serpent);
                            boardModel.createNewMeleeSheet(serpent);
                            break;
                        case "RUINS":
                            LOG.debug("Giant bat created from Ruins chit");
                            final Clearing startForBats = tile.getClearings()[tile.getClearings().length - 1];
                            final GiantBat giantBat = new GiantBat();
                            giantBat.setStartingClearing(startForBats);
                            giantBat.setCurrentClearing(startForBats);
                            startForBats.addEntity(giantBat);
                            boardModel.getAbstractMonsters().add(giantBat);
                            boardModel.createNewMeleeSheet(giantBat);
                    }
                }
            }
        }

        /** determine which monsters are prowling **/
        int monsterRoll = DiceRoller.rollOnce();

        for (Denizen denizen: boardModel.getAbstractMonsters()) {
            /** Reset prowling monsters/natives **/
            if (currentDay % 7 == 0) {
                denizen.setCurrentClearing(denizen.getStartingClearing());
            }

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
