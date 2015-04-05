import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.control.Combat;
import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
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
import ca.carleton.magicrealm.item.armor.SuitOfArmor;
import ca.carleton.magicrealm.item.weapon.*;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
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
}