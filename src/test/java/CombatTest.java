import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.control.Combat;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.AttackDirection;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.game.combat.Maneuver;
import ca.carleton.magicrealm.game.combat.MeleeSheet;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.game.combat.chit.ActionType;
import ca.carleton.magicrealm.item.ItemInformation;
import ca.carleton.magicrealm.item.armor.SuitOfArmor;
import ca.carleton.magicrealm.item.weapon.AbstractWeapon;
import ca.carleton.magicrealm.item.weapon.AttackType;
import ca.carleton.magicrealm.item.weapon.Crossbow;
import ca.carleton.magicrealm.item.weapon.ThrustingSword;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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
        attackerSheet.setTarget(defender.getCharacter());

        // Defender melee sheet
        boardModel.createNewMeleeSheet(defender);
        final MeleeSheet defenderSheet = boardModel.getMeleeSheet(defender);
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
        attackerSheet.setTarget(defender.getCharacter());

        // Defender melee sheet
        boardModel.createNewMeleeSheet(defender);
        final MeleeSheet defenderSheet = boardModel.getMeleeSheet(defender);
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
        attackerSheet.setTarget(defender.getCharacter());

        // Defender melee sheet
        boardModel.createNewMeleeSheet(defender);
        final MeleeSheet defenderSheet = boardModel.getMeleeSheet(defender);
        defenderSheet.setManeuver(Maneuver.DODGE);
        defenderSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        defenderSheet.setArmor(new SuitOfArmor());

        Combat.doCombat(boardModel, attacker, defender);

        assertThat(defender.getCharacter().isWounded(), is(true));
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
        attackerSheet.setTarget(defender.getCharacter());

        // Defender melee sheet
        boardModel.createNewMeleeSheet(defender);
        final MeleeSheet defenderSheet = boardModel.getMeleeSheet(defender);
        defenderSheet.setManeuver(Maneuver.CHARGE);
        defenderSheet.setManeuverChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());
        defenderSheet.setArmor(new SuitOfArmor());

        Combat.doCombat(boardModel, attacker, defender);

        assertThat(defender.getCharacter().isWounded(), is(true));
        assertThat(defender.getCharacter().isDead(), is(false));
        assertThat(defenderSheet.getArmor().isDamaged(), is(true));
    }
}