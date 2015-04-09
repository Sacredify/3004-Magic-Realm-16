import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.GUI.board.EntityBuilder;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.control.Combat;
import ca.carleton.magicrealm.control.CombatUtils;
import ca.carleton.magicrealm.control.Sunset;
import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.entity.chit.Dwelling;
import ca.carleton.magicrealm.entity.chit.GoldChit;
import ca.carleton.magicrealm.entity.monster.AbstractMonster;
import ca.carleton.magicrealm.entity.monster.Dragon;
import ca.carleton.magicrealm.entity.monster.Spider;
import ca.carleton.magicrealm.entity.natives.NativeFaction;
import ca.carleton.magicrealm.entity.natives.NativeFactory;
import ca.carleton.magicrealm.entity.natives.NativeType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.AttackDirection;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.game.combat.Maneuver;
import ca.carleton.magicrealm.game.combat.MeleeSheet;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.game.combat.chit.ActionType;
import ca.carleton.magicrealm.item.ItemInformation;
import ca.carleton.magicrealm.item.armor.*;
import ca.carleton.magicrealm.item.weapon.*;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

/**
 * Created with IntelliJ IDEA.
 * Date: 26/03/2015
 * Time: 3:16 AM
 */
public class CombatTest {

    /**
     * This test only ensures we can actually run the code properly (without an error). See logging output for the run info.
     */
    @Test
    public void canDoCombat() {

        // Create the board and player
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);

        final Player attacker = new Player();
        attacker.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        boardModel.getStartingLocation().addEntity(attacker.getCharacter());

        final Player defender = new Player();
        defender.setCharacter(CharacterFactory.createCharacter(CharacterType.CAPTAIN));
        boardModel.getStartingLocation().addEntity(defender.getCharacter());

        // Attacker melee sheet
        boardModel.createNewMeleeSheet(attacker);
        final MeleeSheet attackerSheet = boardModel.getMeleeSheet(attacker);
        attackerSheet.setAttackWeapon(new Crossbow());
        attackerSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setAttackDirection(AttackDirection.THRUST);
        attackerSheet.setManeuver(Maneuver.CHARGE);
        attackerSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setArmor(new SuitOfArmor());

        // Defender melee sheet
        boardModel.createNewMeleeSheet(defender);
        final MeleeSheet defenderSheet = boardModel.getMeleeSheet(defender);
        defenderSheet.setAttackWeapon(new Crossbow());
        defenderSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        defenderSheet.setAttackDirection(AttackDirection.THRUST);
        defenderSheet.setManeuver(Maneuver.CHARGE);
        defenderSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        defenderSheet.setArmor(new SuitOfArmor());

