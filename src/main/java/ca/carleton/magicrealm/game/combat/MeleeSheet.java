package ca.carleton.magicrealm.game.combat;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.item.armor.AbstractArmor;
import ca.carleton.magicrealm.item.weapon.AbstractWeapon;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/03/15
 * Time: 3:52 PM
 */
public class MeleeSheet implements Serializable {

    private static final long serialVersionUID = 2502936258541118790L;

    private static final int NUMBER_ATTACKERS = 3;

    private final Player player;

    // The move or fight chit used during the encounter step. If encounter, allow the player to alert a weapon. If move, run away to another clearing.
    private ActionChit encounterStepChit;

    // The target of the combat.
    private Entity target;

    // Chits used when planning an "Attack" are the weapon, a FIGHT chit for the timing, and a direction.
    private AbstractWeapon attackWeapon;

    private ActionChit attackChit;

    private AttackDirection attackDirection;

    // Chits used when planning a "Maneuver" are the move chit used for the timing, and the maneuver selected.
    private Maneuver maneuver;

    private ActionChit maneuverChit;

    // Armor. Can only use "active" armor.
    private AbstractArmor armor;

    MeleeSheet(final Player player) {
        this.player = player;
    }

    public void resetSheet() {
        this.attackChit = null;
        this.encounterStepChit = null;
        this.attackWeapon = null;
        this.attackDirection = null;
        this.maneuver = null;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setEncounterStepChit(final ActionChit chit) {
        this.encounterStepChit = chit;
    }

    public void setAttackChit(final ActionChit chit) {
        this.attackChit = chit;
    }

    public void setManeuverChit(final ActionChit chit) {
        this.maneuverChit = chit;
    }

    public ActionChit getEncounterStepChit() {
        return this.encounterStepChit;
    }

    public ActionChit getAttackChit() {
        return this.attackChit;
    }

    public ActionChit getManeuverChit() {
        return this.maneuverChit;
    }

    public AbstractWeapon getAttackWeapon() {
        return this.attackWeapon;
    }

    public void setAttackWeapon(final AbstractWeapon attackWeapon) {
        this.attackWeapon = attackWeapon;
    }

    public AttackDirection getAttackDirection() {
        return this.attackDirection;
    }

    public void setAttackDirection(final AttackDirection attackDirection) {
        this.attackDirection = attackDirection;
    }

    public Maneuver getManeuver() {
        return this.maneuver;
    }

    public void setManeuver(final Maneuver maneuver) {
        this.maneuver = maneuver;
    }

    public Entity getTarget() {
        return this.target;
    }

    public void setTarget(final Entity target) {
        this.target = target;
    }

    public AbstractArmor getArmor() {
        return this.armor;
    }

    public void setArmor(final AbstractArmor armor) {
        this.armor = armor;
    }

}
