package ca.carleton.magicrealm.game.combat.chit;

/**
 * Used to do actions within the game (move, etc.)
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 16/03/15
 * Time: 3:53 PM
 */
public class ActionChit {

    /**
     * The number on the chit (time it takes to do something)
     */
    private final int time;

    /**
     * The letter on the chit (strength).
     */
    private final String strength;

    /**
     * The number of fatigue asterisks on the chit
     */
    private int fatigueAsterisks;

    /**
     * The action of the chit.
     */
    private final ActionType action;

    private ActionChit(final ActionChitBuilder builder) {
        this.time = builder.time;
        this.strength = builder.strength;
        this.fatigueAsterisks = builder.fatigueAsterisks;
        this.action = builder.action;
    }

    public ActionType getAction() {
        return this.action;
    }


    public String getStrength() {
        return this.strength;
    }

    public int getFatigueAsterisks() {
        return this.fatigueAsterisks;
    }

    public void setFatigueAsterisks(final int fatigueAsterisks) {
        this.fatigueAsterisks = fatigueAsterisks;
    }

    public int getTime() {
        return this.time;
    }

    /**
     * Builder class for action chits.
     */
    public static class ActionChitBuilder {

        private int time;

        private String strength;

        private int fatigueAsterisks;

        private final ActionType action;

        public ActionChitBuilder(final ActionType action) {
            this.action = action;
        }

        public ActionChitBuilder withTime(final int time) {
            this.time = time;
            return this;
        }

        public ActionChitBuilder withStrength(final String strength) {
            this.strength = strength;
            return this;
        }

        public ActionChitBuilder withFatigueAsterisks(final int number) {
            this.fatigueAsterisks = number;
            return this;
        }

        public ActionChit build() {
            return new ActionChit(this);
        }
    }
}
