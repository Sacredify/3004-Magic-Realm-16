package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.monster.AbstractMonster;
import ca.carleton.magicrealm.entity.natives.AbstractNative;
import ca.carleton.magicrealm.entity.natives.NativeFaction;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.game.combat.MeleeSheet;
import ca.carleton.magicrealm.game.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Some util code for combat.
 *
 * Created with IntelliJ IDEA.
 * Date: 31/03/2015
 * Time: 10:40 AM
 */
public class CombatUtils {

    private static final Logger LOG = LoggerFactory.getLogger(CombatUtils.class);

    private static final String ROLL_ON_MEETING_TABLE_BUY_DRINKS = "Native faction [%s] is in the same clearing with you!\n " +
            "You must roll on the meeting table to check to see if you will be battling them. Do you wish to buy drinks?";

    public static boolean isDoingCombatWithNativeFaction(final Player player, final Clearing location, final NativeFaction faction, final Component parent) {

        LOG.info("Checking to see if {} is battling {}", player.getCharacter().getEntityInformation().convertToCharacterType(), faction);
        int roll = Table.MeetingTable.roll(player, location);
        Relationship relationship = player.getCharacter().getRelationshipWith(faction);

        if (JOptionPane.showConfirmDialog(parent, String.format(ROLL_ON_MEETING_TABLE_BUY_DRINKS, faction)) == JOptionPane.YES_OPTION) {
            player.getCharacter().addGold(-1);
            relationship = Table.MeetingTable.getBoughtDrinksRelationship(relationship);
            LOG.info("Added roll for drinks bonus.");
        }

        boolean toReturn;

        LOG.info("Relationship with native: {}. Roll value: {}.", relationship, roll);

        switch (relationship) {
            case ENEMY:
                toReturn = roll != 1 || roll != 2;
                break;
            case UNFRIENDLY:
                toReturn = roll == 6;
                break;
            default:
                toReturn = false;
        }

        LOG.info("Player battling with {} : {}.", faction, toReturn);
        return toReturn;
    }

    public static void assignNativeFactionsToPlayer(final Player currentPlayer, final Map<NativeFaction, Boolean> fightingNatives, final Map<Entity, Entity> battleAssignments, final Clearing combatSite) {
        fightingNatives.entrySet().stream().filter(Map.Entry::getValue).forEach(entry -> combatSite.getEntities().stream().filter(entity -> entity instanceof AbstractNative).forEach(entity -> {
            final AbstractNative abstractNative = (AbstractNative) entity;
            if (abstractNative.getFaction() == entry.getKey()) {
                battleAssignments.put(currentPlayer.getCharacter(), entity);
                LOG.info("Added {} battling {} to assignments.", entity, currentPlayer.getCharacter());
            }
        }));
    }

    /**
     * Handle logic for when a player kills another.
     *
     * @param killer     the victor.
     * @param killed     the person who died.
     * @param boardModel the board.
     */
    public static void resolvePlayerKilledAnother(final Player killer, final Player killed, final BoardModel boardModel) {
        LOG.info("Transferring items, gold, notoriety from {} to {}.", killer.getCharacter(), killed.getCharacter());
        killer.getCharacter().getItems().addAll(killed.getCharacter().getItems());
        killed.getCharacter().getItems().clear();
        killer.getCharacter().addGold(killed.getCharacter().getCurrentGold());
        killed.getCharacter().addGold(-killed.getCharacter().getCurrentGold());
        killer.getCharacter().addNotoriety(killed.getCharacter().getCurrentNotoriety());
        killed.getCharacter().addNotoriety(-killed.getCharacter().getCurrentNotoriety());
        resolveDeadPlayer(boardModel, killed);
    }

    /**
     * Handle the cleanup of a dead character, and give them a new life.
     *
     * @param board the board.
     * @param dead  the dead player.
     */
    public static void resolveDeadPlayer(final BoardModel board, final Player dead) {
        LOG.info("AUTO REINCARNATION. Giving player a new start at the inn.");
        LOG.info("Creating a new character and Setting location to their starting location ({}).", dead.getStartingLocation());
        board.getClearingForPlayer(dead).removeEntity(dead.getCharacter());
        dead.setCharacter(CharacterFactory.createCharacter(dead.getCharacter().getEntityInformation().convertToCharacterType()));
        dead.restartNewLife();
        board.getClearingOfDwelling(dead.getStartingLocation()).addEntity(dead.getCharacter());
    }

    /**
     * Handle the cleanup of a dead denizen.
     *
     * @param board the board.
     * @param dead  the denizen.
     */
    public static void resolveDeadDenizen(final BoardModel board, final Denizen dead) {
        dead.getCurrentClearing().removeEntity(dead);
        LOG.info("{} removed from {}.", dead, dead.getCurrentClearing());
    }

    /**
     * Increase the strength of a weapon by its sharpness
     *
     * @param strength  the strength.
     * @param sharpness the sharpness.
     * @return the new strength.
     */
    public static Harm increaseStrengthBySharpness(final Harm strength, final int sharpness) {
        int count = 0;
        LOG.info("Increasing {} by {}.", strength, sharpness);
        Harm toReturn = strength;
        while (count < sharpness) {
            toReturn = toReturn.increase();
            count++;
        }
        LOG.info("Increased strength through sharpness. New strength: {}.", toReturn);
        return toReturn;
    }

    /**
     * Adds monsters in the clearing with a player to the list of people who are going to fight.
     *
     * @param board      the board.
     * @param characters the characters in-game.
     */
    public static java.util.List<Entity> getMonstersFightingToday(final BoardModel board, final java.util.List<AbstractCharacter> characters) {
        final java.util.List<Entity> monstersFighting = new ArrayList<>();

        // Assumption for now that 2 or more people aren't in the same clearing.
        for (final AbstractCharacter character : characters) {
            if (!character.isHidden()) {
                LOG.info("{} isn't hidden! Any monsters in the clearing will attack.", character);
                final java.util.List<Entity> monsters = board.getClearingForCharacter(character).getEntities().stream()
                        .filter(entity -> entity instanceof AbstractMonster).collect(Collectors.toList());

                monsters.stream().forEach(monster -> {
                    final MeleeSheet sheetForMonster = board.getMeleeSheet(monster);
                    sheetForMonster.setTarget(character);
                    LOG.info("{} is now targeting {}!", monster, character);
                    monstersFighting.add(monster);
                });

            } else {
                LOG.info("{} is hidden - monsters won't attack them this turn.", character);
            }
        }
        return monstersFighting;
    }


}
