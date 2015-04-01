package ca.carleton.magicrealm.game.combat;

/**
 * Created with IntelliJ IDEA.
 * Date: 25/03/2015
 * Time: 2:36 PM
 */
public enum Harm {
    NEGLIGIBLE {
        public Harm decrease() {
            return this;
        }
    },
    LIGHT,
    MEDIUM,
    HEAVY,
    TREMENDOUS {
        public Harm increase() {
            return this;
        }
    },
    LETHAL; // only used by missile table. We won't ever get it, but we can say we wrote the logic in.

    public Harm increase() {
        return values()[this.ordinal() + 1];
    }

    public Harm decrease() {
        return values()[this.ordinal() - 1];
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
