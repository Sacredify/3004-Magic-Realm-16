package ca.carleton.magicrealm.GUI.infodialog;

import ca.carleton.magicrealm.GUI.board.BoardServices;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.chit.GoldChit;
import ca.carleton.magicrealm.entity.chit.LostCastle;
import ca.carleton.magicrealm.entity.chit.LostCity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 3:57 PM
 */
public class InfoPanel extends JPanel {

    private final JLabel entityLabel;

    private final JList<Clearing> clearingList;

    private final JList<Entity> entityList;

    BoardServices boardServices;

    public InfoPanel(final AbstractTile info) {

        this.setLayout(null);
        this.entityLabel = new JLabel();
        this.entityLabel.setSize(new Dimension(250, 20));
        this.entityLabel.setLocation(580, 10);
        this.entityLabel.setVisible(true);
        this.entityLabel.setText("Entities on clearing: ");
        this.add(this.entityLabel);

        final JLabel clearingLabel = new JLabel();
        clearingLabel.setSize(new Dimension(250, 20));
        clearingLabel.setLocation(300, 10);
        clearingLabel.setVisible(true);
        clearingLabel.setText("Clearings on tile: ");
        this.add(clearingLabel);

        final JLabel chitsLabel = new JLabel();
        chitsLabel.setSize(new Dimension(250, 20));
        chitsLabel.setLocation(20, 10);
        chitsLabel.setVisible(true);
        chitsLabel.setText("Chits on tile: ");
        this.add(chitsLabel);

        final java.util.List<Object> chitsAndTreasures = new ArrayList<Object>();
        info.getChits().stream().forEach(chit -> {
            chitsAndTreasures.add(chit);
            if (chit instanceof GoldChit) {
                ((GoldChit) chit).getTreasure().stream().forEach(treasure -> chitsAndTreasures.add("    " + treasure));
            } else if (chit instanceof LostCity) {
                ((LostCity) chit).soundChits.forEach(sound -> chitsAndTreasures.add("    " + sound));
                ((LostCity) chit).treasureChits.forEach(treasure ->
                {
                    chitsAndTreasures.add("    " + treasure);
                    treasure.getTreasure().stream().forEach(item -> chitsAndTreasures.add("        " + item));
                });
            } else if (chit instanceof LostCastle) {
                ((LostCastle) chit).soundChits.forEach(sound -> chitsAndTreasures.add("    " + sound));
                ((LostCastle) chit).treasureChits.forEach(treasure ->
                {
                    chitsAndTreasures.add("    " + treasure);
                    treasure.getTreasure().stream().forEach(item -> chitsAndTreasures.add("        " + item));
                });
            }
        });

        final JList<Object> chits = new JList<Object>(chitsAndTreasures.toArray(new Object[chitsAndTreasures.size()]));
        final JScrollPane scrollPane = new JScrollPane(chits);
        scrollPane.setLocation(20, 40);
        scrollPane.setSize(new Dimension(250, 200));
        scrollPane.setEnabled(false);
        scrollPane.setVisible(true);
        this.add(scrollPane);

        this.clearingList = new JList<>(info.getClearings());
        this.clearingList.setLocation(300, 40);
        this.clearingList.setSize(new Dimension(250, 200));
        this.clearingList.setEnabled(true);
        this.clearingList.setVisible(true);
        this.clearingList.addListSelectionListener(e -> InfoPanel.this.clearingsListSelectedAction());
        this.add(this.clearingList);

        this.entityList = new JList<>();
        this.entityList.setLocation(580, 40);
        this.entityList.setSize(new Dimension(250, 200));
        this.entityList.setEnabled(false);
        this.entityList.setVisible(false);
        this.entityList.addListSelectionListener(e -> InfoPanel.this.entityListSelectedAction());
        this.add(this.entityList);

        this.setVisible(true);
    }

    public void clearingsListSelectedAction() {
        final ArrayList<Entity> entities1 = new ArrayList<>();
        entities1.addAll(this.clearingList.getSelectedValue().getEntities());

        Entity[] entities = entities1.toArray(new Entity[entities1.size()]);

        this.entityList.setListData(entities);
        this.entityList.setVisible(true);
    }

    public void entityListSelectedAction() {
        ImageIcon newIcon = this.boardServices.createImageIcon(this.entityList.getSelectedValue().getEntityInformation().getPath());
        this.entityLabel.setIcon(newIcon);
        this.entityLabel.setVisible(true);
    }

}
