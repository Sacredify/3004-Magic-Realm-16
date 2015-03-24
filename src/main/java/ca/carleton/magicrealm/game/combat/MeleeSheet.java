package ca.carleton.magicrealm.game.combat;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.item.weapon.AbstractWeapon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/03/15
 * Time: 3:52 PM
 */
public class MeleeSheet implements Serializable {

    private static final long serialVersionUID = 2502936258541118790L;

    private final Player player;

    // The chits used by the player while planning a round. They cannot use these ones again (need to check this list).
    private List<ActionChit> usedChits = new ArrayList<ActionChit>();

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

    MeleeSheet(final Player player) {
        this.player = player;
    }

    public void resetSheet() {
        this.usedChits.clear();
        this.maneuverChit = null;
        this.attackChit = null;
        this.encounterStepChit = null;
        this.attackWeapon = null;
        this.attackDirection = null;
        this.maneuver = null;
    }

    public void setEncounterStepChit(final ActionChit chit) {
        this.encounterStepChit = chit;
        this.usedChits.add(this.encounterStepChit);
    }

    public void setAttackChit(final ActionChit chit) {
        this.attackChit = chit;
        this.usedChits.add(this.attackChit);
    }

    public void setManeuverChit(final ActionChit chit) {
        this.maneuverChit = chit;
        this.usedChits.add(this.maneuverChit);
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

    public List<ActionChit> getUsedChits() {
        return this.usedChits;
    }

}
