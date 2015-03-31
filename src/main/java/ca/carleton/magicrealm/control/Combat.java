package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.natives.NativeFaction;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.AttackDirection;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.game.combat.Maneuver;
import ca.carleton.magicrealm.game.combat.MeleeSheet;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.game.combat.chit.ActionType;
import ca.carleton.magicrealm.game.table.Table;
import ca.carleton.magicrealm.item.Item;
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
import java.util.stream.Collectors;


/**
 * Created with IntelliJ IDEA.
 * Date: 23/03/2015
 * Time: 1:58 PM
 */
public class Combat {

    private static final Logger LOG = LoggerFactory.getLogger(Combat.class);

    /**
     * Fill out the melee sheet for a given player.
     *
     * @param boardModel the board.
     * @param player     the player.
     * @param parent     the parent UI to hook with.
     */
    public static void fillOutMeleeSheet(final BoardModel boardModel, final Player player, final Component parent) {
        final MeleeSheet playerSheet = boardModel.getMeleeSheet(player);

        final Clearing clearingOfCombat = boardModel.getClearingForPlayer(player);

        JOptionPane.showMessageDialog(parent, "Now starting combat functions. Please fill out your melee sheet with the following dialogs.", "Combat", JOptionPane.INFORMATION_MESSAGE);

        LOG.info("Starting attack options.");
        LOG.info("Showing target select.");
        final List<Entity> potentialTargets = clearingOfCombat.getEntities().stream().filter(entity -> !entity.equals(player.getCharacter())).collect(Collectors.toList());
        final Entity target = (Entity) JOptionPane.showInputDialog(parent, "Combat Step 1: Select a target:", "Combat",
                JOptionPane.QUESTION_MESSAGE, null, potentialTargets.toArray(), potentialTargets.get(0));
        playerSheet.setTarget(target);

        LOG.info("Showing attack direction select.");
        final AttackDirection attackDirection = (AttackDirection) JOptionPane.showInputDialog(parent, "Combat Step 2: Select an attack direction", "Combat",
                JOptionPane.QUESTION_MESSAGE, null, AttackDirection.values(), AttackDirection.values()[0]);
        playerSheet.setAttackDirection(attackDirection);

        LOG.info("Showing weapon select.");
        final List<Item> weapons = player.getCharacter().getItems().stream().filter(item -> item instanceof AbstractWeapon).collect(Collectors.toList());
        // Handle the possibility they don't have any weapons.
        final List<Object> weaponsWithNone = new ArrayList<Object>(weapons);
        weaponsWithNone.add("None (dagger)");
        final Object weapon = JOptionPane.showInputDialog(parent, "Combat Step 3: Select a weapon:", "Combat",
                JOptionPane.QUESTION_MESSAGE, null, weaponsWithNone.toArray(), weaponsWithNone.get(0));
        // Set the weapon if they actually chose a weapon.
        if (!(weapon instanceof String)) {
            playerSheet.setAttackWeapon((AbstractWeapon) weapon);
        }

        int numberOfAsterisksRemaining = 2;

        LOG.info("Showing fight chit select.");
        final List<ActionChit> fightChits = player.getCharacter().getActionChits().stream().filter(chit -> chit.getAction() == ActionType.FIGHT).collect(Collectors.toList());
        final ActionChit fightChit = (ActionChit) JOptionPane.showInputDialog(parent, "Combat Step 4: Select a fight chit to attack:", "Combat",
                JOptionPane.QUESTION_MESSAGE, null, fightChits.toArray(), fightChits.get(0));
        playerSheet.setAttackChit(fightChit);
        numberOfAsterisksRemaining -= fightChit.getFatigueAsterisks();

        LOG.info("Starting defense options");
        LOG.info("Showing maneuver select.");
        final Maneuver maneuver = (Maneuver) JOptionPane.showInputDialog(parent, "Combat Step 5: Select a maneuver (dodge) direction:", "Combat",
                JOptionPane.QUESTION_MESSAGE, null, Maneuver.values(), Maneuver.values()[0]);
        playerSheet.setManeuver(maneuver);


        LOG.info("Showing maneuver chit.");
        // Lambda issue... need to use final variables, wat?
        final int finalNumberOfAsterisksRemaining = numberOfAsterisksRemaining;
        final List<ActionChit> moveChits = player.getCharacter().getActionChits().stream().filter(
                chit -> (chit.getAction() == ActionType.MOVE || chit.getAction() == ActionType.DUCK) && finalNumberOfAsterisksRemaining - chit.getFatigueAsterisks() >= 0
        ).collect(Collectors.toList());
        final ActionChit moveChit = (ActionChit) JOptionPane.showInputDialog(parent, "Combat Step 6: Select a move chit to dodge:", "Combat",
                JOptionPane.QUESTION_MESSAGE, null, moveChits.toArray(), moveChits.get(0));
        playerSheet.setManeuverChit(moveChit);

        LOG.info("Showing armor select.");
        final List<Item> armors = player.getCharacter().getItems().stream().filter(item -> item instanceof AbstractArmor).collect(Collectors.toList());
        // Handle the possibility they don't have any armor.
        final List<Object> armorsWithNone = new ArrayList<Object>(armors);
        armorsWithNone.add("None");
        final Object armor = JOptionPane.showInputDialog(parent, "Combat Step 7: Select armor to wear (if any):", "Combat",
                JOptionPane.QUESTION_MESSAGE, null, armorsWithNone.toArray(), armorsWithNone.get(0));
        // Set the armor if they actually chose a armor.
        if (!(armor instanceof String)) {
            playerSheet.setArmor((AbstractArmor) armor);
        }

        LOG.info("Done filling out {}'s melee sheet.", player.getCharacter());

    }

