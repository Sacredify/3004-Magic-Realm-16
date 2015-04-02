package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.game.phase.PhaseType;

import javax.swing.*;

/**
 * The panel.
 *
 * Created by Tony on 20/02/2015.
 */
public class PhaseSelectorPanel extends JPanel {

    public static final String CONFIRM_TEXT = "Submit";

    public static final int PHASE_PANEL_WIDTH = 500;
    public static final int PHASE_PANEL_HEIGHT = 300;

    private final JComboBox<PhaseType> firstPhaseBox;

    private final JLabel infoLabel;

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

        this.infoLabel = new JLabel();
        this.infoLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        this.add(this.infoLabel);

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

    public void updateInfoText(final String newText) {
        this.infoLabel.setText(newText);
    }
}
