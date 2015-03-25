package ca.carleton.magicrealm.game.combat.chit;

import ca.carleton.magicrealm.game.combat.Harm;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Used to do actions within the game (move, etc.)
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 16/03/15
 * Time: 3:53 PM
 */
public class ActionChit implements Serializable {

    private static final long serialVersionUID = -4329114575505018771L;

    /**
     * The number on the chit (time it takes to do something)
     */
    private final int time;

    /**
     * The letter on the chit (strength).
     */
    private final Harm strength;

    /**
     * The number of fatigue asterisks on the chit
     */
    private final int fatigueAsterisks;

    /**
     * The action of the chit.
     */
    private final ActionType action;

    /**
     * Whether or not the chit is fatigued
     */
    private boolean fatigued = false;

    private boolean wounded = false;

    private ActionChit(final ActionChitBuilder builder) {
        this.time = builder.time;
        this.strength = builder.strength;
        this.fatigueAsterisks = builder.fatigueAsterisks;
        this.action = builder.action;
    }

    public ActionType getAction() {
        return this.action;
    }


    public Harm getStrength() {
        return this.strength;
    }

    public int getFatigueAsterisks() {
        return this.fatigueAsterisks;
    }

    public int getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(this.action.name().toLowerCase()) + " " + this.strength + getAsterisksRepresentation(this.fatigueAsterisks) + getWoundedAndFatigued(this.wounded, this.fatigued);
    }

    private static String getWoundedAndFatigued(final boolean wounded, final boolean fatigued) {
        return String.format(" wounded=[%b], fatigued=[%b]", wounded, fatigued);
    }

    private static String getAsterisksRepresentation(final int number) {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        while (count < number) {
            builder.append("*");
            count++;
        }
        return builder.toString();
    }

    public boolean isWounded() {
        return this.wounded;
    }

    public void setWounded(final boolean wounded) {
        this.wounded = wounded;
    }

    public boolean isFatigued() {
        return this.fatigued;
    }

    public void setFatigued(final boolean fatigued) {
        this.fatigued = fatigued;
    }

    /**
     * Builder class for action chits.
     */
    public static class ActionChitBuilder {

        private int time;

        private Harm strength;

        private int fatigueAsterisks;

        private final ActionType action;

        public ActionChitBuilder(final ActionType action) {
            this.action = action;
        }

        public ActionChitBuilder withTime(final int time) {
            this.time = time;
            return this;
        }

        public ActionChitBuilder withStrength(final Harm strength) {
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
