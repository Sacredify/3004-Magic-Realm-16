package ca.carleton.magicrealm.game.combat;

import ca.carleton.magicrealm.entity.Entity;

/**
 * Used by entities on the melee sheet to determine how they are fighting.
 * Created with IntelliJ IDEA.
 * Date: 25/03/2015
 * Time: 2:16 PM
 */
public class AttackBox {

    private AttackDirection attackDirection;

    private Maneuver maneuver;

    private Entity entity;

    public AttackBox(final AttackDirection attackDirection, final Maneuver maneuver, final Entity entity) {
        this.attackDirection = attackDirection;
        this.maneuver = maneuver;
        this.entity = entity;
    }

    public static AttackBox newChargeAndThrust(final Entity entity) {
        return new AttackBox(AttackDirection.THRUST, Maneuver.CHARGE, entity);
    }

    public static AttackBox newDuckAndSwing(final Entity entity) {
        return new AttackBox(AttackDirection.SWING, Maneuver.DUCK, entity);

    }

    public static AttackBox newDuckAndSmash(final Entity entity) {
        return new AttackBox(AttackDirection.SMASH, Maneuver.DUCK, entity);
    }

    public AttackDirection getAttackDirection() {
        return this.attackDirection;
    }

    public Maneuver getManeuver() {
        return this.maneuver;
    }

    public Entity getEntity() {
        return this.entity;
    }

}
