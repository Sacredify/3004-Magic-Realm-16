package ca.carleton.magicrealm.GUI.phaseselector.detailwindows;

import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.item.Item;
import ca.carleton.magicrealm.item.weapon.AbstractWeapon;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Tony on 19/03/2015.
 */
public class AlertSelectionPanel extends JPanel{

    public static final String CONFIRM_BUTTON_TEXT = "Confirm";

    private JList<AbstractWeapon> alertableWeapons;

    private JButton confirmButton;

    public AlertSelectionPanel(final Player player) {
        this.alertableWeapons = new JList<>();
        ArrayList<AbstractWeapon> weapons = new ArrayList<>();
        for (Item item: player.getCharacter().getItems()) {
            if (item instanceof AbstractWeapon) {
                weapons.add((AbstractWeapon)item);
            }
        }
        this.alertableWeapons.setListData(weapons.toArray(new AbstractWeapon[weapons.size()]));

        this.add(alertableWeapons);

        this.confirmButton = new JButton(CONFIRM_BUTTON_TEXT);
        this.confirmButton.setSize(40,30);
        this.confirmButton.setEnabled(false);
        this.add(confirmButton);
    }

    public ListSelectionListener weaponSelectedListListener() {
        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                confirmButton.setEnabled(true);
            }
        };
    }

    public JList<AbstractWeapon> getAlertableWeapons() {
        return alertableWeapons;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }
}
