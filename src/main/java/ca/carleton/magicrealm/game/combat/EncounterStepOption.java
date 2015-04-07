package ca.carleton.magicrealm.game.combat;

/**
 * Created with IntelliJ IDEA.
 * Date: 07/04/2015
 * Time: 11:17 AM
 */
public enum EncounterStepOption {
    RUN_AWAY {
        public String toString() {
            return "Play a move chit to run away!";
        }
    },
    ALERT_WEAPON {
        public String toString() {
            return "Pay a fight chit to alert a weapon!";
        }
    },
    DO_NOTHING {
        public String toString() {
            return "Do nothing.";
        }
    }
}