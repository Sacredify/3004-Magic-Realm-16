package ca.carleton.magicrealm.game.combat;

/**
 * Created with IntelliJ IDEA.
 * Date: 24/03/2015
 * Time: 6:17 PM
 */
public enum AttackDirection {
    THRUST,
    SWING,
    SMASH;

    /**
     * Whether or not the attack direction matches the maneuver.
     *
     * @param maneuver the maneuver.
     * @return true if they match.
     */
    public boolean matches(final Maneuver maneuver) {
        switch (this) {
            case THRUST:
                return maneuver == Maneuver.CHARGE;
            case SWING:
                return maneuver == Maneuver.DODGE;
            case SMASH:
                return maneuver == Maneuver.DUCK;
            default:
                return false;
        }
    }

}
