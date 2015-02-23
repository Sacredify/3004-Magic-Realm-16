package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.game.phase.AbstractPhase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Tony on 20/02/2015.
 */
public class PhaseSelectorPanel extends JPanel {

    public static final int PHASE_PANEL_WIDTH = 500;
    public static final int PHASE_PANEL_HEIGHT = 300;

    public static final String[] PHASE_NAMES = {"Move"};
    public static final String CONFIRM = "ENTER";

    private JComboBox<AbstractPhase> firstPhaseBox;

    private JComboBox secondPhaseBox;

    private JComboBox thirdPhaseBox;

    private JComboBox fourthPhaseBox;



    public PhaseSelectorPanel() {
        this.setSize(PHASE_PANEL_WIDTH, PHASE_PANEL_HEIGHT);

        firstPhaseBox = new JComboBox(PHASE_NAMES);

        this.add(firstPhaseBox);

    }

    public JComboBox getFirstPhaseBox() {
        return firstPhaseBox;
    }
}
