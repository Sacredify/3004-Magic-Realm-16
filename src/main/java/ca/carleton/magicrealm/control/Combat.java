package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.natives.AbstractNative;
import ca.carleton.magicrealm.entity.natives.NativeFaction;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.CombatResults;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.game.combat.MeleeSheet;
import ca.carleton.magicrealm.game.table.Table;
import ca.carleton.magicrealm.item.armor.AbstractArmor;
import ca.carleton.magicrealm.item.weapon.AbstractWeapon;
import ca.carleton.magicrealm.item.weapon.AttackType;
import ca.carleton.magicrealm.item.weapon.Dagger;
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
        nativesInClearing.stream().forEach(nativeFaction -> nativeCombat.put(nativeFaction, isDoingCombatWithNativeFaction(currentPlayer, combatSite, nativeFaction, parent)));
        LOG.info("Done checking to if doing native combat.");

        final Map<Entity, Entity> battleAssignments = new HashMap<Entity, Entity>();

        LOG.info("Step 2: Assign natives that will be fighting player.");
        assignNativeFactionsToPlayer(currentPlayer, nativeCombat, battleAssignments, combatSite);
        LOG.info("Done assigning valid natives.");

        LOG.info("Step 3: Luring. Begin voluntary assignment of enemies to character.");
        // TODO need to throw in a GUI into a dialog that will return a list of entities that the user wishes to fight.
        LOG.info("Done luring for player.");

        LOG.info("Step 4: Random Assignment. Begin assigning all remaining unassigned natives to player..");
        // TODO
        LOG.info("Done random assignment.");

        LOG.info("Step 5: Deployment and Charging. Begin asking user is they want to charge other characters in clearing...");
        // TODO
        LOG.info("Done charging options.");

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

    private static void assignNativeFactionsToPlayer(final Player currentPlayer, final Map<NativeFaction, Boolean> fightingNatives, final Map<Entity, Entity> battleAssignments, final Clearing combatSite) {
        fightingNatives.entrySet().stream().filter(Map.Entry::getValue).forEach(entry -> combatSite.getEntities().stream().filter(entity -> entity instanceof AbstractNative).forEach(entity -> {
            final AbstractNative abstractNative = (AbstractNative) entity;
            if (abstractNative.getFaction() == entry.getKey()) {
                battleAssignments.put(currentPlayer.getCharacter(), entity);
                LOG.info("Added {} battling {} to assignments.", entity, currentPlayer.getCharacter());
            }
        }));
    }

    /**
     * Initiate combat between two players.
     *
     * @param boardModel the board.
     * @param playerOne  the first player.
     * @param playerTwo  the second player.
     */
    public void doCombat(final BoardGUIModel boardModel, final Player playerOne, final Player playerTwo) {
        final CombatResults results = this.resolveCombat(boardModel, playerOne, playerTwo);

        // Player two died. Transfer results to other player.
        if (results.isDead()) {
            LOG.info("Defender died. Transferring items, gold, notoriety to other player.");
            playerOne.getCharacter().getItems().addAll(playerTwo.getCharacter().getItems());
            playerTwo.getCharacter().getItems().clear();
            playerOne.getCharacter().addGold(playerTwo.getCharacter().getCurrentGold());
            playerTwo.getCharacter().addGold(-playerTwo.getCharacter().getCurrentGold());
            playerOne.getCharacter().addNotoriety(playerTwo.getCharacter().getCurrentNotoriety());
            playerTwo.getCharacter().addNotoriety(-playerTwo.getCharacter().getCurrentNotoriety());

            LOG.info("AUTO REINCARNATION. Giving player a new start.");
            LOG.info("Creating a new character and Setting location to starting location (inn).");
            boardModel.getClearingForPlayer(playerTwo).removeEntity(playerTwo.getCharacter());
            playerTwo.setCharacter(CharacterFactory.createCharacter(playerTwo.getCharacter().getEntityInformation().convertToCharacterType()));
            boardModel.getStartingLocation().addEntity(playerTwo.getCharacter());
        }
    }

    /**
     * Resolve a round of combat between two characters. This assumes playerOne is attacking player two (who is defending).
     *
     * @param boardModel the board model.
     * @param playerOne  the first player.
     * @param playerTwo  the second player.
     * @return the results for the DEFENDER.
     */
    private CombatResults resolveCombat(final BoardGUIModel boardModel, final Player playerOne, final Player playerTwo) {
        final MeleeSheet attacker = boardModel.getMeleeSheetForPlayer(playerOne);
        final MeleeSheet defender = boardModel.getMeleeSheetForPlayer(playerTwo);

        // Determine if attackers attack hits through defenders maneuver
        boolean attackHit = attacker.getAttackDirection().matches(defender.getManeuver());
        LOG.info("Attacker attacked with {}. Defender maneuvered with {}. Attack landed result: {}", attacker.getAttackDirection(), defender.getManeuver(), attackHit);
        // The attack can still hit even if they don't match if the attack time is faster than the maneuver time.
        if (!attackHit) {
            LOG.info("Attack did not land. Checking weapon times for modification...");
            attackHit = attacker.getAttackChit().getTime() < defender.getManeuverChit().getTime();
            LOG.info("Attack landed after checking times: {}", attackHit);
        }
        if (attackHit) {

            // Determine the harm (strength) of the attack
            AbstractWeapon weapon = attacker.getAttackWeapon();

            // No weapon (using only a fight chit) means they use a dagger.
            if (weapon == null) {
                weapon = new Dagger();
                LOG.info("Attacker had no weapon equipped. Using a dagger.");
            }

            // Increase strength by the sharpness. If the weapon is striking, increase by one if the fight chit strength is greater
            // than the power of the weapon. For missile attacks, roll on the table.
            Harm attackStrength = increaseStrengthBySharpness(weapon.getStrength(), weapon.getSharpness());
            LOG.info("Attacker weapon strength: {}", attackStrength);
            if (weapon.getAttackType() == AttackType.STRIKING) {
                if (attacker.getAttackChit().getStrength().greaterThan(attackStrength)) {
                    attackStrength = attackStrength.increase();
                    LOG.info("Attack chit stronger. Increased weapon strength to {}.", attackStrength);
                }
            } else {
                attackStrength = Table.MissileTable.roll(playerOne, attackStrength);
                LOG.info("Rolled on missile table. New strength: {}.", attackStrength);
            }

            // Check to see if the armor the defender may be wearing intercepts.
            boolean intercepted = false;
            final AbstractArmor armor = defender.getArmor();
            if (armor != null) {
                intercepted = armor.getProtectsAgainst().intercepts(attacker.getAttackDirection());
            }
            LOG.info("Defender armor protects against {}. Attack intercepted: {}", armor.getProtectsAgainst(), intercepted);

            CombatResults combatResults;

            if (intercepted) {
                if (attackStrength == armor.getWeight()) {
                    if (armor.isDamaged()) {
                        // Armor that is already damaged is destroyed (removed from inventory).
                        playerTwo.getCharacter().getItems().remove(armor);
                        LOG.info("Armor was already damaged and has been destroyed! Removed from inventory of {}.", playerTwo.getCharacter());
                    } else {
                        armor.setDamaged(true);
                        LOG.info("Armor has been damaged by the hit.");
                    }
                } else if (attackStrength.greaterThan(armor.getWeight())) {
                    // Any attack greater than the armor weight instantly destroys it
                    playerTwo.getCharacter().getItems().remove(armor);
                    LOG.info("Armor was destroyed by an attack greater than the weight of the armor!. Removed from inventory of {}.", playerTwo.getCharacter());
                }
                // Only MEDIUM and higher makes us wound stuff.
                if (attackStrength.greaterThan(Harm.LIGHT)) {
                    combatResults = CombatResults.createResultsFor(playerTwo, false, true);
                } else {
                    combatResults = CombatResults.createResultsFor(playerTwo, false, false);
                }

            } else {
                // Target is killed if the strength of the attack >= the players health.
                if (attackStrength.greaterThan(playerTwo.getCharacter().getVulnerability()) || attackStrength == playerTwo.getCharacter().getVulnerability()) {
                    combatResults = CombatResults.createResultsFor(playerTwo, true, false);
                } else {
                    combatResults = CombatResults.createResultsFor(playerTwo, false, true);
                }
            }

            // Weapons automatically get un-alerted.
            weapon.setAlert(false);

            return combatResults;
        } else {
            LOG.info("Attacker missed. Ending round of combat.");
            return CombatResults.createResultsFor(playerTwo, false, false);
        }
    }

    /**
     * Increase the strength of a weapon by its sharpness
     *
     * @param strength  the strength.
     * @param sharpness the sharpness.
     * @return the new strength.
     */
    private static Harm increaseStrengthBySharpness(final Harm strength, final int sharpness) {
        int count = 0;
        while (count < sharpness) {
            strength.increase();
            count++;
        }
        return strength;
    }
}
