package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.GUI.phaseselector.detailwindows.MoveSelectionMenu;
import ca.carleton.magicrealm.control.GameController;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Tony on 20/02/2015.
 */
public class PhaseSelectorMenu extends JDialog {

    public static final String PHASE_SELECTOR_WINDOW = "Select your phases";
    public static final int PHASE_WINDOW_WIDTH = 500;
    public static final int PHASE_WINDOW_HEIGHT = 300;

    PhaseSelectorPanel phaseSelectorPanel;

    PhaseSelectorModel phaseSelectorModel;

    GameController controller;

    MoveSelectionMenu moveSelectionMenu;

    public PhaseSelectorMenu(final List<AbstractPhase> phases, int numberOfPhases, final GameController gameController) {
        this.setTitle(PHASE_SELECTOR_WINDOW);

        this.phaseSelectorPanel = new PhaseSelectorPanel();
        this.phaseSelectorPanel.getConfirmButton().addActionListener(this.createActionListenerForPhaseSelectButton());
        this.phaseSelectorPanel.getDoneEnteringPhasesButton().addActionListener(this.createActionListenerForDoneButton());

        this.phaseSelectorModel = new PhaseSelectorModel(this, phases);
        this.controller = gameController;

        this.setSize(PHASE_WINDOW_WIDTH, PHASE_WINDOW_HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.add(this.phaseSelectorPanel);
    }

    /**
     * Called when done.
     */
    public void disposeWindow() {
        this.dispose();
        this.controller.doneEnteringPhasesForDay();
    }

    public ActionListener createActionListenerForPhaseSelectButton() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton comboBox = (JButton) e.getSource();
                PhaseType selectedPhase = (PhaseType) PhaseSelectorMenu.this.phaseSelectorPanel.getFirstPhaseBox().getSelectedItem();
                if (selectedPhase.equals(PhaseType.MOVE)) {
                    PhaseSelectorMenu.this.moveSelectionMenu = new MoveSelectionMenu(PhaseSelectorMenu.this.controller.getCurrentPlayer().getCurrentClearing());
                    PhaseSelectorMenu.this.moveSelectionMenu.getMoveSelectionPanel().getConfirmButton().addActionListener(PhaseSelectorMenu.this.createActionListenerForMoveSelectButton());
                }
            }
        };
    }

    public ActionListener createActionListenerForMoveSelectButton() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PhaseSelectorMenu.this.phaseSelectorModel.addMovementPhase(PhaseSelectorMenu.this.moveSelectionMenu.getMoveSelectionPanel().getClearingJList().getSelectedValue());
                PhaseSelectorMenu.this.moveSelectionMenu.dispose();
            }
        };
    }

    public ActionListener createActionListenerForDoneButton() {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PhaseSelectorMenu.this.phaseSelectorModel.done();
            }
        };
    }
}