    /**
     * Starts combat in the clearing for the given player.
     *
     * @param boardModel    the board data.
     * @param currentPlayer the current player.
     * @param parent        the parent frame to attach any messages to.
     */
    @SuppressWarnings("unused")
    @Deprecated
    private static void doCombat(final BoardModel boardModel, final Player currentPlayer, final Component parent) {

        final Clearing combatSite = boardModel.getClearingForPlayer(currentPlayer);

        LOG.info("Step 1: Checking to see if player is battling any unhired native groups in the clearing this day.");
        final Map<NativeFaction, Boolean> nativeCombat = new HashMap<NativeFaction, Boolean>();
        final List<NativeFaction> nativesInClearing = boardModel.getNativeFactionsInClearing(combatSite);
        nativesInClearing.stream().forEach(nativeFaction -> nativeCombat.put(nativeFaction, CombatUtils.isDoingCombatWithNativeFaction(currentPlayer, combatSite, nativeFaction, parent)));
        LOG.info("Done checking to if doing native combat.");

        final Map<Entity, Entity> battleAssignments = new HashMap<Entity, Entity>();

        LOG.info("Step 2: Assign natives that will be fighting player.");
        CombatUtils.assignNativeFactionsToPlayer(currentPlayer, nativeCombat, battleAssignments, combatSite);
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

    /**
     * Initiate combat between two players.
     *
     * @param boardModel the board.
     * @param playerOne  the first player.
     * @param playerTwo  the second player.
     */
    public static void doCombat(final BoardModel boardModel, final Player playerOne, final Player playerTwo) {

        final MeleeSheet playerOneSheet = boardModel.getMeleeSheet(playerOne);
        final MeleeSheet playerTwoSheet = boardModel.getMeleeSheet(playerTwo);

        // Determine who would go first, based on their weapon length.
        int playerOneWeaponLength = 0;
        int playerTwoWeaponLength = 0;
        if (playerOneSheet.getAttackWeapon() != null) {
            playerOneWeaponLength = playerOneSheet.getAttackWeapon().getLength();
        }
        if (playerTwoSheet.getAttackWeapon() != null) {
            playerTwoWeaponLength = playerTwoSheet.getAttackWeapon().getLength();
        }

        // Player one goes first if their weapon is longer...
        if (playerOneWeaponLength >= playerTwoWeaponLength) {
            // "Round" of combat... attacker attacks, then gets attacked by defender, assuming the defender isn't dead.
            LOG.info("Beginning round one of combat. {} is attacking {}.", playerOne.getCharacter(), playerTwo.getCharacter());
            resolveRound(playerOneSheet, playerTwoSheet);
            if (!playerTwo.getCharacter().isDead()) {
                LOG.info("Beginning round two of combat. {} is attacking {}.", playerTwo.getCharacter(), playerOne.getCharacter());
                resolveRound(playerTwoSheet, playerOneSheet);
            }
        } else {
            LOG.info("Beginning round one of combat. {} is attacking {}.", playerTwo.getCharacter(), playerOne.getCharacter());
            resolveRound(playerTwoSheet, playerOneSheet);
            if (!playerOne.getCharacter().isDead()) {
                LOG.info("Beginning round two of combat. {} is attacking {}.", playerOne.getCharacter(), playerTwo.getCharacter());
                resolveRound(playerOneSheet, playerTwoSheet);
            }
        }

        // Check for dead players and fatigue step.
        if (playerOne.getCharacter().isDead()) {
            LOG.info("{} died. Beginning transfer of items.", playerOne.getCharacter());
            resolveDeadPlayers(playerTwo, playerOne, boardModel);
        } else {
            LOG.info("{} is not dead. Beginning fatigue step calculations.", playerOne.getCharacter());
            int attackerAsterisk = playerOneSheet.getManeuverChit().getFatigueAsterisks() + playerOneSheet.getAttackChit().getFatigueAsterisks();
            if (attackerAsterisk >= 2) {
                LOG.info("{} played 2 or more fatigue asterisks this round and must fatigue a chit.", playerOne.getCharacter());
                playerOne.getCharacter().setFatigued(true);
            }
            if (playerOne.getCharacter().isWounded()) {
                LOG.info("{} has been wounded and must wound a chit.", playerOne.getCharacter());
            }
        }
        if (playerTwo.getCharacter().isDead()) {
            LOG.info("{} died. Beginning transfer of items.", playerTwo.getCharacter());
            resolveDeadPlayers(playerOne, playerTwo, boardModel);
        } else {
            LOG.info("{} is not dead. Beginning fatigue step calculations.", playerTwo.getCharacter());
            int defenderAsterisk = playerTwoSheet.getManeuverChit().getFatigueAsterisks() + playerTwoSheet.getAttackChit().getFatigueAsterisks();
            if (defenderAsterisk >= 2) {
                LOG.info("{} played 2 or more fatigue asterisks this round and must fatigue a chit.", playerTwo.getCharacter());
                playerTwo.getCharacter().setFatigued(true);
            }
            if (playerOne.getCharacter().isWounded()) {
                LOG.info("{} has been wounded and must wound a chit.", playerTwo.getCharacter());
            }
        }

        playerOneSheet.markFought();
        playerTwoSheet.markFought();
    }

    /**
     * Handle logic for when a player kills another.
     *
     * @param killer     the victor.
     * @param killed     the person who died.
     * @param boardModel the board.
     */
    private static void resolveDeadPlayers(final Player killer, final Player killed, final BoardModel boardModel) {
        LOG.info("Transferring items, gold, notoriety from {} to {}.", killer.getCharacter(), killed.getCharacter());
        killer.getCharacter().getItems().addAll(killed.getCharacter().getItems());
        killed.getCharacter().getItems().clear();
        killer.getCharacter().addGold(killed.getCharacter().getCurrentGold());
        killed.getCharacter().addGold(-killed.getCharacter().getCurrentGold());
        killer.getCharacter().addNotoriety(killed.getCharacter().getCurrentNotoriety());
        killed.getCharacter().addNotoriety(-killed.getCharacter().getCurrentNotoriety());

        LOG.info("AUTO REINCARNATION. Giving player a new start.");
        LOG.info("Creating a new character and Setting location to starting location (inn).");
        boardModel.getClearingForPlayer(killed).removeEntity(killed.getCharacter());
        killed.setCharacter(CharacterFactory.createCharacter(killed.getCharacter().getEntityInformation().convertToCharacterType()));
        killed.restartNewLife();
        boardModel.getStartingLocation().addEntity(killed.getCharacter());
    }

    /**
     * Cleanup after combat. This includes resetting the sheets and wound status.
     *
     * @param attacker   the attacker.
     * @param defender   the defender.
     */
    public static void cleanup(final Player attacker, final Player defender) {

        LOG.info("Resetting wounded status at the end of the day.");
        attacker.getCharacter().setWounded(false);
        attacker.getCharacter().setFatigued(false);
        defender.getCharacter().setWounded(false);
        defender.getCharacter().setFatigued(false);
    }

    /**
     * Resolve a round of combat between two entities. This assumes playerOne is attacking player two (who is defending).
     *
     * @param attacker the first melee sheet.
     * @param defender the second melee sheet.
     */
    private static void resolveRound(final MeleeSheet attacker, final MeleeSheet defender) {

        // Determine if attackers attack hits through defenders maneuver
        boolean attackHit = attacker.getAttackDirection().matches(defender.getManeuver());
        LOG.info("{} attacked with {}. Defender maneuvered with {}. Attack landed result: {}", attacker.getOwner(), attacker.getAttackDirection(), defender.getManeuver(), attackHit);
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
                LOG.info("{} had no weapon equipped. Using a dagger.", attacker.getOwner());
            }

            LOG.info("{} weapon strength: {}", attacker.getOwner(), weapon.getStrength());
            // Increase strength by the sharpness. If the weapon is striking, increase by one if the fight chit strength is greater
            // than the power of the weapon. For missile attacks, roll on the table.
            Harm attackStrength = increaseStrengthBySharpness(weapon.getStrength(), weapon.getSharpness());
            if (weapon.getAttackType() == AttackType.STRIKING) {
                if (attacker.getAttackChit().getStrength().greaterThan(attackStrength)) {
                    attackStrength = attackStrength.increase();
                    LOG.info("Attack chit stronger. Increased weapon strength to {}.", attackStrength);
                }
            } else {
                attackStrength = Table.MissileTable.roll(attacker.getPlayer(), attackStrength);
                LOG.info("Rolled on missile table. New strength: {}.", attackStrength);
            }

            // Check to see if the armor the defender may be wearing intercepts.
            boolean intercepted = false;
            final AbstractArmor armor = defender.getArmor();
            if (armor != null) {
                intercepted = armor.getProtectsAgainst().intercepts(attacker.getAttackDirection());
                LOG.info("{}'s armor protects against {}. Attack intercepted: {}", defender.getOwner(), armor.getProtectsAgainst(), intercepted);
            }
            if (intercepted) {
                LOG.info("Armor weight is {}.", armor.getWeight());
                attackStrength = attackStrength.decrease();
                LOG.info("Strength reduced by 1 for hitting armor. New value: {}", attackStrength);
                if (attackStrength == armor.getWeight()) {
                    if (armor.isDamaged()) {
                        // Armor that is already damaged is destroyed (removed from inventory).
                        defender.getOwner().getItems().remove(armor);
                        LOG.info("Armor was already damaged and has been destroyed! Removed from inventory of {}.", defender.getOwner());
                    } else {
                        armor.setDamaged(true);
                        LOG.info("Armor has been damaged by the hit.");
                    }
                } else if (attackStrength.greaterThan(armor.getWeight())) {
                    // Any attack greater than the armor weight instantly destroys it
                    defender.getOwner().getItems().remove(armor);
                    LOG.info("Armor was destroyed by an attack greater than the weight of the armor!. Removed from inventory of {}.", defender.getOwner());
                }
                // Only MEDIUM and higher makes us wound stuff.
                if (attackStrength.greaterThan(Harm.LIGHT)) {
                    LOG.info("Attack strength was greater than LIGHT. Player is wounded and must wound a chit.");
                    ((AbstractCharacter) defender.getOwner()).setWounded(true);
                } else {
                    LOG.info("Attack strength was LIGHT or less. No further duties required.");
                }

            } else {
                LOG.info("{} either wasn't wearing armor or the attack wasn't intercepted.", defender.getOwner());
                // Target is killed if the strength of the attack >= the players health.
                if (attackStrength.greaterThan(defender.getOwner().getVulnerability()) || attackStrength == defender.getOwner().getVulnerability()) {
                    ((AbstractCharacter) defender.getOwner()).setDead(true);
                    LOG.info("Attack strength was greater than or equal to {}'s vulnerability! {} has died!", defender.getOwner(), defender.getOwner());
                } else {
                    ((AbstractCharacter) defender.getOwner()).setWounded(true);
                    LOG.info("Player took a wound and must wound a chit.");
                }
            }

            // Weapons automatically get un-alerted.
            weapon.setAlert(false);
            LOG.info("Weapon un-alerted.");

        } else {
            LOG.info("Attacker missed. Ending round of combat.");
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
        Harm toReturn = strength;
        while (count < sharpness) {
            toReturn = toReturn.increase();
            count++;
        }
        LOG.info("Increased strength through sharpness. New strength: {}.", toReturn);
        return toReturn;
    }
}
