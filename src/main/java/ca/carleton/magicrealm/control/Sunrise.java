package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.EntityBuilder;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.chit.ColoredChit;
import ca.carleton.magicrealm.entity.chit.Dwelling;
import ca.carleton.magicrealm.entity.monster.*;
import ca.carleton.magicrealm.entity.natives.AbstractNative;
import ca.carleton.magicrealm.entity.natives.NativeFactory;
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

    private static EntityBuilder entityBuilder = new EntityBuilder();

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
                                createGiant(tile, boardModel);
                                break;
                            }
                            else if (tile.getTileType().equals(TileType.CAVE)) {
                                createTroll(tile, boardModel);
                                break;
                            }
                        case "DANK":
                            if (tile.getTileType().equals(TileType.MOUNTAIN)) {
                                createSpider(tile, boardModel);
                                break;
                            }
                            else if (tile.getTileType().equals(TileType.CAVE)) {
                                createSerpent(tile, boardModel);
                                break;
                            }
                        case "SLITHER":
                            createDragon(tile, boardModel);
                            createSerpent(tile, boardModel);
                            break;
                        case "RUINS":
                            if (tile.getTileType().equals(TileType.MOUNTAIN)) {
                                createGiantBat(tile, boardModel);
                                break;
                            }
                            else {
                                createGoblin(tile, boardModel);
                                break;
                            }
                        case "LOSTCASTLE":
                            int lostCastleRoll = DiceRoller.rollOnce();
                            switch (lostCastleRoll) {
                                case 1:
                                    createDragon(tile, boardModel);
                                    break;
                                case 2:
                                    createSerpent(tile, boardModel);
                                    break;
                                case 4:
                                    createGiant(tile, boardModel);
                                    break;
                                case 5:
                                    createSpider(tile, boardModel);
                                    break;
                                case 6:
                                    createGiantBat(tile, boardModel);
                                    break;
                            }
                            break;
                        case "LOSTCITY":
                            int lostCityRoll = DiceRoller.rollOnce();
                            switch (lostCityRoll) {
                                case 1:
                                    createDragon(tile, boardModel);
                                    break;
                                case 2:
                                    createSerpent(tile, boardModel);
                                    break;
                                case 3:
                                    createGoblin(tile, boardModel);
                                    break;
                                case 4:
                                    createTroll(tile, boardModel);
                                    break;
                                case 6:
                                    createGiantBat(tile, boardModel);
                                    break;
                            }
                            break;
                        }
                    }
                }
            }

        for (Dwelling dwelling : Dwelling.values()) {
            Clearing clearing = boardModel.getClearingOfDwelling(dwelling);
            int nativeRoll = DiceRoller.rollOnce();
            if (clearing != null) {
                if (dwelling.equals(Dwelling.CHAPEL)) {
                    if (nativeRoll == 3)
                        createPatrol(clearing, boardModel);
                    else if (nativeRoll == 4)
                        createLancer(clearing, boardModel);
                }
                if (dwelling.equals(Dwelling.GUARD)) {
                    if (nativeRoll == 3)
                        createPatrol(clearing, boardModel);
                }
                if (dwelling.equals(Dwelling.SMALL_FIRE)) {
                    if (nativeRoll == 2)
                        createWoodfolk(clearing, boardModel);
                    else if (nativeRoll == 4)
                        createLancer(clearing, boardModel);
                    else if (nativeRoll == 5)
                        createBashkar(clearing, boardModel);
                }
                if (dwelling.equals(Dwelling.HOUSE)) {
                    if (nativeRoll == 2)
                        createWoodfolk(clearing, boardModel);
                    if (nativeRoll == 3)
                        createPatrol(clearing, boardModel);
                }
                if (dwelling.equals(Dwelling.SMALL_FIRE)) {
                    if (nativeRoll == 1)
                        createCompany(clearing, boardModel);
                    if (nativeRoll == 4)
                        createLancer(clearing, boardModel);
                    if (nativeRoll == 5)
                        createBashkar(clearing, boardModel);
                }
                if (dwelling.equals(Dwelling.INN)) {
                    if (nativeRoll == 1)
                        createCompany(clearing, boardModel);
                    if (nativeRoll == 3)
                        createPatrol(clearing, boardModel);
                }
            }
        }

        for (Denizen denizen: boardModel.getAbstractMonsters()) {
            /** Reset prowling monsters/natives **/
            if (currentDay % 7 == 0) {
                denizen.setCurrentClearing(denizen.getStartingClearing());
                denizen.setHidden(true);
            }
            /** determine which monsters are prowling **/
            int monsterRoll = DiceRoller.rollOnce();

            // Denizens that weren't rolled are not prowling by default
            denizen.setProwling(false);
            if (monsterRoll == 1) {
                if (denizen.getEntityInformation().equals(EntityInformation.DRAGON)) {
                    denizen.setProwling(true);
                    denizen.setHidden(false);
                }
            }
            else if (monsterRoll == 2) {
                if (denizen.getEntityInformation().equals(EntityInformation.SERPENT)) {
                    denizen.setProwling(true);
                    denizen.setHidden(false);
                }
            }
            else if (monsterRoll == 3) {
                if (denizen.getEntityInformation().equals(EntityInformation.GOBLIN)) {
                    denizen.setProwling(true);
                    denizen.setHidden(false);
                }
            }
            else if (monsterRoll == 4) {
                if (denizen.getEntityInformation().equals(EntityInformation.GIANT)) {
                    denizen.setProwling(true);
                    denizen.setHidden(false);
                }
            }
            else if (monsterRoll == 5) {
                if (denizen.getEntityInformation().equals(EntityInformation.SPIDER)) {
                    denizen.setProwling(true);
                    denizen.setHidden(false);
                }
            }
            else if (monsterRoll == 6) {
                if (denizen.getEntityInformation().equals(EntityInformation.GIANTBAT)) {
                    denizen.setProwling(true);
                    denizen.setHidden(false);
                }
            }
        }
    }

    private static void createGiant(final AbstractTile tile, final BoardModel boardModel) {
        LOG.info("Giant created in Mountain from Bones chit");
        final Clearing startForGiants = tile.getClearings()[tile.getClearings().length - 1];
        final Giant giant = new Giant();
        giant.setStartingClearing(startForGiants);
        giant.setCurrentClearing(startForGiants);
        giant.setHidden(true);
        startForGiants.addEntity(giant);
        boardModel.getAbstractMonsters().add(giant);
        boardModel.createNewMeleeSheet(giant);
    }

    private static void createTroll(final AbstractTile tile, final BoardModel boardModel) {
        LOG.info("Troll created in Cave from Bones chit");
        final Clearing startForTrolls = tile.getClearings()[tile.getClearings().length - 1];
        final Troll troll = new Troll();
        troll.setStartingClearing(startForTrolls);
        troll.setCurrentClearing(startForTrolls);
        troll.setHidden(true);
        startForTrolls.addEntity(troll);
        boardModel.getAbstractMonsters().add(troll);
        boardModel.createNewMeleeSheet(troll);
    }

    private static void createSpider(final AbstractTile tile, final BoardModel boardModel) {
        LOG.info("Spider created in Mountain from Dank chit");
        final Clearing startForSpiders = tile.getClearings()[tile.getClearings().length - 1];
        final Spider spider = new Spider();
        spider.setStartingClearing(startForSpiders);
        spider.setCurrentClearing(startForSpiders);
        spider.setHidden(true);
        startForSpiders.addEntity(spider);
        boardModel.getAbstractMonsters().add(spider);
        boardModel.createNewMeleeSheet(spider);
    }

    private static void createDragon(final AbstractTile tile, final BoardModel boardModel) {
        LOG.info("Dragon created from Slither chit");
        final Clearing startForDragons = tile.getClearings()[tile.getClearings().length - 1];
        final Dragon dragon = new Dragon();
        dragon.setStartingClearing(startForDragons);
        dragon.setCurrentClearing(startForDragons);
        dragon.setHidden(true);
        startForDragons.addEntity(dragon);
        boardModel.getAbstractMonsters().add(dragon);
        boardModel.createNewMeleeSheet(dragon);
    }

    private static void createSerpent(final AbstractTile tile, final BoardModel boardModel) {
        LOG.info("Serpent created from Slither chit");
        final Clearing startForSerpents = tile.getClearings()[tile.getClearings().length - 1];
        final Serpent serpent = new Serpent();
        serpent.setStartingClearing(startForSerpents);
        serpent.setCurrentClearing(startForSerpents);
        serpent.setHidden(true);
        startForSerpents.addEntity(serpent);
        boardModel.getAbstractMonsters().add(serpent);
        boardModel.createNewMeleeSheet(serpent);
    }

    private static void createGoblin(final AbstractTile tile, final BoardModel boardModel) {
        LOG.info("Goblin created from Ruins chit");
        final Clearing startForGoblins = tile.getClearings()[tile.getClearings().length - 1];
        final Goblin giantBat = new Goblin();
        giantBat.setStartingClearing(startForGoblins);
        giantBat.setCurrentClearing(startForGoblins);
        giantBat.setHidden(true);
        startForGoblins.addEntity(giantBat);
        boardModel.getAbstractMonsters().add(giantBat);
        boardModel.createNewMeleeSheet(giantBat);
    }

    private static void createGiantBat(final AbstractTile tile, final BoardModel boardModel) {
        LOG.info("Giant bat created from Ruins chit");
        final Clearing startForBats = tile.getClearings()[tile.getClearings().length - 1];
        final GiantBat giantBat = new GiantBat();
        giantBat.setStartingClearing(startForBats);
        giantBat.setCurrentClearing(startForBats);
        giantBat.setHidden(true);
        startForBats.addEntity(giantBat);
        boardModel.getAbstractMonsters().add(giantBat);
        boardModel.createNewMeleeSheet(giantBat);
    }

    private static void createBashkar(final Clearing clearing, final BoardModel boardModel) {
        LOG.info("Failed to create bashkar, none available");
        final AbstractNative bashkar = entityBuilder.getBashkar(clearing);
        if (bashkar != null) {
            bashkar.setStartingClearing(clearing);
            bashkar.setCurrentClearing(clearing);
            clearing.addEntity(bashkar);
            boardModel.getAbstractNatives().add(bashkar);
            boardModel.createNewMeleeSheet(bashkar);
            LOG.info("Bashkar native created");
        }
    }

    private static void createLancer(final Clearing clearing, final BoardModel boardModel) {
        LOG.info("Failed to create lancer, none available");
        final AbstractNative lancer = entityBuilder.getLancer(clearing);
        if (lancer != null) {
            lancer.setStartingClearing(clearing);
            lancer.setCurrentClearing(clearing);
            clearing.addEntity(lancer);
            boardModel.getAbstractNatives().add(lancer);
            boardModel.createNewMeleeSheet(lancer);
            LOG.info("Lancer native created");
        }
    }

    private static void createWoodfolk(final Clearing clearing, final BoardModel boardModel) {
        LOG.info("Failed to create woodfolk, none available");
        final AbstractNative woodfolk = entityBuilder.getWoodfolk(clearing);
        if (woodfolk != null) {
            woodfolk.setStartingClearing(clearing);
            woodfolk.setCurrentClearing(clearing);
            clearing.addEntity(woodfolk);
            boardModel.getAbstractNatives().add(woodfolk);
            boardModel.createNewMeleeSheet(woodfolk);
            LOG.info("Woodfolk native created");
        }
    }

    private static void createCompany(final Clearing clearing, final BoardModel boardModel) {
        LOG.info("Failed to create company, none available");
        final AbstractNative company = entityBuilder.getCompany(clearing);
        if (company != null) {
            company.setStartingClearing(clearing);
            company.setCurrentClearing(clearing);
            clearing.addEntity(company);
            boardModel.getAbstractNatives().add(company);
            boardModel.createNewMeleeSheet(company);
            LOG.info("Company native created");
        }
    }

    private static void createPatrol(final Clearing clearing, final BoardModel boardModel) {
        LOG.info("Patrol native created");
        final AbstractNative patrol = entityBuilder.getPatrol(clearing);
        if (patrol != null) {
            patrol.setStartingClearing(clearing);
            patrol.setCurrentClearing(clearing);
            clearing.addEntity(patrol);
            boardModel.getAbstractNatives().add(patrol);
            boardModel.createNewMeleeSheet(patrol);
        }
    }

}
