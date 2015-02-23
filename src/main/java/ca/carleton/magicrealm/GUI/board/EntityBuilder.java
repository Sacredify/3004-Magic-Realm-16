package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.chit.Dwelling;
import ca.carleton.magicrealm.entity.natives.AbstractNative;
import ca.carleton.magicrealm.entity.natives.NativeFaction;
import ca.carleton.magicrealm.entity.natives.NativeFactory;
import ca.carleton.magicrealm.entity.natives.NativeType;
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

    private static final List<AbstractNative> orderNatives = new ArrayList<AbstractNative>();

    private static final List<AbstractNative> soldierNatives = new ArrayList<AbstractNative>();

    private static final List<AbstractNative> rogueNatives = new ArrayList<AbstractNative>();

    private static final List<AbstractNative> guardNatvies = new ArrayList<AbstractNative>();

    /**
     * Test runner.
     */
    public static void main(String[] args) {
        EntityBuilder.placeEntities(new BoardGUIModel());
    }

    /**
     * Places the entities on the given board.
     *
     * @param board the board.
     */
    public static void placeEntities(final BoardGUIModel board) {

        LOG.info("Starting build of natives that start at dwellings...");
        buildOrder(board);
        buildSoldiers(board);
        buildRogues(board);
        buildGuard(board);
        LOG.info("Done building and placement natives that start at dwellings...");

        // Build HOUSE Solider natives

        // Build INN Rogue natives

        // Build GUARD HOUSE guard natvies


    }

    /**
     * Build the order natives that start in the chapel.
     *
     * @param board the board.
     */
    private static void buildOrder(final BoardGUIModel board) {

        final Clearing startingLocation = board.getClearingOfDwelling(Dwelling.CHAPEL);

        AbstractNative orderKnight1 = NativeFactory.createNative(NativeFaction.ORDER, NativeType.KNIGHT);
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
    private static void buildSoldiers(final BoardGUIModel board) {

        final Clearing startingLocation = board.getClearingOfDwelling(Dwelling.HOUSE);

        AbstractNative soldier1 = NativeFactory.createNative(NativeFaction.SOLDIERS, NativeType.CROSSBOW_MAN);
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
    private static void buildRogues(final BoardGUIModel board) {

        final Clearing startingLocation = board.getClearingOfDwelling(Dwelling.INN);

        AbstractNative rogue1 = NativeFactory.createNative(NativeFaction.SOLDIERS, NativeType.ARCHER);
        AbstractNative rogue2 = NativeFactory.createNative(NativeFaction.SOLDIERS, NativeType.ASSASSIN);
        AbstractNative rogue3 = NativeFactory.createNative(NativeFaction.SOLDIERS, NativeType.ASSASSIN);
        AbstractNative rogue4 = NativeFactory.createNative(NativeFaction.SOLDIERS, NativeType.GREAT_AXEMAN);
        AbstractNative rogue5 = NativeFactory.createNative(NativeFaction.SOLDIERS, NativeType.GREAT_AXEMAN);
        AbstractNative rogue6 = NativeFactory.createNative(NativeFaction.SOLDIERS, NativeType.SHORT_SWORDSMAN);
        AbstractNative rogue7 = NativeFactory.createNative(NativeFaction.SOLDIERS, NativeType.SWORDSMAN);
        AbstractNative rogue8 = NativeFactory.createNative(NativeFaction.SOLDIERS, NativeType.SWORDSMAN);

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
    private static void buildGuard(final BoardGUIModel board) {

        final Clearing startingLocation = board.getClearingOfDwelling(Dwelling.GUARD);

        AbstractNative guard1 = NativeFactory.createNative(NativeFaction.GUARD, NativeType.GREAT_SWORDSMAN);
        AbstractNative guard2 = NativeFactory.createNative(NativeFaction.GUARD, NativeType.GREAT_SWORDSMAN);
        AbstractNative guard3 = NativeFactory.createNative(NativeFaction.GUARD, NativeType.GREAT_SWORDSMAN);

        rogueNatives.add(guard1);
        rogueNatives.add(guard2);
        rogueNatives.add(guard3);

        for (final AbstractNative guard : rogueNatives) {
            guard.setStartingClearing(startingLocation);
            guard.setCurrentClearing(startingLocation);
            guard.getCurrentClearing().addEntity(guard);
            guard.setProwling(false);
            guard.setHidden(false);
        }

    }

}
