package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.chit.Dwelling;
import ca.carleton.magicrealm.entity.natives.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 23/02/15
 * Time: 4:03 AM
 */
public class EntityBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(EntityBuilder.class);

    private static final int NUMBER_OF_BASHKARS = 6;

    private static final int NUMBER_OF_WOODFOLK = 3;

    private static final int NUMBER_OF_LANCERS = 4;

    private static final int NUMBER_OF_PATROL = 3;

    private static final int NUMBER_OF_COMPANY = 7;

    private static final List<AbstractNative> orderNatives = new ArrayList<>();

    private static final List<AbstractNative> soldierNatives = new ArrayList<>();

    private static final List<AbstractNative> rogueNatives = new ArrayList<>();

    private static final List<AbstractNative> guardNatives = new ArrayList<>();

    private static final List<AbstractNative> bashkarNatives = new ArrayList<>();

    private static final List<AbstractNative> woodfolkNatives = new ArrayList<>();

    private static final List<AbstractNative> lancerNatives = new ArrayList<>();

    private static final List<AbstractNative> companyNatives = new ArrayList<>();

    private static final List<AbstractNative> patrolNatives = new ArrayList<>();

    /**
     * Places the entities on the given board.
     *
     * @param board the board.
     */
    public static void placeEntities(final BoardModel board) {

        LOG.debug("Starting build of natives that start at dwellings...");
        buildOrder(board);
        buildSoldiers(board);
        buildRogues(board);
        buildGuard(board);
        buildBashkar();
        buildCompany();
        buildLancer();
        buildPatrol();
        buildWoodfolk();
        LOG.debug("Done building and placement natives that start at dwellings...");

        // Build HOUSE Solider natives

        // Build INN Rogue natives

        // Build GUARD HOUSE guard natives

        LOG.debug("Creating melee sheets for natives...");
        final List<AbstractNative> allNatives = new ArrayList<AbstractNative>();
        allNatives.addAll(orderNatives);
        allNatives.addAll(soldierNatives);
        allNatives.addAll(rogueNatives);
        allNatives.addAll(guardNatives);
        allNatives.addAll(bashkarNatives);
        allNatives.addAll(lancerNatives);
        allNatives.addAll(companyNatives);
        allNatives.addAll(woodfolkNatives);
        allNatives.addAll(patrolNatives);
        allNatives.forEach(board::createNewMeleeSheet);
        LOG.debug("Done creating melee sheets for natives.");

    }

    /**
     * Build the order natives that start in the chapel.
     *
     * @param board the board.
     */
    private static void buildOrder(final BoardModel board) {

        final Clearing startingLocation = board.getClearingOfDwelling(Dwelling.CHAPEL);

        AbstractNative orderKnight1 = NativeFactory.createNative(NativeFaction.ORDER, NativeType.KNIGHT);
        orderKnight1.markLeader();
        AbstractNative orderKnight2 = NativeFactory.createNative(NativeFaction.ORDER, NativeType.KNIGHT);
        AbstractNative orderKnight3 = NativeFactory.createNative(NativeFaction.ORDER, NativeType.KNIGHT);
        AbstractNative orderKnight4 = NativeFactory.createNative(NativeFaction.ORDER, NativeType.KNIGHT);

        orderNatives.add(orderKnight1);
        orderNatives.add(orderKnight2);
        orderNatives.add(orderKnight3);
        orderNatives.add(orderKnight4);

        for (final AbstractNative order : orderNatives) {
            order.setStartingClearing(startingLocation);
            order.setCurrentClearing(startingLocation);
            order.getCurrentClearing().addEntity(order);
            order.setProwling(false);
            order.setHidden(false);
        }

    }

    /**
     * Build the soldier natives that start in the house.
     *
     * @param board the board.
     */
    private static void buildSoldiers(final BoardModel board) {

        final Clearing startingLocation = board.getClearingOfDwelling(Dwelling.HOUSE);

        AbstractNative soldier1 = NativeFactory.createNative(NativeFaction.SOLDIERS, NativeType.CROSSBOW_MAN);
        soldier1.markLeader();
        AbstractNative soldier2 = NativeFactory.createNative(NativeFaction.SOLDIERS, NativeType.GREAT_SWORDSMAN);
        AbstractNative soldier3 = NativeFactory.createNative(NativeFaction.SOLDIERS, NativeType.PIKEMAN);
        AbstractNative soldier4 = NativeFactory.createNative(NativeFaction.SOLDIERS, NativeType.PIKEMAN);

        soldierNatives.add(soldier1);
        soldierNatives.add(soldier2);
        soldierNatives.add(soldier3);
        soldierNatives.add(soldier4);

        for (final AbstractNative soldier : soldierNatives) {
            soldier.setStartingClearing(startingLocation);
            soldier.setCurrentClearing(startingLocation);
            soldier.getCurrentClearing().addEntity(soldier);
            soldier.setProwling(false);
            soldier.setHidden(false);
        }

    }

    /**
     * Build the rogue natives that start in the inn.
     *
     * @param board the board.
     */
    private static void buildRogues(final BoardModel board) {

        final Clearing startingLocation = board.getClearingOfDwelling(Dwelling.INN);

        AbstractNative rogue1 = NativeFactory.createNative(NativeFaction.ROGUES, NativeType.ARCHER);
        rogue1.markLeader();
        AbstractNative rogue2 = NativeFactory.createNative(NativeFaction.ROGUES, NativeType.ASSASSIN);
        AbstractNative rogue3 = NativeFactory.createNative(NativeFaction.ROGUES, NativeType.ASSASSIN);
        AbstractNative rogue4 = NativeFactory.createNative(NativeFaction.ROGUES, NativeType.GREAT_AXEMAN);
        AbstractNative rogue5 = NativeFactory.createNative(NativeFaction.ROGUES, NativeType.GREAT_AXEMAN);
        AbstractNative rogue6 = NativeFactory.createNative(NativeFaction.ROGUES, NativeType.SHORT_SWORDSMAN);
        AbstractNative rogue7 = NativeFactory.createNative(NativeFaction.ROGUES, NativeType.SWORDSMAN);
        AbstractNative rogue8 = NativeFactory.createNative(NativeFaction.ROGUES, NativeType.SWORDSMAN);

        rogueNatives.add(rogue1);
        rogueNatives.add(rogue2);
        rogueNatives.add(rogue3);
        rogueNatives.add(rogue4);
        rogueNatives.add(rogue5);
        rogueNatives.add(rogue6);
        rogueNatives.add(rogue7);
        rogueNatives.add(rogue8);

        for (final AbstractNative rogue : rogueNatives) {
            rogue.setStartingClearing(startingLocation);
            rogue.setCurrentClearing(startingLocation);
            rogue.getCurrentClearing().addEntity(rogue);
            rogue.setProwling(false);
            rogue.setHidden(false);
        }

    }

    /**
     * Build the guard natives that start in the guard house.
     *
     * @param board the board.
     */
    private static void buildGuard(final BoardModel board) {

        final Clearing startingLocation = board.getClearingOfDwelling(Dwelling.GUARD);

        AbstractNative guard1 = NativeFactory.createNative(NativeFaction.GUARD, NativeType.GREAT_SWORDSMAN);
        guard1.markLeader();
        AbstractNative guard2 = NativeFactory.createNative(NativeFaction.GUARD, NativeType.GREAT_SWORDSMAN);
        AbstractNative guard3 = NativeFactory.createNative(NativeFaction.GUARD, NativeType.GREAT_SWORDSMAN);

        guardNatives.add(guard1);
        guardNatives.add(guard2);
        guardNatives.add(guard3);

        for (final AbstractNative guard : guardNatives) {
            guard.setStartingClearing(startingLocation);
            guard.setCurrentClearing(startingLocation);
            guard.getCurrentClearing().addEntity(guard);
            guard.setProwling(false);
            guard.setHidden(false);
        }

    }

    /**
     * Return a bashkar if there are more available in the queue
     * @param clearing
     * @return
     */
    public AbstractNative getBashkar(final Clearing clearing) {
        AbstractNative bashkar;
        if (bashkarNatives.size() > 0) {
            bashkar = bashkarNatives.remove(0);
            bashkar.setStartingClearing(clearing);
            bashkar.setCurrentClearing(clearing);
            clearing.addEntity(bashkar);
            return bashkar;
        }
        else {
            return null;
        }
    }

    /**
     * Build 6 bashkars for the list of bashkars available
     */
    private static void buildBashkar() {
        boolean firstOne = true;
        for (int i = 0; i < NUMBER_OF_BASHKARS; i++) {
            AbstractNative bashkarRaider = NativeFactory.createNative(NativeFaction.BASHKARS, NativeType.RAIDER);
            // Don't set the clearings yet, wait until they are rolled
            if (firstOne) {
                bashkarRaider.markLeader();
                firstOne = false;
            }
            bashkarRaider.setProwling(true);
            bashkarRaider.setHidden(false);
            bashkarNatives.add(bashkarRaider);
        }
    }

    /**
     * Return a woodfolk from the list if available
     * @param clearing
     * @return
     */
    public AbstractNative getWoodfolk(final Clearing clearing) {
        if (woodfolkNatives.size() > 0) {
            AbstractNative woodfolk = woodfolkNatives.remove(0);
            woodfolk.setStartingClearing(clearing);
            woodfolk.setCurrentClearing(clearing);
            clearing.addEntity(woodfolk);
            return woodfolk;
        }
        else {
            return null;
        }
    }

    /**
     * Build 3 woodfolk archers for the list
     */
    private static void buildWoodfolk() {
        boolean firstOne = true;
        for (int i = 0; i < NUMBER_OF_WOODFOLK; i++) {
            AbstractNative woodfolkArcher = NativeFactory.createNative(NativeFaction.WOODFOLK, NativeType.ARCHER);
            if (firstOne) {
                woodfolkArcher.markLeader();
                firstOne = false;
            }
            woodfolkArcher.setProwling(true);
            woodfolkArcher.setHidden(false);
            woodfolkNatives.add(woodfolkArcher);
        }
    }

    /**
     * Get an available lancer from the list
     * @param clearing
     * @return
     */
    public AbstractNative getLancer(final Clearing clearing) {
        if (lancerNatives.size() > 0) {
            AbstractNative lancer = lancerNatives.remove(0);
            lancer.setStartingClearing(clearing);
            lancer.setCurrentClearing(clearing);
            clearing.addEntity(lancer);
            return lancer;
        }
        else {
            return null;
        }
    }

    /**
     * Build the lancers for the list
     */
    private static void buildLancer() {
        boolean firstOne = true;
        for (int i = 0; i < NUMBER_OF_LANCERS; i++) {
            AbstractNative lancerLancer = NativeFactory.createNative(NativeFaction.LANCERS, NativeType.LANCER);
            if (firstOne) {
                lancerLancer.markLeader();
                firstOne = false;
            }
            lancerLancer.setProwling(true);
            lancerLancer.setHidden(false);
            lancerNatives.add(lancerLancer);
        }
    }

    /**
     * Get an available company from the list
     * @param clearing
     * @return
     */
    public AbstractNative getCompany(final Clearing clearing) {
        if (companyNatives.size() > 0) {
            AbstractNative company = companyNatives.remove(0);
            company.setStartingClearing(clearing);
            company.setCurrentClearing(clearing);
            clearing.addEntity(company);
            return company;
        }
        else {
            return null;
        }
    }

    /**
     * build the company for the list
     */
    private static void buildCompany() {

        AbstractNative companyCrowssbowman = NativeFactory.createNative(NativeFaction.COMPANY, NativeType.CROSSBOW_MAN);
        companyCrowssbowman.markLeader();
        companyCrowssbowman.setProwling(true);
        companyCrowssbowman.setHidden(false);
        companyNatives.add(companyCrowssbowman);

        AbstractNative companyGreatSwordsman = NativeFactory.createNative(NativeFaction.COMPANY, NativeType.GREAT_SWORDSMAN);
        companyGreatSwordsman.setProwling(true);
        companyGreatSwordsman.setHidden(false);
        companyNatives.add(companyGreatSwordsman);

        for (int i = 0; i < 3; i++) {
            AbstractNative companyPikeman = NativeFactory.createNative(NativeFaction.COMPANY, NativeType.PIKEMAN);
            companyPikeman.setProwling(true);
            companyPikeman.setHidden(false);
            companyNatives.add(companyPikeman);
        }

        for (int i = 0; i < 2; i++) {
            AbstractNative companySwordsman = NativeFactory.createNative(NativeFaction.COMPANY, NativeType.SHORT_SWORDSMAN);
            companySwordsman.setProwling(true);
            companySwordsman.setHidden(false);
            companyNatives.add(companySwordsman);
        }

    }

    /**
     * Get an available patrol from the list
     * @param clearing
     * @return
     */
    public AbstractNative getPatrol(final Clearing clearing) {
        if (patrolNatives.size() > 0) {
            AbstractNative patrol = patrolNatives.remove(0);
            patrol.setStartingClearing(clearing);
            patrol.setCurrentClearing(clearing);
            clearing.addEntity(patrol);
            return patrol;
        }
        else {
            return null;
        }
    }

    /**
     * Build the patrol natives for the list
     */
    private static void buildPatrol() {
        boolean firstOne = true;
        for (int i = 0; i < NUMBER_OF_PATROL; i++) {
            AbstractNative patrolSwordsman = NativeFactory.createNative(NativeFaction.PATROL, NativeType.SHORT_SWORDSMAN);
            if (firstOne) {
                patrolSwordsman.markLeader();
                firstOne = false;
            }
            patrolSwordsman.setProwling(true);
            patrolSwordsman.setHidden(false);
            lancerNatives.add(patrolSwordsman);
        }
    }
}
