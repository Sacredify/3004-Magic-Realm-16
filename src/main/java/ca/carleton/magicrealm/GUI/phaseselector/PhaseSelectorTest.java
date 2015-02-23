package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.control.GameController;
import ca.carleton.magicrealm.game.phase.AbstractPhase;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Date: 23/02/15
 * Time: 2:54 AM
 */
public class PhaseSelectorTest {

    public static void main(final String[] args) {
        new PhaseSelectorMenu(new ArrayList<AbstractPhase>(), 4, new GameController()).setVisible(true);
    }

}
