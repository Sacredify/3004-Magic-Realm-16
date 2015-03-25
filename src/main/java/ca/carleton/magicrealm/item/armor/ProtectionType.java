package ca.carleton.magicrealm.item.armor;

import ca.carleton.magicrealm.game.combat.AttackDirection;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:28 AM
 */
public enum ProtectionType {
    ALL_DIRECTIONS,
    THRUST_AND_SWING,
    SMASH,
    ONE_DIRECTION;

    /**
     * Whether or not the armor intercepts an attack from a given direction.
     *
     * @param attackDirection the direction.
     * @return true if it does.
     */
    public boolean intercepts(final AttackDirection attackDirection) {
        switch (this) {
            case ALL_DIRECTIONS:
                return true;
            case THRUST_AND_SWING:
                return attackDirection == AttackDirection.SWING || attackDirection == AttackDirection.THRUST;
            case SMASH:
                return attackDirection == AttackDirection.SMASH;
            case ONE_DIRECTION:
                return attackDirection == AttackDirection.THRUST;
            default:
                return false;
        }
    }

}
