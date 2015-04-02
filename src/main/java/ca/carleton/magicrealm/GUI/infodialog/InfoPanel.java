package ca.carleton.magicrealm.GUI.infodialog;

import ca.carleton.magicrealm.GUI.board.BoardServices;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.chit.GoldChit;

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
            if (chit instanceof GoldChit) {
                chitsAndTreasures.add(chit);
                ((GoldChit) chit).getTreasure().stream().forEach(treasure -> chitsAndTreasures.add("    " + treasure));
            } else {
                chitsAndTreasures.add(chit);
            }
        });

        final JList<Object> chits = new JList<Object>(chitsAndTreasures.toArray(new Object[chitsAndTreasures.size()]));
        chits.setLocation(20, 40);
        chits.setSize(new Dimension(250, 200));
        chits.setEnabled(false);
        chits.setVisible(true);
        this.add(chits);

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
