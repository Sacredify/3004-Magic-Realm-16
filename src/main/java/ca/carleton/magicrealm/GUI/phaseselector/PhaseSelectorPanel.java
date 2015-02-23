package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.MovePhase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Tony on 20/02/2015.
 */
public class PhaseSelectorPanel extends JPanel {
    public static final String CONFIRM_TEXT = "Move";

    public static final int PHASE_PANEL_WIDTH = 500;
    public static final int PHASE_PANEL_HEIGHT = 300;

    private JComboBox<AbstractPhase> firstPhaseBox;

    private JComboBox secondPhaseBox;

    private JComboBox thirdPhaseBox;

    private JComboBox fourthPhaseBox;

    private JButton confirmButton;

    private ArrayList<AbstractPhase> phases;

    public PhaseSelectorPanel() {
        this.phases = new ArrayList<>();
        this.phases.add(new MovePhase());

        this.setSize(PHASE_PANEL_WIDTH, PHASE_PANEL_HEIGHT);

        firstPhaseBox = new JComboBox<>(phases.toArray(new AbstractPhase[phases.size()]));
        this.add(firstPhaseBox);

        confirmButton = new JButton(CONFIRM_TEXT);
        confirmButton.setSize(50,30);
        confirmButton.setLocation(PHASE_PANEL_WIDTH/2 - confirmButton.getWidth()/2,
                                  250);
        this.add(confirmButton);
    }

    public JComboBox getFirstPhaseBox() {
        return firstPhaseBox;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }
}
