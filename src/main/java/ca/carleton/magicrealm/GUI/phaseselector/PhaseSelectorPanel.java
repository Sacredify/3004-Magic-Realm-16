package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.game.phase.impl.MovePhase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Tony on 20/02/2015.
 */
public class PhaseSelectorPanel extends JPanel {

    public static final String CONFIRM_TEXT = "Enter details";

    public static final int PHASE_PANEL_WIDTH = 500;
    public static final int PHASE_PANEL_HEIGHT = 300;

    private final JComboBox<PhaseType> firstPhaseBox;

    private JComboBox secondPhaseBox;

    private JComboBox thirdPhaseBox;

    private JComboBox fourthPhaseBox;

    private final JButton confirmButton;

    private final JButton doneEnteringPhases;

    PhaseType[] phases = PhaseType.values();

    public PhaseSelectorPanel() {
        this.setSize(PHASE_PANEL_WIDTH, PHASE_PANEL_HEIGHT);

        this.firstPhaseBox = new JComboBox<>(this.phases);
        this.add(this.firstPhaseBox);

        this.confirmButton = new JButton(CONFIRM_TEXT);
        this.confirmButton.setSize(50, 30);
        this.confirmButton.setLocation(PHASE_PANEL_WIDTH / 2 - this.confirmButton.getWidth() / 2, 250);
        this.add(this.confirmButton);

        this.doneEnteringPhases = new JButton("Done");
        this.doneEnteringPhases.setSize(50, 30);
        this.doneEnteringPhases.setLocation(250, 200);
        this.add(this.doneEnteringPhases);

    }

    public JComboBox<PhaseType> getFirstPhaseBox() {
        return this.firstPhaseBox;
    }

    public JButton getConfirmButton() {
        return this.confirmButton;
    }

    public JButton getDoneEnteringPhasesButton() {
        return this.doneEnteringPhases;
    }
}
