package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.GUI.phaseselector.detailwindows.MoveSelectionMenu;
import ca.carleton.magicrealm.control.GameController;
import ca.carleton.magicrealm.game.Player;
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

    public static final String PHASE_SELECTOR_WINDOW = "Birdsong - Action Selection Menu";
    public static final int PHASE_WINDOW_WIDTH = 300;
    public static final int PHASE_WINDOW_HEIGHT = 200;

    PhaseSelectorPanel phaseSelectorPanel;

    PhaseSelectorModel phaseSelectorModel;

    GameController controller;

    MoveSelectionMenu moveSelectionMenu;

    private final Player player;

    public PhaseSelectorMenu(final Player player, final List<AbstractPhase> phases, int numberOfPhases, final GameController gameController) {
        this.player = player;
        this.setTitle(PHASE_SELECTOR_WINDOW);
        this.setLocationRelativeTo(gameController.getParentWindow());

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
                PhaseType selectedPhase = (PhaseType) PhaseSelectorMenu.this.phaseSelectorPanel.getFirstPhaseBox().getSelectedItem();
                if (selectedPhase.equals(PhaseType.MOVE)) {
                    PhaseSelectorMenu.this.moveSelectionMenu = new MoveSelectionMenu(PhaseSelectorMenu.this.controller.getBoardModel());
                    PhaseSelectorMenu.this.moveSelectionMenu.getMoveSelectionPanel().getConfirmButton().addActionListener(PhaseSelectorMenu.this.createActionListenerForMoveSelectButton());
                } else if (selectedPhase.equals(PhaseType.HIDE)) {
                    PhaseSelectorMenu.this.phaseSelectorModel.addHidePhase();
                    ((JButton) e.getSource()).setEnabled(false);
                }
            }
        };
    }

    public ActionListener createActionListenerForMoveSelectButton() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PhaseSelectorMenu.this.phaseSelectorModel.addMovementPhase(PhaseSelectorMenu.this.moveSelectionMenu.getMoveSelectionPanel().getClearingJList().getSelectedValue(),
                        PhaseSelectorMenu.this.controller.getBoardModel().getClearingForPlayer(PhaseSelectorMenu.this.player));
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
