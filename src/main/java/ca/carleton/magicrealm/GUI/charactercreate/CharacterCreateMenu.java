package ca.carleton.magicrealm.GUI.charactercreate;

import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.VictoryCondition;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Dialog menu for choosing your character as well as assigning victory points.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 8:37 PM
 */
public class CharacterCreateMenu extends JFrame {

    private static final String WINDOW_NAME = "Character Creation";

    public static final int WINDOW_WIDTH = 650;

    public static final int WINDOW_HEIGHT = 200;

    MenuPanel view;

    MenuModel model;

    /**
     * Creates a new character create dialog.
     *
     * @param player the player this dialog is for.
     */
    public CharacterCreateMenu(final Player player, final List<CharacterType> availableCharacters) {
        super(WINDOW_NAME);
        this.initWindowSettings();
        this.model = new MenuModel(this, player, availableCharacters);
        this.view = new MenuPanel(this.model);
        this.setupListeners();
        this.add(this.view);
    }

    /**
     * Displays the dialog on screen.
     */
    public void displayWindow() {
        this.setVisible(true);
    }

    /**
     * Closes the dialog once done with settings.
     */
    public void disposeWindow() {
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Initialize settings for this frame.
     */
    private void initWindowSettings() {
        this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }

    /**
     * Setup listeners for this dialog.
     */
    private void setupListeners() {
        // List
        this.view.characterSelectList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(final ListSelectionEvent e) {
                CharacterCreateMenu.this.model.selectedCharacter = CharacterCreateMenu.this.view.characterSelectList.getSelectedValue();
                CharacterCreateMenu.this.view.setIncrementKeysEnabled(true);
            }
        });

        // Decrement keys
        this.view.victoryPointsButtons[0][0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CharacterCreateMenu.this.model.decreaseVictoryCondition(VictoryCondition.Conditions.GOLD);
            }
        });
        this.view.victoryPointsButtons[1][0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CharacterCreateMenu.this.model.decreaseVictoryCondition(VictoryCondition.Conditions.NOTORIETY);
            }
        });
        this.view.victoryPointsButtons[2][0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CharacterCreateMenu.this.model.decreaseVictoryCondition(VictoryCondition.Conditions.FAME);
            }
        });
        this.view.victoryPointsButtons[3][0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CharacterCreateMenu.this.model.decreaseVictoryCondition(VictoryCondition.Conditions.SPELLS_COUNT);
            }
        });
        this.view.victoryPointsButtons[4][0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CharacterCreateMenu.this.model.decreaseVictoryCondition(VictoryCondition.Conditions.GREAT_TREASURES_COUNT);
            }
        });

        // Increment Keys
        this.view.victoryPointsButtons[0][1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CharacterCreateMenu.this.model.increaseVictoryCondition(VictoryCondition.Conditions.GOLD);
            }
        });
        this.view.victoryPointsButtons[1][1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CharacterCreateMenu.this.model.increaseVictoryCondition(VictoryCondition.Conditions.NOTORIETY);
            }
        });
        this.view.victoryPointsButtons[2][1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CharacterCreateMenu.this.model.increaseVictoryCondition(VictoryCondition.Conditions.FAME);
            }
        });
        this.view.victoryPointsButtons[3][1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CharacterCreateMenu.this.model.increaseVictoryCondition(VictoryCondition.Conditions.SPELLS_COUNT);
            }
        });
        this.view.victoryPointsButtons[4][1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                CharacterCreateMenu.this.model.increaseVictoryCondition(VictoryCondition.Conditions.GREAT_TREASURES_COUNT);
            }
        });

        // Finish
        this.view.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (JOptionPane.showConfirmDialog(CharacterCreateMenu.this, "Are you sure these are your character details?") == JOptionPane.YES_OPTION) {
                    CharacterCreateMenu.this.model.done();
                }
            }
        });
    }

}
