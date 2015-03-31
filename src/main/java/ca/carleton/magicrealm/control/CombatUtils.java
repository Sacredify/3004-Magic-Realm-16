package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.entity.natives.AbstractNative;
import ca.carleton.magicrealm.entity.natives.NativeFaction;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

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

}
