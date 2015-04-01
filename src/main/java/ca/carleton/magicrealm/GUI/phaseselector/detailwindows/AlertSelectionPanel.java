package ca.carleton.magicrealm.GUI.phaseselector.detailwindows;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.item.ItemInformation;
import ca.carleton.magicrealm.item.weapon.AbstractWeapon;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Panel for alerting weapons.
 *
 * Created by Tony on 19/03/2015.
 */
public class AlertSelectionPanel extends JPanel{

    public static final String CONFIRM_BUTTON_TEXT = "Confirm";

    private static final long serialVersionUID = -2050747007758552829L;

    private final JList<AbstractWeapon> alertableWeapons;

    private final JButton confirmButton;

    public AlertSelectionPanel(final Player player) {
        this.alertableWeapons = new JList<>();
        List<AbstractWeapon> weapons = player.getCharacter().getItems().stream().filter(item -> item instanceof AbstractWeapon).map(item -> (AbstractWeapon) item).collect(Collectors.toList());

        if (player.getCharacter().getEntityInformation() == EntityInformation.CHARACTER_BERSERKER) {
            // Add in a pseudo-weapon for chit.
            weapons.add(new AbstractWeapon() {
                @Override
                public ItemInformation getItemInformation() {
                    return ItemInformation.BERSERKER_BERSERK;
                }
            });
        }

        this.alertableWeapons.setListData(weapons.toArray(new AbstractWeapon[weapons.size()]));

        this.add(this.alertableWeapons);

        this.confirmButton = new JButton(CONFIRM_BUTTON_TEXT);
        this.confirmButton.setSize(40,30);
        this.confirmButton.setEnabled(false);
        this.add(this.confirmButton);
    }

    public ListSelectionListener weaponSelectedListListener() {
        return e -> AlertSelectionPanel.this.confirmButton.setEnabled(true);
    }

    public JList<AbstractWeapon> getAlertableWeapons() {
        return this.alertableWeapons;
    }

    public JButton getConfirmButton() {
        return this.confirmButton;
    }
}
