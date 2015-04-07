package ca.carleton.magicrealm.game;

import ca.carleton.magicrealm.Launcher;

import javax.swing.*;
import java.util.Random;

/**
 * Utility class for dice rolls.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 23/02/15
 * Time: 4:15 AM
 */
public final class DiceRoller {

    private DiceRoller() {
    }

    private static final Random random = new Random();

    public static int rollOnce() {
        return random.nextInt((6 - 1) + 1) + 1;
    }

    public static int rollTwiceTakeHigher() {
        if (!Launcher.CHEAT_MODE) {
            return Math.max(rollOnce(), rollOnce());
        } else {
            return Integer.parseInt(JOptionPane.showInputDialog(null, "Enter cheat roll. Warning: No validation will occur. Use at your own risk."));
        }
    }

    public static int rollTwiceTakeLower() {
        if (!Launcher.CHEAT_MODE) {
            return Math.min(rollOnce(), rollOnce());
        } else {
            return Integer.parseInt(JOptionPane.showInputDialog(null, "Enter cheat roll. Warning: No validation will occur. Use at your own risk."));
        }
    }

    public static Random getInstance() {
        return random;
    }
}
