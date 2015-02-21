package ca.carleton.magicrealm.GUI.charactercreate;

import ca.carleton.magicrealm.entity.character.CharacterType;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 8:51 PM
 */
public class MenuPanel extends JPanel {

    private static final String FORMAT_STRING = "Remaining points: %d";

    JButton submitButton;

    JTextField[] victoryPointsFields;

    JButton[][] victoryPointsButtons;

    JLabel[] victoryLabels;

    JLabel remainingPointsLabel;

    JLabel listInfoLabel;

    JLabel pointsInfoLabel;

    JList<CharacterType> characterSelectList;

    private MenuModel model;

    public MenuPanel(final MenuModel model) {
        this.setLayout(null);

        this.model = model;
        this.initializeLabels();
        this.initializeVictoryButtons();
        this.initializeVictoryFields();
        this.initializeList();
        this.initializeSubmit();
    }

    public void updateList() {

        DefaultListModel<CharacterType> dataModel = new DefaultListModel<>();

        for (final CharacterType characterType : this.model.availableCharacters) {
            dataModel.addElement(characterType);
        }

        this.characterSelectList.setModel(dataModel);
    }

    public void setFinishEnabled(final boolean status) {
        this.submitButton.setEnabled(status);
    }

    public void setDecrementKeysEnabled(final boolean status) {
        this.victoryPointsButtons[0][0].setEnabled(status);
        this.victoryPointsButtons[1][0].setEnabled(status);
        this.victoryPointsButtons[2][0].setEnabled(status);
        this.victoryPointsButtons[3][0].setEnabled(status);
        this.victoryPointsButtons[4][0].setEnabled(status);
    }

    public void setIncrementKeysEnabled(final boolean status) {
        this.victoryPointsButtons[0][1].setEnabled(status);
        this.victoryPointsButtons[1][1].setEnabled(status);
        this.victoryPointsButtons[2][1].setEnabled(status);
        this.victoryPointsButtons[3][1].setEnabled(status);
        this.victoryPointsButtons[4][1].setEnabled(status);
    }

    public void checkIndividualButtons() {
        // Need to check each individual condition for increment/decrement
        if (this.model.player.getVictoryCondition().getGold() == 0) {
            this.victoryPointsButtons[0][0].setEnabled(false);
        }
        if (this.model.player.getVictoryCondition().getNotoriety() == 0) {
            this.victoryPointsButtons[1][0].setEnabled(false);
        }
        if (this.model.player.getVictoryCondition().getFame() == 0) {
            this.victoryPointsButtons[2][0].setEnabled(false);
        }
        if (this.model.player.getVictoryCondition().getSpellsCount() == 0) {
            this.victoryPointsButtons[3][0].setEnabled(false);
        }
        if (this.model.player.getVictoryCondition().getGreatTreasuresCount() == 0) {
            this.victoryPointsButtons[4][0].setEnabled(false);
        }
    }

    public void updateText() {
        this.remainingPointsLabel.setText(String.format(FORMAT_STRING, this.model.pointsLeft));
        this.victoryPointsFields[0].setText(String.valueOf(this.model.player.getVictoryCondition().getGold()));
        this.victoryPointsFields[1].setText(String.valueOf(this.model.player.getVictoryCondition().getNotoriety()));
        this.victoryPointsFields[2].setText(String.valueOf(this.model.player.getVictoryCondition().getFame()));
        this.victoryPointsFields[3].setText(String.valueOf(this.model.player.getVictoryCondition().getSpellsCount()));
        this.victoryPointsFields[4].setText(String.valueOf(this.model.player.getVictoryCondition().getGreatTreasuresCount()));
    }

    private void initializeSubmit() {
        this.submitButton = new JButton("Finish");
        this.submitButton.setSize(150, 100);
        this.submitButton.setLocation(475, 40);
        this.submitButton.setVisible(true);
        this.submitButton.setEnabled(false);
        this.add(this.submitButton);
    }

    private void initializeList() {
        this.characterSelectList = new JList<CharacterType>(this.model.availableCharacters.toArray(new CharacterType[this.model.availableCharacters.size()]));
        this.characterSelectList.setLocation(20, 40);
        this.characterSelectList.setSize(new Dimension(150, 110));
        this.characterSelectList.setToolTipText("Select a character.");
        this.characterSelectList.setVisible(true);
        this.add(this.characterSelectList);
    }

    private void initializeLabels() {
        this.victoryLabels = new JLabel[5];

        for (int i = 0; i < this.victoryLabels.length; i++) {
            this.victoryLabels[i] = new JLabel();
            this.victoryLabels[i].setSize(new Dimension(100, 20));
            this.victoryLabels[i].setLocation(180, 40 + (i * 20));
            this.victoryLabels[i].setVisible(true);
            this.victoryLabels[i].setHorizontalAlignment(SwingConstants.RIGHT);
            this.add(this.victoryLabels[i]);
        }

        this.victoryLabels[0].setText("Gold");
        this.victoryLabels[1].setText("Notoriety");
        this.victoryLabels[2].setText("Fame");
        this.victoryLabels[3].setText("Spells");
        this.victoryLabels[4].setText("Great Treasures");

        this.remainingPointsLabel = new JLabel("Remaining points: 5");
        this.remainingPointsLabel.setSize(new Dimension(150, 20));
        this.remainingPointsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        this.remainingPointsLabel.setLocation(290, 140);
        this.remainingPointsLabel.setVisible(true);
        this.add(this.remainingPointsLabel);

        this.listInfoLabel = new JLabel("1. Choose your character:");
        this.listInfoLabel.setSize(new Dimension(150, 20));
        this.listInfoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.listInfoLabel.setLocation(20, 10);
        this.listInfoLabel.setVisible(true);
        this.add(this.listInfoLabel);

        this.pointsInfoLabel = new JLabel("2. Allocate your victory points:");
        this.pointsInfoLabel.setSize(new Dimension(180, 20));
        this.pointsInfoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.pointsInfoLabel.setLocation(265, 10);
        this.pointsInfoLabel.setVisible(true);
        this.add(this.pointsInfoLabel);


    }

    private void initializeVictoryFields() {
        this.victoryPointsFields = new JTextField[5];

        for (int i = 0; i < this.victoryPointsFields.length; i++) {
            this.victoryPointsFields[i] = new JTextField();
            this.victoryPointsFields[i].setSize(new Dimension(45, 20));
            this.victoryPointsFields[i].setLocation(345, 40 + (i * 20));
            this.victoryPointsFields[i].setVisible(true);
            this.victoryPointsFields[i].setEnabled(false);
            this.victoryPointsFields[i].setHorizontalAlignment(SwingConstants.CENTER);
            this.victoryPointsFields[i].setText("0");
            this.add(this.victoryPointsFields[i]);
        }
    }

    private void initializeVictoryButtons() {
        this.victoryPointsButtons = new JButton[5][2];

        for (int i = 0; i < this.victoryPointsButtons.length; i++) {
            for (int j = 0; j < this.victoryPointsButtons[i].length; j++) {
                this.victoryPointsButtons[i][j] = new JButton();
                if (j == 0) {
                    this.victoryPointsButtons[i][j].setText("<");
                } else {
                    this.victoryPointsButtons[i][j].setText(">");
                }

                this.victoryPointsButtons[i][j].setSize(new Dimension(45, 20));
                this.victoryPointsButtons[i][j].setLocation(295 + (j * 100), 40 + (i * 20));
                this.victoryPointsButtons[i][j].setVisible(true);
                this.victoryPointsButtons[i][j].setEnabled(false);
                this.add(this.victoryPointsButtons[i][j]);

            }
        }
    }
}
