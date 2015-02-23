package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.GUI.tile.Clearing;
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
        this.phaseSelectorPanel.getFirstPhaseBox().addActionListener(createActionListenerForPhaseSelectList());

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

    public PhaseSelectorPanel getPhaseSelectorPanel() {
        return this.phaseSelectorPanel;
    }

    public void setPhaseSelectorPanel(PhaseSelectorPanel phaseSelectorPanel) {
        this.phaseSelectorPanel = phaseSelectorPanel;
    }

    public ActionListener createActionListenerForPhaseSelectList() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractPhase selectedPhase = (AbstractPhase)e.getSource();
                if (selectedPhase.getPhaseType().equals(PhaseType.MOVE)) {
                    moveSelectionMenu = new MoveSelectionMenu(controller.getCurrentPlayer().getCurrentClearing());
                    moveSelectionMenu.getMoveSelectionPanel().getConfirmButton().addActionListener(createActionListenerForMoveSelectList());
                }
            }
        };
    }

    public ActionListener createActionListenerForMoveSelectList() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                phaseSelectorModel.addMovementPhase(moveSelectionMenu.getMoveSelectionPanel().getClearingJList().getSelectedValue());

            }
        };
    }
}
