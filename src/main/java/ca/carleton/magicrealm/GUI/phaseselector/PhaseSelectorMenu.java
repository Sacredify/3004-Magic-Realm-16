package ca.carleton.magicrealm.GUI.phaseselector;

import ca.carleton.magicrealm.GUI.phaseselector.detailwindows.AlertSelectionMenu;
import ca.carleton.magicrealm.GUI.phaseselector.detailwindows.MoveSelectionMenu;
import ca.carleton.magicrealm.GUI.phaseselector.detailwindows.TradeSelectionMenu;
import ca.carleton.magicrealm.control.GameController;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.item.Item;
import ca.carleton.magicrealm.item.treasure.Treasure;
import ca.carleton.magicrealm.item.weapon.AbstractWeapon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 20/02/2015.
 */
public class PhaseSelectorMenu extends JDialog {

    private static final Logger LOG = LoggerFactory.getLogger(PhaseSelectorMenu.class);

    public static final String PHASE_SELECTOR_WINDOW = "Birdsong - Action Selection Menu";

    public static final int PHASE_WINDOW_WIDTH = 300;

    public static final int PHASE_WINDOW_HEIGHT = 200;

    private static final long serialVersionUID = -5375929478122288444L;

    PhaseSelectorPanel phaseSelectorPanel;

    PhaseSelectorModel phaseSelectorModel;

    GameController controller;

    MoveSelectionMenu moveSelectionMenu;

    TradeSelectionMenu tradeSelectionMenu;

    AlertSelectionMenu alertSelectionMenu;

    private final Player player;

    public PhaseSelectorMenu(final Player player, final List<AbstractPhase> phases, int numberOfPhases, final GameController gameController) {
        this.player = player;
        this.setTitle(PHASE_SELECTOR_WINDOW);
        this.setLocationRelativeTo(gameController.getParentWindow());

        this.phaseSelectorPanel = new PhaseSelectorPanel();
        this.phaseSelectorPanel.getConfirmButton().addActionListener(this.createActionListenerForPhaseSelectButton());
        this.phaseSelectorPanel.getDoneEnteringPhasesButton().addActionListener(this.createActionListenerForDoneButton());

        this.phaseSelectorModel = new PhaseSelectorModel(this, phases,player);
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
                LOG.info("Adding {} to list of phases.", selectedPhase);
                if (selectedPhase.equals(PhaseType.MOVE)) {
                    PhaseSelectorMenu.this.moveSelectionMenu = new MoveSelectionMenu(PhaseSelectorMenu.this.controller.getBoardModel());
                    PhaseSelectorMenu.this.moveSelectionMenu.getMoveSelectionPanel().getConfirmButton().addActionListener(PhaseSelectorMenu.this.createActionListenerForMoveSelectButton());
                } else if (selectedPhase.equals(PhaseType.HIDE)) {
                    PhaseSelectorMenu.this.phaseSelectorModel.addHidePhase();
                } else if (selectedPhase.equals(PhaseType.TRADE)) {
                    PhaseSelectorMenu.this.tradeSelectionMenu = new TradeSelectionMenu(PhaseSelectorMenu.this.player, PhaseSelectorMenu.this.controller.getBoardModel().getTradableTargets());
                    PhaseSelectorMenu.this.tradeSelectionMenu.getTradeSelectionPanel().getConfirmTradeButton().addActionListener(PhaseSelectorMenu.this.createActionListenerForTradeConfirmButton());
                } else if (selectedPhase.equals(PhaseType.ALERT)) {
                    PhaseSelectorMenu.this.alertSelectionMenu = new AlertSelectionMenu(PhaseSelectorMenu.this.player);
                    PhaseSelectorMenu.this.alertSelectionMenu.getAlertSelectionPanel().getConfirmButton().addActionListener(PhaseSelectorMenu.this.createActionListenerForAlertConfirmButton());
                } else if (selectedPhase.equals(PhaseType.REST)) {
                    // For now just use a dialog... much easier.
                    final List<ActionChit> chits = PhaseSelectorMenu.this.player.getCharacter().getActionChits();
                    ActionChit theChit = (ActionChit) JOptionPane.showInputDialog(
                            null,
                            "Choose a chit to rest: ",
                            "Rest action",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            chits.toArray(),
                            chits.get(0));
                    PhaseSelectorMenu.this.phaseSelectorModel.addRestPhase(theChit);
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

    public ActionListener createActionListenerForTradeConfirmButton() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Entity tradeTarget = PhaseSelectorMenu.this.tradeSelectionMenu.getTradeSelectionPanel().getEntitiesAvailableList().getSelectedValue();
                Item tradedItem = PhaseSelectorMenu.this.tradeSelectionMenu.getTradeSelectionPanel().getItemsAvailableList().getSelectedValue();
                boolean isSelling = PhaseSelectorMenu.this.tradeSelectionMenu.getTradeSelectionPanel().getSellRadioButton().isSelected();
                boolean isBuyingDrinks = PhaseSelectorMenu.this.tradeSelectionMenu.getTradeSelectionPanel().getBuyDrinksCheckBox().isSelected();

                PhaseSelectorMenu.this.phaseSelectorModel.addTradePhase(tradeTarget, tradedItem, isSelling, isBuyingDrinks, PhaseSelectorMenu.this.controller.getBoardModel().getClearingForPlayer(player));
                PhaseSelectorMenu.this.tradeSelectionMenu.dispose();
            }
        };
    }

    public ActionListener createActionListenerForAlertConfirmButton() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractWeapon alertedWeapon = PhaseSelectorMenu.this.alertSelectionMenu.getAlertSelectionPanel().getAlertableWeapons().getSelectedValue();
                PhaseSelectorMenu.this.phaseSelectorModel.addAlertPhase(alertedWeapon);
                PhaseSelectorMenu.this.alertSelectionMenu.dispose();
            }
        };
    }
}
