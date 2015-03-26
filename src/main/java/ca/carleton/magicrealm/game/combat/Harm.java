package ca.carleton.magicrealm.game.combat;

/**
 * Created with IntelliJ IDEA.
 * Date: 25/03/2015
 * Time: 2:36 PM
 */
public enum Harm {
    NEGLIGIBLE,
    LIGHT,
    MEDIUM,
    HEAVY,
    TREMENDOUS,
    LETHAL; // only used by missile table. We won't ever get it, but we can say we wrote the logic in.

    public Harm increase() {
        switch (this) {
            case NEGLIGIBLE:
                return LIGHT;
            case LIGHT:
                return MEDIUM;
            case MEDIUM:
                return HEAVY;
            case HEAVY:
                return TREMENDOUS;
            default:
                return TREMENDOUS;
        }
    }

    public Harm decrease() {
        switch (this) {
            case NEGLIGIBLE:
                return NEGLIGIBLE;
            case LIGHT:
                return NEGLIGIBLE;
            case MEDIUM:
                return LIGHT;
            case HEAVY:
                return MEDIUM;
            case TREMENDOUS:
                return HEAVY;
            default:
                return HEAVY;
        }
    }

    public boolean greaterThan(final Harm harm) {
        switch (this) {
            case NEGLIGIBLE:
                return false;
            case LIGHT:
                return harm == Harm.NEGLIGIBLE;
            case MEDIUM:
                return harm == Harm.NEGLIGIBLE || harm == Harm.LIGHT;
            case HEAVY:
                return harm == Harm.NEGLIGIBLE || harm == Harm.LIGHT || harm == Harm.MEDIUM;
            case TREMENDOUS:
                return harm == Harm.NEGLIGIBLE || harm == Harm.LIGHT || harm == Harm.MEDIUM || harm == Harm.HEAVY;
            case LETHAL:
                return true;
            default:
                return false;
        }
    }
}
