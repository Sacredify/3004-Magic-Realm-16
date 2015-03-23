package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.entity.natives.AbstractNative;
import ca.carleton.magicrealm.entity.natives.NativeFaction;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * Date: 23/03/2015
 * Time: 1:58 PM
 */
public class Combat {

    private static final Logger LOG = LoggerFactory.getLogger(Combat.class);

    private static final String ROLL_ON_MEETING_TABLE_BUY_DRINKS = "Native faction [%s] is in the same clearing with you!\n " +
            "You must roll on the meeting table to check to see if you will be battling them. Do you wish to buy drinks?";

    /**
     * Starts combat in the clearing for the given player.
     *
     * @param boardModel    the board data.
     * @param currentPlayer the current player.
     * @param parent        the parent frame to attach any messages to.
     */
    public static void doCombat(final BoardGUIModel boardModel, final Player currentPlayer, final Component parent) {

        final Clearing combatSite = boardModel.getClearingForPlayer(currentPlayer);

        LOG.info("Step 1: Checking to see if player is battling any unhired native groups in the clearing this day.");
        final Map<NativeFaction, Boolean> nativeCombat = new HashMap<NativeFaction, Boolean>();
        final List<NativeFaction> nativesInClearing = getNativeFactionsInClearing(combatSite);
        nativesInClearing.stream().forEach(nativeFaction -> {
            nativeCombat.put(nativeFaction, isDoingCombatWithNativeFaction(currentPlayer, combatSite, nativeFaction, parent));
        });
        LOG.info("Done checking to if doing native combat.");

    }

    private static List<NativeFaction> getNativeFactionsInClearing(final Clearing clearing) {
        final List<NativeFaction> factions = new ArrayList<NativeFaction>();
        clearing.getEntities().stream().filter(entity -> entity instanceof AbstractNative).forEach(entity -> {
            final NativeFaction faction = ((AbstractNative) entity).getFaction();
            if (!factions.contains(faction)) {
                factions.add(faction);
            }
        });
        return factions;
    }

    private static boolean isDoingCombatWithNativeFaction(final Player player, final Clearing location, final NativeFaction faction, final Component parent) {

        LOG.info("Checking to see if {} is battling {}", player.getCharacter().getEntityInformation().convertToCharacterType(), faction);
        int roll = Table.MeetingTable.roll(player, location);
        Relationship relationship = player.getCharacter().getRelationshipWith(faction);

        if (JOptionPane.showConfirmDialog(parent, String.format(ROLL_ON_MEETING_TABLE_BUY_DRINKS, faction)) == JOptionPane.YES_OPTION) {
            player.getCharacter().addGold(-1);
            relationship =  Table.MeetingTable.getBoughtDrinksRelationship(relationship);
            LOG.info("Added roll for drinks bonus.");
        }

        boolean toReturn;

        LOG.info("Relationship with native: {}. Roll value: {}.", relationship, roll);

        switch (relationship){
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

}
