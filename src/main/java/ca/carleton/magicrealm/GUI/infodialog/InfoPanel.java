package ca.carleton.magicrealm.GUI.infodialog;

import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 3:57 PM
 */
public class InfoPanel extends JPanel {

    private JLabel listInfoLabel;

    private JList entityList;

    public InfoPanel(final Clearing info) {

        this.setLayout(null);
        this.listInfoLabel = new JLabel("Items currently on this clearing:");
        this.listInfoLabel.setSize(new Dimension(150, 20));
        this.listInfoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.listInfoLabel.setLocation(10, 10);
        this.listInfoLabel.setVisible(true);
        this.add(this.listInfoLabel);


        this.entityList = new JList<Entity>(info.getEntities().toArray(new Entity[info.getEntities().size()]));
        this.entityList.setLocation(20, 40);
        this.entityList.setSize(new Dimension(150, 110));
        this.entityList.setEnabled(false);
        this.entityList.setVisible(true);
        this.add(this.entityList);

    }

}
