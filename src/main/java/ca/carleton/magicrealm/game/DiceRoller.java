package ca.carleton.magicrealm.game;

import java.util.Random;

/**
 * Utility class for dice rolls.
 *
 * Created with IntelliJ IDEA.
 * Date: 23/02/15
 * Time: 4:15 AM
 */
public final class DiceRoller {

    private DiceRoller() {}

    private static final Random random = new Random();

    public static int rollOnce() {
        return random.nextInt((6 - 1) + 1) + 1;
    }

    public static int rollTwiceTakeHigher() {
      return Math.max(random.nextInt((6 - 1) + 1) + 1, random.nextInt((6 - 1) + 1) + 1);
    }

    public static int rollTwiceTakeLower() {
        return Math.min(random.nextInt((6 - 1) + 1) + 1, random.nextInt((6 - 1) + 1) + 1);
    }

}