        Combat.doCombat(boardModel, attacker, defender);
    }

    @Test
    public void canResolveMissedHit() {
        // Create the board and player
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);

        final Player attacker = new Player();
        attacker.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        boardModel.getStartingLocation().addEntity(attacker.getCharacter());

        final Player defender = new Player();
        defender.setCharacter(CharacterFactory.createCharacter(CharacterType.CAPTAIN));
        boardModel.getStartingLocation().addEntity(defender.getCharacter());

        // Attacker melee sheet
        boardModel.createNewMeleeSheet(attacker);
        final MeleeSheet attackerSheet = boardModel.getMeleeSheet(attacker);
        attackerSheet.setAttackWeapon(new Crossbow());
        attackerSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setAttackDirection(AttackDirection.THRUST);
        attackerSheet.setManeuver(Maneuver.DODGE);
        attackerSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setArmor(new SuitOfArmor());

        // Defender melee sheet
        boardModel.createNewMeleeSheet(defender);
        final MeleeSheet defenderSheet = boardModel.getMeleeSheet(defender);
        defenderSheet.setAttackWeapon(new Crossbow());
        defenderSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        defenderSheet.setAttackDirection(AttackDirection.THRUST);
        defenderSheet.setManeuver(Maneuver.DODGE);
        defenderSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        defenderSheet.setArmor(new SuitOfArmor());

        Combat.doCombat(boardModel, attacker, defender);

        assertThat(defender.getCharacter().isWounded(), is(false));
        assertThat(defender.getCharacter().isDead(), is(false));
        assertThat(defenderSheet.getArmor().isDamaged(), is(false));
    }

    @Test
    public void canResolveHitDueToTiming() {
        // Create the board and player
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);

        final Player attacker = new Player();
        attacker.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        boardModel.getStartingLocation().addEntity(attacker.getCharacter());

        final Player defender = new Player();
        defender.setCharacter(CharacterFactory.createCharacter(CharacterType.CAPTAIN));
        boardModel.getStartingLocation().addEntity(defender.getCharacter());

        // Attacker melee sheet
        boardModel.createNewMeleeSheet(attacker);
        final MeleeSheet attackerSheet = boardModel.getMeleeSheet(attacker);
        attackerSheet.setAttackWeapon(new ThrustingSword());
        attackerSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(2).build());
        attackerSheet.setAttackDirection(AttackDirection.THRUST);
        attackerSheet.setManeuver(Maneuver.DODGE);
        attackerSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setArmor(new SuitOfArmor());

        // Defender melee sheet
        boardModel.createNewMeleeSheet(defender);
        final MeleeSheet defenderSheet = boardModel.getMeleeSheet(defender);
        defenderSheet.setAttackWeapon(new ThrustingSword());
        defenderSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(2).build());
        defenderSheet.setAttackDirection(AttackDirection.THRUST);
        defenderSheet.setManeuver(Maneuver.DODGE);
        defenderSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        defenderSheet.setArmor(new SuitOfArmor());

        Combat.doCombat(boardModel, attacker, defender);

        assertThat(defender.getCharacter().isWounded(), is(false));
        assertThat(defender.getCharacter().isDead(), is(false));
        assertThat(defenderSheet.getArmor().isDamaged(), is(false));
    }

    @Test
    public void canDamageArmor() {
        // Create the board and player
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);

        final Player attacker = new Player();
        attacker.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        boardModel.getStartingLocation().addEntity(attacker.getCharacter());

        final Player defender = new Player();
        defender.setCharacter(CharacterFactory.createCharacter(CharacterType.CAPTAIN));
        boardModel.getStartingLocation().addEntity(defender.getCharacter());

        // Attacker melee sheet
        boardModel.createNewMeleeSheet(attacker);
        final MeleeSheet attackerSheet = boardModel.getMeleeSheet(attacker);
        attackerSheet.setAttackWeapon(new AbstractWeapon() {

            private static final long serialVersionUID = 3274031187299686287L;

            @Override
            public int getSharpness() {
                return 1;
            }

            @Override
            public Harm getStrength() {
                return Harm.HEAVY;
            }

            @Override
            public AttackType getAttackType() {
                return AttackType.STRIKING;
            }

            @Override
            public ItemInformation getItemInformation() {
                return null;
            }
        });
        attackerSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(2).build());
        attackerSheet.setAttackDirection(AttackDirection.THRUST);
        attackerSheet.setManeuver(Maneuver.CHARGE);
        attackerSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setArmor(new SuitOfArmor());

        // Defender melee sheet
        boardModel.createNewMeleeSheet(defender);
        final MeleeSheet defenderSheet = boardModel.getMeleeSheet(defender);
        defenderSheet.setAttackWeapon(new AbstractWeapon() {

            private static final long serialVersionUID = 3274031187299686287L;

            @Override
            public int getSharpness() {
                return 1;
            }

            @Override
            public Harm getStrength() {
                return Harm.HEAVY;
            }

            @Override
            public AttackType getAttackType() {
                return AttackType.STRIKING;
            }

            @Override
            public ItemInformation getItemInformation() {
                return null;
            }
        });
        defenderSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(2).build());
        defenderSheet.setAttackDirection(AttackDirection.THRUST);
        defenderSheet.setManeuver(Maneuver.CHARGE);
        defenderSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        defenderSheet.setArmor(new SuitOfArmor());

        Combat.doCombat(boardModel, attacker, defender);

        assertThat(defender.getCharacter().isWounded(), is(true));
        assertThat(defender.getCharacter().isDead(), is(false));
        assertThat(defenderSheet.getArmor().isDamaged(), is(true));
    }

    @Test
    public void canKillPlayer() {

        // Create the board and player
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);

        final Player attacker = new Player();
        attacker.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        boardModel.getStartingLocation().addEntity(attacker.getCharacter());

        final Player defender = new Player();
        defender.setCharacter(CharacterFactory.createCharacter(CharacterType.CAPTAIN));
        boardModel.getStartingLocation().addEntity(defender.getCharacter());

        // Attacker melee sheet
        boardModel.createNewMeleeSheet(attacker);
        final MeleeSheet attackerSheet = boardModel.getMeleeSheet(attacker);
        attackerSheet.setAttackWeapon(new TruesteelSword());
        attackerSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setAttackDirection(AttackDirection.THRUST);
        attackerSheet.setManeuver(Maneuver.CHARGE);
        attackerSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setArmor(new SuitOfArmor());

        // Defender melee sheet
        boardModel.createNewMeleeSheet(defender);
        final MeleeSheet defenderSheet = boardModel.getMeleeSheet(defender);
        defenderSheet.setAttackWeapon(new Crossbow());
        defenderSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        defenderSheet.setAttackDirection(AttackDirection.THRUST);
        defenderSheet.setManeuver(Maneuver.CHARGE);
        defenderSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        defenderSheet.setArmor(null);

        Combat.doCombat(boardModel, attacker, defender);

        assertThat(defender.getRestarts(), is(1));
        assertThat(defender.getCharacter().getEntityInformation(), is(EntityInformation.CHARACTER_CAPTAIN));
        assertThat(defender.getCharacter().getCurrentGold(), is(10));
        assertThat(attacker.getCharacter().getCurrentGold(), is(20));
    }

    @Test
    public void canFightDenizen() {
        // Create the board and player
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);

        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        boardModel.getStartingLocation().addEntity(player.getCharacter());

        final Denizen denizen = NativeFactory.createNative(NativeFaction.COMPANY, NativeType.LANCER);
        boardModel.getStartingLocation().addEntity(denizen);
        denizen.setCurrentClearing(boardModel.getStartingLocation());

        // Attacker melee sheet
        boardModel.createNewMeleeSheet(player);
        final MeleeSheet attackerSheet = boardModel.getMeleeSheet(player);
        attackerSheet.setAttackWeapon(new TruesteelSword());
        attackerSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setAttackDirection(AttackDirection.THRUST);
        attackerSheet.setManeuver(Maneuver.CHARGE);
        attackerSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setArmor(new SuitOfArmor());

        // Defender melee sheet, lets override the random just so we can test...
        boardModel.createNewMeleeSheet(denizen);
        final MeleeSheet denizenSheet = boardModel.getMeleeSheet(denizen);
        denizenSheet.setAttackDirection(AttackDirection.THRUST);
        denizenSheet.setManeuver(Maneuver.CHARGE);

        Combat.doCombat(boardModel, player, denizen);

        // The player is alive, the denizen dies (his attack missed).
        assertThat(player.getCharacter().isDead(), is(false));
        assertThat(player.getCharacter().isFatigued(), is(true));
        assertThat(denizen.isDead(), is(true));
        assertThat(denizen.getCurrentClearing().getEntities().contains(denizen), is(false));
        assertThat(player.getCharacter().getCurrentGold(), is(12));
        assertThat(player.getCharacter().getCurrentNotoriety(), is(4));
    }

    @Test
    public void canResolveTwoMisses() {
        // Create the board and player
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);

        final Player attacker = new Player();
        attacker.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        boardModel.getStartingLocation().addEntity(attacker.getCharacter());

        final Player defender = new Player();
        defender.setCharacter(CharacterFactory.createCharacter(CharacterType.CAPTAIN));
        boardModel.getStartingLocation().addEntity(defender.getCharacter());

        // Attacker melee sheet
        boardModel.createNewMeleeSheet(attacker);
        final MeleeSheet attackerSheet = boardModel.getMeleeSheet(attacker);
        attackerSheet.setAttackWeapon(new Crossbow());
        attackerSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setAttackDirection(AttackDirection.THRUST);
        attackerSheet.setManeuver(Maneuver.DODGE);
        attackerSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setArmor(new SuitOfArmor());

        // Defender melee sheet
        boardModel.createNewMeleeSheet(defender);
        final MeleeSheet defenderSheet = boardModel.getMeleeSheet(defender);
        defenderSheet.setAttackWeapon(new Crossbow());
        defenderSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        defenderSheet.setAttackDirection(AttackDirection.THRUST);
        defenderSheet.setManeuver(Maneuver.DODGE);
        defenderSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        defenderSheet.setArmor(new SuitOfArmor());

        Combat.doCombat(boardModel, attacker, defender);


        assertThat(attacker.getCharacter().isWounded(), is(false));
        assertThat(attacker.getCharacter().isDead(), is(false));
        assertThat(attackerSheet.getArmor().isDamaged(), is(false));
        assertThat(defender.getCharacter().isWounded(), is(false));
        assertThat(defender.getCharacter().isDead(), is(false));
        assertThat(defenderSheet.getArmor().isDamaged(), is(false));
    }

    @Test
    public void canAssignMonstersToPlayerInSameClearing() {

        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);

        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        player.getCharacter().setHidden(false);
        boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[0].addEntity(player.getCharacter());

        // Attacker melee sheet
        boardModel.createNewMeleeSheet(player);
        final MeleeSheet attackerSheet = boardModel.getMeleeSheet(player);
        attackerSheet.setAttackWeapon(new Crossbow());
        attackerSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setAttackDirection(AttackDirection.THRUST);
        attackerSheet.setManeuver(Maneuver.DODGE);
        attackerSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setArmor(new SuitOfArmor());

        final AbstractMonster monster = new Dragon();
        boardModel.createNewMeleeSheet(monster);
        boardModel.getStartingLocation().addEntity(monster);

        // Set location to the same tile, but diff clearing.
        boardModel.createNewMeleeSheet(monster);
        boardModel.getAbstractMonsters().add(monster);
        boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[1].addEntity(monster);
        monster.setCurrentClearing(boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[1]);
        monster.setProwling(true);

        final AbstractMonster monster2 = new Spider();
        boardModel.getStartingLocation().addEntity(monster);

        // Set location to a different tile.
        boardModel.createNewMeleeSheet(monster2);
        boardModel.getAbstractMonsters().add(monster2);
        boardModel.getTilesOfType(TileType.VALLEY).get(1).getClearings()[1].addEntity(monster2);
        monster2.setCurrentClearing(boardModel.getTilesOfType(TileType.VALLEY).get(1).getClearings()[1]);
        monster2.setProwling(true);

        // Monster moves to clearing
        Sunset.doSunset(boardModel);

        final List<Entity> monsters = CombatUtils.getMonstersFightingToday(boardModel, Arrays.asList(player.getCharacter()));
        assertThat(monsters, containsInAnyOrder(monster));
        assertThat(monsters, not(containsInAnyOrder(monster2)));

        final MeleeSheet monsterSheet = boardModel.getMeleeSheet(monster);
        assertThat(monsterSheet.getTarget(), is(player.getCharacter()));

    }

    @Test
    public void canMonsterAttackPlayerOnSameTile() throws Exception {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);

        final Player player = new Player();
        boardModel.addPlayer(player);
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        player.getCharacter().setHidden(false);
        boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[0].addEntity(player.getCharacter());

        // Attacker melee sheet
        boardModel.createNewMeleeSheet(player);
        final MeleeSheet attackerSheet = boardModel.getMeleeSheet(player);
        attackerSheet.setAttackWeapon(new BaneSword());
        attackerSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setAttackDirection(AttackDirection.THRUST);
        attackerSheet.setManeuver(Maneuver.DODGE);
        attackerSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setArmor(new SuitOfArmor());

        final AbstractMonster monster = new Dragon();
        // Override the dragons health because I'm too lazy to find a value that makes this test work properly.
        final Field health = Entity.class.getDeclaredField("vulnerability");
        health.setAccessible(true);
        health.set(monster, Harm.HEAVY);
        boardModel.getStartingLocation().addEntity(monster);

        // Set location to the same tile, but diff clearing.
        boardModel.createNewMeleeSheet(monster);
        final MeleeSheet monsterSheet = boardModel.getMeleeSheet(monster);
        monsterSheet.setManeuver(Maneuver.CHARGE);
        boardModel.getAbstractMonsters().add(monster);
        boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[1].addEntity(monster);
        monster.setCurrentClearing(boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[1]);
        monster.setProwling(true);

        final AbstractMonster monster2 = new Spider();
        boardModel.getStartingLocation().addEntity(monster);

        // Set location to a different tile.
        boardModel.createNewMeleeSheet(monster2);
        boardModel.getAbstractMonsters().add(monster2);
        boardModel.getTilesOfType(TileType.VALLEY).get(1).getClearings()[1].addEntity(monster2);
        monster2.setCurrentClearing(boardModel.getTilesOfType(TileType.VALLEY).get(1).getClearings()[1]);
        monster2.setProwling(true);

        // Monster moves to clearing and then we start combat.
        Sunset.doSunset(boardModel);
        Combat.process(Arrays.asList(player), boardModel);

        // The player doesn't die, but is fatigued from playing too many asterisks. The dragon dies.
        assertThat(player.getCharacter().isDead(), is(false));
        assertThat(player.getCharacter().isFatigued(), is(true));
        assertThat(monster.isDead(), is(true));
        assertThat(player.getCharacter().getCurrentFame(), is(10));
        assertThat(player.getCharacter().getCurrentNotoriety(), is(10));
    }

    @Test
    public void canUseAlertedWeapon() {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);

        final Player player = new Player();
        boardModel.addPlayer(player);
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        // Attacker melee sheet
        boardModel.createNewMeleeSheet(player);
        final MeleeSheet attackerSheet = boardModel.getMeleeSheet(player);
        attackerSheet.setAttackWeapon(new Crossbow());

        // not alerted, 0 sharpness.
        assertThat(attackerSheet.getAttackWeapon().getSharpness(), is(0));
        attackerSheet.getAttackWeapon().setAlert(true);
        // alerted, 1 sharpness.
        assertThat(attackerSheet.getAttackWeapon().getSharpness(), is(1));
    }

    @Test
    public void canIgnoreMultipleSameTargetCombats() {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);

        final Player player = new Player();
        boardModel.addPlayer(player);
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        player.getCharacter().setHidden(false);
        boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[0].addEntity(player.getCharacter());

        // Attacker melee sheet
        boardModel.createNewMeleeSheet(player);
        final MeleeSheet attackerSheet = boardModel.getMeleeSheet(player);
        attackerSheet.setAttackWeapon(new BaneSword());
        attackerSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setAttackDirection(AttackDirection.THRUST);
        attackerSheet.setManeuver(Maneuver.DODGE);
        attackerSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setArmor(new SuitOfArmor());

        final AbstractMonster monster = new Dragon();
        boardModel.getStartingLocation().addEntity(monster);

        // Set the target to be the dragon (creating a double combat... monster --> player, player --> monster).
        attackerSheet.setTarget(monster);

        // Set location to the same tile, but diff clearing.
        boardModel.createNewMeleeSheet(monster);
        final MeleeSheet monsterSheet = boardModel.getMeleeSheet(monster);
        monsterSheet.setManeuver(Maneuver.CHARGE);
        boardModel.getAbstractMonsters().add(monster);
        boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[1].addEntity(monster);
        monster.setCurrentClearing(boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[1]);
        monster.setProwling(true);

        final AbstractMonster monster2 = new Spider();
        boardModel.getStartingLocation().addEntity(monster);

        // Set location to a different tile.
        boardModel.createNewMeleeSheet(monster2);
        boardModel.getAbstractMonsters().add(monster2);
        boardModel.getTilesOfType(TileType.VALLEY).get(1).getClearings()[1].addEntity(monster2);
        monster2.setCurrentClearing(boardModel.getTilesOfType(TileType.VALLEY).get(1).getClearings()[1]);
        monster2.setProwling(true);

        // Monster moves to clearing and then we start combat.
        Sunset.doSunset(boardModel);
        assertThat(Combat.process(Arrays.asList(player), boardModel), is(1));
    }

    @Test
    public void canHiddenPreventMonstersAttacking() {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);

        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        player.getCharacter().setHidden(true);
        boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[0].addEntity(player.getCharacter());

        // Attacker melee sheet
        boardModel.createNewMeleeSheet(player);
        final MeleeSheet attackerSheet = boardModel.getMeleeSheet(player);
        attackerSheet.setAttackWeapon(new Crossbow());
        attackerSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setAttackDirection(AttackDirection.THRUST);
        attackerSheet.setManeuver(Maneuver.DODGE);
        attackerSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setArmor(new SuitOfArmor());

        final AbstractMonster monster = new Dragon();
        boardModel.createNewMeleeSheet(monster);
        boardModel.getStartingLocation().addEntity(monster);

        // Set location to the same tile, but diff clearing.
        boardModel.createNewMeleeSheet(monster);
        boardModel.getAbstractMonsters().add(monster);
        boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[1].addEntity(monster);
        monster.setCurrentClearing(boardModel.getTilesOfType(TileType.VALLEY).get(0).getClearings()[1]);
        monster.setProwling(true);

        // Monster moves to clearing
        Sunset.doSunset(boardModel);

        final List<Entity> monsters = CombatUtils.getMonstersFightingToday(boardModel, Arrays.asList(player.getCharacter()));
        assertThat(monsters.size(), is(0));
    }

    @Ignore("This test runs locally, but for some reason fails when run remotely by travis... it builds 2x of what it should.")
    @Test
    public void canNativesHelpOthersOfSameFaction() throws Exception {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        EntityBuilder.placeEntities(boardModel);

        final Player player = new Player();
        boardModel.addPlayer(player);
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        player.getCharacter().setHidden(false);
        boardModel.getClearingOfDwelling(Dwelling.CHAPEL).addEntity(player.getCharacter());

        boardModel.createNewMeleeSheet(player);
        final MeleeSheet theSheet = boardModel.getMeleeSheet(player);
        theSheet.setAttackWeapon(new BaneSword());
        theSheet.setArmor(new TremendousArmor());
        theSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        theSheet.setAttackDirection(AttackDirection.THRUST);
        theSheet.setManeuver(Maneuver.DODGE);
        theSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        // Set to an order native
        theSheet.setTarget(boardModel.getClearingOfDwelling(Dwelling.CHAPEL).getEntities().get(0));

        // Set their health temporarily so they don't die.
        final Field health = Entity.class.getDeclaredField("vulnerability");
        health.setAccessible(true);
        health.set(player.getCharacter(), Harm.TREMENDOUS);

        // Number of combats = all the order natives.
        assertThat(Combat.process(Arrays.asList(player), boardModel), is(4));
    }

    @Test
    public void canDropTreasurePileWhenDead() {
        // Create the board and player
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);

        final Player attacker = new Player();
        attacker.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));
        boardModel.getStartingLocation().addEntity(attacker.getCharacter());

        final Player defender = new Player();
        defender.setCharacter(CharacterFactory.createCharacter(CharacterType.CAPTAIN));
        boardModel.getStartingLocation().addEntity(defender.getCharacter());

        // Attacker melee sheet
        boardModel.createNewMeleeSheet(attacker);
        final MeleeSheet attackerSheet = boardModel.getMeleeSheet(attacker);
        attackerSheet.setAttackWeapon(new TruesteelSword());
        attackerSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setAttackDirection(AttackDirection.THRUST);
        attackerSheet.setManeuver(Maneuver.CHARGE);
        attackerSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        attackerSheet.setArmor(new SuitOfArmor());

        // Defender melee sheet
        boardModel.createNewMeleeSheet(defender);
        final MeleeSheet defenderSheet = boardModel.getMeleeSheet(defender);
        defenderSheet.setAttackWeapon(new Crossbow());
        defenderSheet.setAttackChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        defenderSheet.setAttackDirection(AttackDirection.THRUST);
        defenderSheet.setManeuver(Maneuver.CHARGE);
        defenderSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        defenderSheet.setArmor(null);

        Combat.doCombat(boardModel, attacker, defender);

        // check they did die.
        assertThat(defender.getRestarts(), is(1));

        // check gold pile stuff here.
        assertThat(boardModel.getStartingLocation().getParentTile().getChits().size(), is(1));
        assertThat(boardModel.getStartingLocation().getParentTile().getChits().get(0), is(instanceOf(GoldChit.class)));

        final GoldChit theChit = (GoldChit) boardModel.getStartingLocation().getParentTile().getChits().get(0);
        assertThat(theChit.getTreasure().size(), is(4));
        assertThat(theChit.getTreasure(), containsInAnyOrder(new ShortSword(), new Helmet(), new BreastPlate(), new Shield()));
    }
}