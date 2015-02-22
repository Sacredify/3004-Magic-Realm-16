package ca.carleton.magicrealm.GUI.infodialog;

import ca.carleton.magicrealm.GUI.board.BoardServices;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 3:57 PM
 */
public class InfoPanel extends JPanel {

    private JLabel entityLabel;

    private JList<Clearing> clearingList;

    private JList<Entity> entityList;

    private ArrayList<Clearing> clearings;

    private ArrayList<Entity> entities;

    BoardServices boardServices;

    public InfoPanel(final AbstractTile info) {

        this.setLayout(null);
        this.entityLabel = new JLabel();
        this.entityLabel.setSize(new Dimension(150, 150));
        this.entityLabel.setLocation(400, 10);
        this.entityLabel.setVisible(false);
        this.add(this.entityLabel);

        this.clearingList = new JList<>(info.getClearings());
        this.clearingList.setLocation(20, 40);
        this.clearingList.setSize(new Dimension(150, 110));
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
        this.entityList.setLocation(200, 40);
        this.entityList.setSize(new Dimension(150, 110));
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

        this.entityList.setListData(this.entities.toArray(new Entity[entities.size()]));
        this.entityList.setVisible(true);
    }

    public void entityListSelectedAction() {
        ImageIcon newIcon = this.boardServices.createImageIcon(entityList.getSelectedValue().getEntityInformation().getPath());
        entityLabel.setIcon(newIcon);
        entityLabel.setVisible(true);
    }

}
