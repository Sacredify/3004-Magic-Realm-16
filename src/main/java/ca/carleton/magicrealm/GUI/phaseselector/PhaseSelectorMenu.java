package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.game.phase.AbstractPhase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Tony on 20/02/2015.
 */
public class PhaseSelectorMenu extends JFrame {

    public static final String PHASE_SELECTOR_WINDOW = "Select your phases";
    public static final int PHASE_WINDOW_WIDTH = 500;
    public static final int PHASE_WINDOW_HEIGHT = 300;

    PhaseSelectorPanel phaseSelectorPanel;

    PhaseSelectorModel phaseSelectorModel;

    public PhaseSelectorMenu(final List<AbstractPhase> phases, int numberOfPhases) {
        super(PHASE_SELECTOR_WINDOW);

        this.phaseSelectorPanel = new PhaseSelectorPanel();
        this.phaseSelectorModel = new PhaseSelectorModel(phases);

        this.setSize(PHASE_WINDOW_WIDTH, PHASE_WINDOW_HEIGHT);

        this.add(this.phaseSelectorPanel);
    }

    public PhaseSelectorPanel getPhaseSelectorPanel() {
        return this.phaseSelectorPanel;
    }

    public void setPhaseSelectorPanel(PhaseSelectorPanel phaseSelectorPanel) {
        this.phaseSelectorPanel = phaseSelectorPanel;
    }
}
