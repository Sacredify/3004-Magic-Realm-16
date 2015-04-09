package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.Path;
import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.monster.AbstractMonster;
import ca.carleton.magicrealm.entity.natives.AbstractNative;
import ca.carleton.magicrealm.game.DiceRoller;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.*;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.game.combat.chit.ActionType;
import ca.carleton.magicrealm.game.table.Table;
import ca.carleton.magicrealm.item.Item;
import ca.carleton.magicrealm.item.armor.AbstractArmor;
import ca.carleton.magicrealm.item.weapon.AbstractWeapon;
import ca.carleton.magicrealm.item.weapon.AttackType;
import ca.carleton.magicrealm.item.weapon.Dagger;
import ca.carleton.magicrealm.item.weapon.MonsterWeapon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

        try {
            LOG.info("Starting encounter step.");
            if (!doEncounterStep(boardModel, player, parent)) {
                return;
            }

            LOG.info("Starting attack options.");
            LOG.info("Showing target select.");
            final List<Object> potentialTargets = clearingOfCombat.getEntities().stream().filter(entity -> !entity.equals(player.getCharacter())).collect(Collectors.toList());
            potentialTargets.add("None");
            final Object target = JOptionPane.showInputDialog(parent, "Combat Step 1: Select a target (Cancel for NO target or click None - You don't want to fight):", "Combat",
                    JOptionPane.QUESTION_MESSAGE, null, potentialTargets.toArray(), potentialTargets.get(0));
            if (target instanceof Entity) {
                playerSheet.setTarget((Entity) target);
            }
            if (target == null || target.equals("None")) {
                LOG.info("Player opted to not fight. Stopping rest of melee sheet fill out.");
                JOptionPane.showMessageDialog(parent, "You must still out the melee sheet, as you may be targeted by other players.");
            }

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
            final List<ActionChit> fightChits = player.getCharacter().getActionChits().stream().filter(chit -> chit.getAction() == ActionType.FIGHT
                    && !chit.isFatigued() && !chit.isWounded()).collect(Collectors.toList());
            final ActionChit fightChit = (ActionChit) JOptionPane.showInputDialog(parent, "Combat Step 4: Select a fight chit to attack:", "Combat",
                    JOptionPane.QUESTION_MESSAGE, null, fightChits.toArray(), fightChits.get(0));
            playerSheet.setAttackChit(fightChit);
            if (fightChit != null) {
                numberOfAsterisksRemaining -= fightChit.getFatigueAsterisks();
            }

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
                            && !chit.isFatigued() && !chit.isWounded()).collect(Collectors.toList());
            List<Object> copyOfMoveChits = new ArrayList<Object>(moveChits);
            copyOfMoveChits.add("None");
            final Object moveChit = JOptionPane.showInputDialog(parent, "Combat Step 6: Select a move chit to dodge:", "Combat",
                    JOptionPane.QUESTION_MESSAGE, null, copyOfMoveChits.toArray(), copyOfMoveChits.get(0));
            if (moveChit instanceof ActionChit) {
                playerSheet.setManeuverChit((ActionChit) moveChit);
            } else {
                LOG.warn("Warning: Maneuver chit set to null!");
            }

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

        } catch (final NullPointerException exception) {
            LOG.error("User didn't enter a required value in combat. This won't necessary fail, continuing execution.", exception);
        }

    }

    /**
     * Logic for the encounter step.
     *
     * @param boardModel the board.
     * @param player     the player.
     * @param parent     the parent frame.
     * @return whether or not to continue to combat.
     */
    private static boolean doEncounterStep(final BoardModel boardModel, final Player player, final Component parent) {

        final MeleeSheet playerSheet = boardModel.getMeleeSheet(player);

        final EncounterStepOption choice = (EncounterStepOption) JOptionPane.showInputDialog(parent, "Encounter Step: Select an activity.", "Combat",
                JOptionPane.QUESTION_MESSAGE, null, EncounterStepOption.values(), EncounterStepOption.values()[0]);

        if (choice == EncounterStepOption.RUN_AWAY) {
            LOG.info("Chose to run away.");

            final List<ActionChit> moveChits = player.getCharacter().getActionChits().stream()
                    .filter(chit -> chit.getAction() == ActionType.MOVE)
                    .collect(Collectors.toList());

            // If they have no available move chits.
            if (moveChits.isEmpty()) {
                LOG.info("Player has no move chits to play.");
                JOptionPane.showMessageDialog(parent, "You have no available move chits! Sorry!", "Combat", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                final ActionChit moveChit = (ActionChit) JOptionPane.showInputDialog(parent, "Encounter Step: Select a move chit to run away with.", "Combat",
                        JOptionPane.QUESTION_MESSAGE, null, moveChits.toArray(), moveChits.get(0));
                if (moveChit == null) {
                    LOG.info("Player selected nothing...");
                    return true;
                } else {
                    LOG.info("Player is running away! Setting their status to running away and ending combat.");
                    playerSheet.setRunningAway(true);
                    return false;
                }
            }
        } else if (choice == EncounterStepOption.ALERT_WEAPON) {
            final List<ActionChit> fightChits = player.getCharacter().getActionChits().stream()
                    .filter(chit -> chit.getAction() == ActionType.FIGHT)
                    .collect(Collectors.toList());

            // If they have no available move chits.
            if (fightChits.isEmpty()) {
                LOG.info("Player has no fight chits to play.");
                JOptionPane.showMessageDialog(parent, "You have no available fight chits! Sorry!", "Combat", JOptionPane.INFORMATION_MESSAGE);
            } else {
                final ActionChit fightChit = (ActionChit) JOptionPane.showInputDialog(parent, "Encounter Step: Select a fight chit to alert a weapon with.", "Combat",
                        JOptionPane.QUESTION_MESSAGE, null, fightChits.toArray(), fightChits.get(0));
                if (fightChit != null) {
                    final List<Item> weapons = player.getCharacter().getItems().stream().filter(item -> item instanceof AbstractWeapon).collect(Collectors.toList());
                    // Handle the possibility they don't have any weapons.
                    final List<Object> weaponsWithNone = new ArrayList<Object>(weapons);
                    weaponsWithNone.add("None (dagger)");
                    final Object weapon = JOptionPane.showInputDialog(parent, "Combat Step 3: Select a weapon:", "Combat",
                            JOptionPane.QUESTION_MESSAGE, null, weaponsWithNone.toArray(), weaponsWithNone.get(0));
                    // Set the weapon if they actually chose a weapon.
                    if (!(weapon instanceof String)) {
                        ((AbstractWeapon) weapon).setAlert(true);
                        LOG.info("Alerted {} successfully.", weapon);
                    } else {
                        LOG.info("Either player selected nothing or chose not to. Alerting nothing.");
                    }
                }
            }
            return true;
        } else {
            LOG.info("Player decided to do nothing for their encounter step... continuing combat.");
            return true;
        }
    }

    /**
     * Process combat on the server side.
     *
     * @param players    the players.
     * @param boardModel the board.
     * @return the number of combats done that day. Honestly, this is just for testing/debugging.
     */
    public static int process(final List<Player> players, final BoardModel boardModel) {

        LOG.info("Starting simplified encounter step - assigning monsters to players in the same clearing.");
        final List<Entity> entitiesFighting = CombatUtils.getMonstersFightingToday(boardModel, players.stream().map(Player::getCharacter).collect(Collectors.toList()));

        //array because lambdas don't like non final variables.
        final int[] combatsDone = new int[1];

        try {
            for (final Player player : players) {
                LOG.info("Starting combat resolution for {}.", player.getCharacter());
                final MeleeSheet playerSheet = boardModel.getMeleeSheet(player);
                final Entity target = playerSheet.getTarget();
                if (target == null) {
                    LOG.info("Player opted to not fight. Skipping their combat.");

                    if (playerSheet.isRunningAway()) {
                        LOG.info("Player is running away today.");
                        final List<Path> potentialExits = boardModel.getClearingForPlayer(player).getAdjacentPaths();
                        final Clearing moveTarget = potentialExits.get(DiceRoller.getInstance().nextInt(potentialExits.size())).getToClearing();
                        LOG.info("Moving {} to {}.", player.getCharacter(), moveTarget);
                        boardModel.getClearingForPlayer(player).getEntities().remove(player.getCharacter());
                        moveTarget.addEntity(player.getCharacter());
                        LOG.info("Player moved successfully. Starting combat of next player.");
                        continue;
                    }
                } else {

                    final MeleeSheet targetSheet = boardModel.getMeleeSheet(target);

                    if (playerSheet.hasFought(target) || targetSheet.hasFought(playerSheet.getOwner())) {
                        LOG.info("Assumption that entities can't fight the same thing twice. - Skipping fight of {} and {}.", playerSheet.getOwner(), targetSheet.getOwner());
                        continue;
                    }

                    if (target instanceof AbstractCharacter) {
                        // Check they are in same clearing.
                        if (!boardModel.getClearingForPlayer(player).equals(boardModel.getClearingForCharacter((AbstractCharacter) target))) {
                            LOG.info("{} and {} are not in the same clearing anymore! Combat skipped. [May have run away]", player.getCharacter(), target);
                        } else {
                            // Combat between two characters
                            final Player otherPlayer = boardModel.getPlayerForCharacter((AbstractCharacter) target);
                            Combat.doCombat(boardModel, player, otherPlayer);
                            combatsDone[0]++;
                        }
                    } else if (target instanceof Denizen) {

                        // Check they are in same clearing.
                        if (!boardModel.getClearingForPlayer(player).equals(((Denizen) target).getCurrentClearing())) {
                            LOG.info("{} and {} are not in the same clearing anymore! Combat skipped. [May have run away]", player.getCharacter(), target);
                        } else {
                            // Combat between a character and a native or monster.
                            if (target instanceof AbstractNative) {
                                LOG.info("Because {} targeted {}, a native, checking to see if any other native of that group will help him...", player.getCharacter(), target);

                                final List<Entity> nativesHelping = boardModel.getClearingForPlayer(player).getEntities().stream()
                                        .filter(entity -> entity instanceof AbstractNative
                                                && ((AbstractNative) entity).getFaction() == ((AbstractNative) target).getFaction()
                                                && !entity.equals(target))
                                        .collect(Collectors.toList());

                                nativesHelping.forEach(entity ->
                                {
                                    final MeleeSheet sheet = boardModel.getMeleeSheet(entity);
                                    sheet.setTarget(player.getCharacter());
                                    LOG.info("{} is now targeting {}!", entity, player.getCharacter());
                                });

                                // Assumption, we'll just add to the list of fighting entities to be resolved at will by the game.
                                entitiesFighting.addAll(nativesHelping);
                            }

                            Combat.doCombat(boardModel, player, (Denizen) target);
                            combatsDone[0]++;
                        }
                    }
                }
                LOG.info("Now resolving combat for any monster currently in the clearing with {}.", player.getCharacter());
                LOG.info("Shuffling for a sense of randomness...");
                Collections.shuffle(entitiesFighting);
                // This should be done in order of attack times, but honestly, who really cares. More work for basically the same effect.
                entitiesFighting.stream()
                        .filter(monster -> ((Denizen) monster).getCurrentClearing().equals(boardModel.getClearingForPlayer(player)))
                        .forEach(monster -> {
                            LOG.info("{} is trying to fight!.", monster);
                            final MeleeSheet monsterSheet = boardModel.getMeleeSheet(monster);
                            // Monsters only attack players.
                            final Player playerTarget = boardModel.getPlayerForCharacter((AbstractCharacter) monsterSheet.getTarget());

                            if (!monsterSheet.hasFought(playerTarget.getCharacter()) && player.equals(playerTarget)) {
                                if (!monsterSheet.getTarget().isDead()) {
                                    Combat.doCombat(boardModel, playerTarget, (Denizen) monster);
                                    combatsDone[0]++;
                                } else {
                                    LOG.info("Skipping combat because {} died in a previous combat.", monsterSheet.getTarget());
                                }
                            } else {
                                if (player.equals(playerTarget)) {
                                    LOG.info("Assumption that entities can't fight the same thing twice. - Skipping fight of {} and {}.", monster, playerTarget.getCharacter());
                                } else {
                                    LOG.info("Skipping {} because they weren't assigned to the player currently being resolved.", monster);
                                }
                            }
                        });

            }
        } catch (final NullPointerException exception) {
            LOG.error("Error with combat resolution. Something may have not been set. Instead of failing, we'll continue and hope it works.", exception);
        }

        // After combat is done
        LOG.info("All combats complete. Resetting melee sheets.");
        boardModel.getAllSheets().stream().forEach(MeleeSheet::resetSheet);
        LOG.info("Done combat for the day. Combats resolved (not rounds - unique entities targeting another unique): {}.", combatsDone);
        return combatsDone[0];
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
            resolvePlayerOnPlayerRound(playerOneSheet, playerTwoSheet);
            if (!playerTwo.getCharacter().isDead()) {
                LOG.info("Beginning round two of combat. {} is attacking {}.", playerTwo.getCharacter(), playerOne.getCharacter());
                resolvePlayerOnPlayerRound(playerTwoSheet, playerOneSheet);
            }
        } else {
            LOG.info("Beginning round one of combat. {} is attacking {}.", playerTwo.getCharacter(), playerOne.getCharacter());
            resolvePlayerOnPlayerRound(playerTwoSheet, playerOneSheet);
            if (!playerOne.getCharacter().isDead()) {
                LOG.info("Beginning round two of combat. {} is attacking {}.", playerOne.getCharacter(), playerTwo.getCharacter());
                resolvePlayerOnPlayerRound(playerOneSheet, playerTwoSheet);
            }
        }

        // Check for dead players and fatigue step.
        if (playerOne.getCharacter().isDead()) {
            LOG.info("{} died. Beginning transfer of items.", playerOne.getCharacter());
            CombatUtils.resolvePlayerKilledAnother(playerTwo, playerOne, boardModel);
        } else {
            CombatUtils.checkFatigueAndWounds(boardModel, playerOne);
        }
        if (playerTwo.getCharacter().isDead()) {
            LOG.info("{} died. Beginning transfer of items.", playerTwo.getCharacter());
            CombatUtils.resolvePlayerKilledAnother(playerOne, playerTwo, boardModel);
        } else {
            CombatUtils.checkFatigueAndWounds(boardModel, playerTwo);
        }

        playerOneSheet.markAlreadyFought(playerTwo.getCharacter());
        playerTwoSheet.markAlreadyFought(playerOne.getCharacter());
    }

    /**
     * Initiate combat between a player and denizen.
     *
     * @param boardModel the board.
     * @param player     the first player.
     * @param denizen    the second player.
     */
    public static void doCombat(final BoardModel boardModel, final Player player, final Denizen denizen) {

        final MeleeSheet playerSheet = boardModel.getMeleeSheet(player);
        final MeleeSheet denizenSheet = boardModel.getMeleeSheet(denizen);

        // Determine who would go first, based on their weapon length.
        int playerOneWeaponLength = 0;
        int denizenWeaponLength = 0;
        if (playerSheet.getAttackWeapon() != null) {
            playerOneWeaponLength = playerSheet.getAttackWeapon().getLength();
        }
        if (denizenSheet.getAttackWeapon() != null) {
            denizenWeaponLength = denizenSheet.getAttackWeapon().getLength();
        }

        LOG.info("{} is using {}. Weapon length: {}. Alert? {}", playerSheet.getOwner(), playerSheet.getAttackWeapon(), playerSheet.getAttackWeapon().getLength(), playerSheet.getAttackWeapon().isAlert());
        LOG.info("{} is using {}. Weapon length: {}.", denizenSheet.getOwner(), denizenSheet.getAttackWeapon(), denizenSheet.getAttackWeapon().getLength());

        // Player one goes first if their weapon is longer...
        if (playerOneWeaponLength >= denizenWeaponLength) {
            // "Round" of combat... attacker attacks, then gets attacked by defender, assuming the defender isn't dead.
            LOG.info("Beginning round one of combat. {} is attacking {}.", player.getCharacter(), denizen);
            resolvePlayerOnDenizenRound(playerSheet, denizenSheet);
            if (!denizen.isDead()) {
                LOG.info("Beginning round two of combat. {} is attacking {}.", denizen, player.getCharacter());
                resolveDenizenOnPlayerRound(denizenSheet, playerSheet);
            }
        } else {
            LOG.info("Beginning round one of combat. {} is attacking {}.", denizen, player.getCharacter());
            resolveDenizenOnPlayerRound(denizenSheet, playerSheet);
            if (!player.getCharacter().isDead()) {
                LOG.info("Beginning round two of combat. {} is attacking {}.", player.getCharacter(), denizen);
                resolvePlayerOnDenizenRound(playerSheet, denizenSheet);
            }
        }

        // Check for a dead player/monster and fatigue step.
        if (player.getCharacter().isDead()) {
            LOG.info("{} died while fighting a denizen! They will be revived automatically...", player.getCharacter());
            CombatUtils.resolveDeadPlayer(boardModel, player);
        } else {
            CombatUtils.checkFatigueAndWounds(boardModel, player);
        }

        if (denizen.isDead()) {
            LOG.info("{} died to the player! The player reaps the rewards and gains his bounty.", denizen);
            denizen.addBountyToPlayer(player);
            LOG.info("Removing denizen from the board...");
            CombatUtils.resolveDeadDenizen(boardModel, denizen);
        } else {
            LOG.info("{} is not dead. Doing nothing more.", denizen);
        }

        playerSheet.markAlreadyFought(denizen);
        denizenSheet.markAlreadyFought(player.getCharacter());
    }

    /**
     * Fatigue or wound chits for a player after combat.
     *
     * @param board  the board.
     * @param player the player.
     * @param parent parent component.
     */
    public static void doFatigueStep(final BoardModel board, final Player player, final Component parent) {

        LOG.info("Starting fatigue a chit step.");
        if (player.getCharacter().isFatigued()) {
            LOG.info("{} is fatigued and must fatigue a chit.", player.getCharacter());
            List<ActionChit> actionChits = player.getCharacter().getActionChits().stream().filter(chit -> chit.getFatigueAsterisks() >= 1).collect(Collectors.toList());
            final ActionChit fatigueChit = (ActionChit) JOptionPane.showInputDialog(parent, "You were fatigued by combat and must fatigue a chit!", "Combat",
                    JOptionPane.QUESTION_MESSAGE, null, actionChits.toArray(), actionChits.get(0));

            fatigueChit.setFatigued(true);
            if (fatigueChit.getFatigueAsterisks() > 1) {
                LOG.info("Because a fight chit with more than one asterisk was chosen, user may un-fatigue a one asterisk one if possible.");
                final List<ActionChit> fatiguedOneAsterisksChits = player.getCharacter().getActionChits().stream().filter(chit ->
                        chit.isFatigued() && chit.getFatigueAsterisks() == 1).collect(Collectors.toList());
                if (!fatiguedOneAsterisksChits.isEmpty()) {
                    LOG.info("Found more than one fatigued 1 asterisk chits... user may un-fatigue one.");
                    final ActionChit unFatigueChit = (ActionChit) JOptionPane.showInputDialog(parent, "Because you fatigued a 2 asterisk chit, you may un-fatigue one.", "Combat",
                            JOptionPane.QUESTION_MESSAGE, null, fatiguedOneAsterisksChits.toArray(), fatiguedOneAsterisksChits.get(0));
                    unFatigueChit.setFatigued(false);
                }
            }
            LOG.info("Successfully fatigued {}'s {}.", player.getCharacter(), fatigueChit);
        }

        LOG.info("Starting wound a chit step.");
        if (player.getCharacter().isWounded()) {
            LOG.info("{} is wounded and must wound a chit.", player.getCharacter());
            List<ActionChit> actionChits = player.getCharacter().getActionChits().stream().filter(chit -> !chit.isFatigued() && !chit.isWounded()).collect(Collectors.toList());

            if (actionChits.isEmpty()) {
                LOG.info("Found no available chits to wound... Trying again with fatigued chits.");
                actionChits = player.getCharacter().getActionChits().stream().filter(chit -> !chit.isWounded()).collect(Collectors.toList());
                if (actionChits.isEmpty()) {
                    LOG.info("No chits to wound... {} has died of his wounds.", player.getCharacter());
                    player.getCharacter().setDead(true);
                }
            }
            if (!player.getCharacter().isDead()) {
                final ActionChit woundedChit = (ActionChit) JOptionPane.showInputDialog(parent, "You were wounded in combat and must wound a chit!", "Combat",
                        JOptionPane.QUESTION_MESSAGE, null, actionChits.toArray(), actionChits.get(0));
                woundedChit.setWounded(true);

                LOG.info("Successfully wounded {}'s {}.", player.getCharacter(), woundedChit);
                if (player.getCharacter().getActionChits().stream().filter(chit -> !chit.isWounded()).count() == 0) {
                    LOG.info("After wounding a chit, player no longer has any un-wounded chits and has died.");
                    player.getCharacter().setDead(true);
                }
            }
        }

        LOG.info("Done fatigue step.");

        if (player.getCharacter().isDead()) {
            LOG.info("AUTO REINCARNATION. Giving player a new start.");
            LOG.info("Creating a new character and Setting location to starting location (inn).");
            board.getClearingForPlayer(player).removeEntity(player.getCharacter());
            player.setCharacter(CharacterFactory.createCharacter(player.getCharacter().getEntityInformation().convertToCharacterType()));
            player.restartNewLife();
            board.getStartingLocation().addEntity(player.getCharacter());
        }
    }

    /**
     * Cleanup after combat. This includes resetting the sheets and wound status.
     *
     * @param player the player.
     */
    public static void cleanup(final Player player) {
        player.getCharacter().setWounded(false);
        player.getCharacter().setFatigued(false);
        player.getCharacter().getItems().stream().filter(item -> item instanceof AbstractWeapon)
                .forEach(weapon -> ((AbstractWeapon) weapon).setAlert(false));
        LOG.info("Cleaned up {} after combat. Weapons un-alerted, wounds and fatigue reset.", player.getCharacter());
    }

    /**
     * Resolve a round of combat where a player attacks a denizen.
     *
     * @param player  the player [attacker].
     * @param denizen the denizen [defender].
     */
    private static void resolvePlayerOnDenizenRound(final MeleeSheet player, final MeleeSheet denizen) {
        // Determine if attackers attack hits through defenders maneuver
        boolean attackHit = player.getAttackDirection().matches(denizen.getManeuver());
        LOG.info("{} attacked with {}. Denizen maneuvered with {}. Attack landed result: {}", player.getOwner(), player.getAttackDirection(), denizen.getManeuver(), attackHit);
        // The attack can still hit even if they don't match if the attack time is faster than the maneuver time.
        if (!attackHit) {
            LOG.info("Attack did not land. Checking weapon times for modification...");

            if (denizen.getManeuverChit() == null) {
                LOG.warn("Maneuver chit was null, hit always lands.");
                attackHit = true;
            } else {
                attackHit = player.getAttackChit().getTime() < denizen.getManeuverChit().getTime();
                LOG.info("Attack landed after checking times: {}", attackHit);
            }
        }
        if (attackHit) {
            // Determine the harm (strength) of the attack
            AbstractWeapon weapon = player.getAttackWeapon();

            // No weapon (using only a fight chit) means they use a dagger.
            if (weapon == null) {
                weapon = new Dagger();
                LOG.info("{} had no weapon equipped. Using a dagger.", player.getOwner());
            }

            LOG.info("{} weapon strength: {}", player.getOwner(), weapon.getStrength());
            // Increase strength by the sharpness. If the weapon is striking, increase by one if the fight chit strength is greater
            // than the power of the weapon. For missile attacks, roll on the table.
            Harm attackStrength = CombatUtils.increaseStrengthBySharpness(weapon.getStrength(), weapon.getSharpness());
            if (weapon.getAttackType() == AttackType.STRIKING) {
                if (player.getAttackChit().getStrength().greaterThan(attackStrength)) {
                    attackStrength = attackStrength.increase();
                    LOG.info("Attack chit stronger. Increased weapon strength to {}.", attackStrength);
                }
            } else {
                attackStrength = Table.MissileTable.roll(player.getPlayer(), attackStrength);
                LOG.info("Rolled on missile table. New strength: {}.", attackStrength);
            }

            // Check to see if the armor the defender may be wearing intercepts.
            final boolean armored = ((Denizen) denizen.getOwner()).isArmored();

            if (armored) {
                attackStrength = attackStrength.decrease();
                LOG.info("The denizen was armored. Reduced strength of attack to {}.", attackStrength);
            }

            // Target is killed if the strength of the attack >= the players health.
            if (attackStrength.greaterThan(denizen.getOwner().getVulnerability()) || attackStrength == denizen.getOwner().getVulnerability()) {
                denizen.getOwner().setDead(true);
                LOG.info("Attack strength was greater than or equal to {}'s vulnerability! {} has died!", denizen.getOwner(), denizen.getOwner());
            } else {
                LOG.info("The attack was not enough to hurt the denizen.");
            }

        } else {
            LOG.info("Attacker missed. Ending round of combat.");
        }
    }

    /**
     * Resolve a round of combat where a denizen attacks a player.
     *
     * @param denizen the denizen [attacker].
     * @param player  the player [defender].
     */
    private static void resolveDenizenOnPlayerRound(final MeleeSheet denizen, final MeleeSheet player) {
        // Determine if attackers attack hits through defenders maneuver
        boolean attackHit = denizen.getAttackDirection().matches(player.getManeuver());
        LOG.info("{} attacked with {}. Player maneuvered with {}. Attack landed result: {}", denizen.getOwner(), denizen.getAttackDirection(), player.getManeuver(), attackHit);
        // The attack can still hit even if they don't match if the attack time is faster than the maneuver time.
        if (!attackHit) {
            LOG.info("Attack did not land. Checking weapon times for modification...");
            if (player.getManeuverChit() == null) {
                LOG.warn("Maneuver chit was null, hit always lands.");
                attackHit = true;
            } else {
                attackHit = denizen.getAttackChit().getTime() < player.getManeuverChit().getTime();
                LOG.info("Attack landed after checking times: {}", attackHit);
            }
        }
        if (attackHit) {

            // Determine the harm (strength) of the attack
            AbstractWeapon weapon = denizen.getAttackWeapon();

            // No weapon for a denizen means they must be a monster (using their claws).
            if (weapon == null && denizen.getOwner() instanceof AbstractMonster) {
                weapon = new MonsterWeapon(denizen.getOwner().getVulnerability());
                LOG.info("{} is using their claw (monster weapon) to attack!");
            }

            LOG.info("{} weapon strength: {}", denizen.getOwner(), weapon.getStrength());
            // Increase strength by the sharpness. If the weapon is striking, increase by one if the fight chit strength is greater
            // than the power of the weapon. For missile attacks, roll on the table.
            Harm attackStrength = CombatUtils.increaseStrengthBySharpness(weapon.getStrength(), weapon.getSharpness());
            if (weapon.getAttackType() == AttackType.STRIKING) {
                if (denizen.getAttackChit().getStrength().greaterThan(attackStrength)) {
                    attackStrength = attackStrength.increase();
                    LOG.info("Attack chit stronger. Increased weapon strength to {}.", attackStrength);
                }
            } else {
                attackStrength = Table.MissileTable.roll(denizen.getPlayer(), attackStrength);
                LOG.info("Rolled on missile table. New strength: {}.", attackStrength);
            }

            // Check to see if the armor the defender may be wearing intercepts.
            boolean intercepted = false;
            final AbstractArmor armor = player.getArmor();
            if (armor != null) {
                intercepted = armor.getProtectsAgainst().intercepts(denizen.getAttackDirection());
                LOG.info("{}'s armor protects against {}. Attack intercepted: {}", player.getOwner(), armor.getProtectsAgainst(), intercepted);
            }
            if (intercepted) {
                LOG.info("Armor weight is {}.", armor.getWeight());
                attackStrength = attackStrength.decrease();
                LOG.info("Strength reduced by 1 for hitting armor. New value: {}", attackStrength);
                if (attackStrength == armor.getWeight()) {
                    if (armor.isDamaged()) {
                        // Armor that is already damaged is destroyed (removed from inventory).
                        player.getOwner().getItems().remove(armor);
                        LOG.info("Armor was already damaged and has been destroyed! Removed from inventory of {}.", player.getOwner());
                    } else {
                        armor.setDamaged(true);
                        LOG.info("Armor has been damaged by the hit.");
                    }
                } else if (attackStrength.greaterThan(armor.getWeight())) {
                    // Any attack greater than the armor weight instantly destroys it
                    player.getOwner().getItems().remove(armor);
                    LOG.info("Armor was destroyed by an attack greater than the weight of the armor!. Removed from inventory of {}.", player.getOwner());
                }
                // Only MEDIUM and higher makes us wound stuff.
                if (attackStrength.greaterThan(Harm.LIGHT)) {
                    LOG.info("Attack strength was greater than LIGHT. Player is wounded and must wound a chit.");
                    ((AbstractCharacter) player.getOwner()).setWounded(true);
                } else {
                    LOG.info("Attack strength was LIGHT or less. No further duties required.");
                }

            } else {
                LOG.info("{} either wasn't wearing armor or the attack wasn't intercepted.", player.getOwner());
                // Target is killed if the strength of the attack >= the players health.
                if (attackStrength.greaterThan(player.getOwner().getVulnerability()) || attackStrength == player.getOwner().getVulnerability()) {
                    player.getOwner().setDead(true);
                    LOG.info("Attack strength was greater than or equal to {}'s vulnerability! {} has died!", player.getOwner(), player.getOwner());
                } else {
                    ((AbstractCharacter) player.getOwner()).setWounded(true);
                    LOG.info("Player took a wound and must wound a chit.");
                }
            }

        } else {
            LOG.info("Attacker missed. Ending round of combat.");
        }
    }

    /**
     * Resolve a round of combat between two players. This assumes playerOne is attacking player two (who is defending).
     *
     * @param attacker the first melee sheet.
     * @param defender the second melee sheet.
     */
    private static void resolvePlayerOnPlayerRound(final MeleeSheet attacker, final MeleeSheet defender) {

        // Determine if attackers attack hits through defenders maneuver
        boolean attackHit = attacker.getAttackDirection().matches(defender.getManeuver());
        LOG.info("{} attacked with {}. Defender maneuvered with {}. Attack landed result: {}", attacker.getOwner(), attacker.getAttackDirection(), defender.getManeuver(), attackHit);
        // The attack can still hit even if they don't match if the attack time is faster than the maneuver time.
        if (!attackHit) {
            LOG.info("Attack did not land. Checking weapon times for modification...");
            if (defender.getManeuverChit() == null) {
                LOG.warn("Maneuver chit was null, hit always lands.");
                attackHit = true;
            } else {
                attackHit = attacker.getAttackChit().getTime() < defender.getManeuverChit().getTime();
                LOG.info("Attack landed after checking times: {}", attackHit);
            }
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
            Harm attackStrength = CombatUtils.increaseStrengthBySharpness(weapon.getStrength(), weapon.getSharpness());
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
                    defender.getOwner().setDead(true);
                    LOG.info("Attack strength was greater than or equal to {}'s vulnerability! {} has died!", defender.getOwner(), defender.getOwner());
                } else {
                    ((AbstractCharacter) defender.getOwner()).setWounded(true);
                    LOG.info("Player took a wound and must wound a chit.");
                }
            }
        } else {
            LOG.info("Attacker missed. Ending round of combat.");
        }
    }

}
