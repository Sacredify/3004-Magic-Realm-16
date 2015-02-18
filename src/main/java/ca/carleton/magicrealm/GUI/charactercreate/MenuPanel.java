package ca.carleton.magicrealm.GUI.charactercreate;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 8:51 PM
 */
public class MenuPanel extends JPanel {

    JButton submitButton;

    JButton[][] victoryPointsButtons;

    JLabel[] victoryLabels;

    private MenuModel model;

    public MenuPanel(final MenuModel model) {
        this.setLayout(null);

        this.model = model;
        this.initializeLabels();
    }

    private void initializeLabels() {
        this.victoryLabels = new JLabel[5];

        for (int i = 0; i < this.victoryLabels.length; i++) {
            this.victoryLabels[i] = new JLabel();
            this.victoryLabels[i].setSize(new Dimension(100, 20));
            this.victoryLabels[i].setLocation(100, 50 + (i * 20));
            this.victoryLabels[i].setVisible(true);
        }

        this.victoryLabels[0].setText("Gold");
        this.victoryLabels[1].setText("Notoriety");
        this.victoryLabels[2].setText("Fame");
        this.victoryLabels[3].setText("Spells");
        this.victoryLabels[4].setText("Great Treasures");

    }

}
