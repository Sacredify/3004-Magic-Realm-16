package ca.carleton.magicrealm.GUI.infodialog;

import ca.carleton.magicrealm.GUI.board.BoardServices;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.chit.ColoredChit;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 3:57 PM
 */
public class InfoPanel extends JPanel {

    private JLabel entityLabel;

    private JLabel clearingLabel;

    private JLabel chitsLabel;

    private JList<Clearing> clearingList;

    private JList<Entity> entityList;

    private JList<ColoredChit> chits;

    private ArrayList<Clearing> clearings;

    private ArrayList<Entity> entities;

    BoardServices boardServices;

    public InfoPanel(final AbstractTile info) {

        this.setLayout(null);
        this.entityLabel = new JLabel();
        this.entityLabel.setSize(new Dimension(250, 20));
        this.entityLabel.setLocation(580, 10);
        this.entityLabel.setVisible(true);
        this.entityLabel.setText("Entities on clearing: ");
        this.add(this.entityLabel);

        this.clearingLabel = new JLabel();
        this.clearingLabel.setSize(new Dimension(250, 20));
        this.clearingLabel.setLocation(300, 10);
        this.clearingLabel.setVisible(true);
        this.clearingLabel.setText("Clearings on tile: ");
        this.add(this.clearingLabel);

        this.chitsLabel = new JLabel();
        this.chitsLabel.setSize(new Dimension(250, 20));
        this.chitsLabel.setLocation(20, 10);
        this.chitsLabel.setVisible(true);
        this.chitsLabel.setText("Chits on tile: ");
        this.add(this.chitsLabel);

        this.chits = new JList<>(info.getChits().toArray(new ColoredChit[info.getChits().size()]));
        this.chits.setLocation(20, 40);
        this.chits.setSize(new Dimension(250, 200));
        this.chits.setEnabled(false);
        this.chits.setVisible(true);
        this.add(this.chits);

        this.clearingList = new JList<>(info.getClearings());
        this.clearingList.setLocation(300, 40);
        this.clearingList.setSize(new Dimension(250, 200));
        this.clearingList.setEnabled(true);
        this.clearingList.setVisible(true);
        this.clearingList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                clearingsListSelectedAction();
            }
        });
        this.add(this.clearingList);

        this.entityList = new JList<>();
        this.entityList.setLocation(580, 40);
        this.entityList.setSize(new Dimension(250, 200));
        this.entityList.setEnabled(false);
        this.entityList.setVisible(false);
        this.entityList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                entityListSelectedAction();
            }
        });
        this.add(this.entityList);

        this.setVisible(true);
    }

    public void clearingsListSelectedAction() {
        this.entities = new ArrayList<>();
        this.entities.addAll(clearingList.getSelectedValue().getEntities());

        Entity[] entities = this.entities.toArray(new Entity[this.entities.size()]);

        this.entityList.setListData(entities);
        this.entityList.setVisible(true);
    }

    public void entityListSelectedAction() {
        ImageIcon newIcon = this.boardServices.createImageIcon(entityList.getSelectedValue().getEntityInformation().getPath());
        entityLabel.setIcon(newIcon);
        entityLabel.setVisible(true);
    }

}
