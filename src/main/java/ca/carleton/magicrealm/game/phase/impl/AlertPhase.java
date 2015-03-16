package ca.carleton.magicrealm.game.phase.impl;

import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.item.weapon.AbstractWeapon;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * Created with IntelliJ IDEA.
 * Date: 15/03/15
 * Time: 11:57 PM
 */
public class AlertPhase extends AbstractPhase {

    private AbstractWeapon weapon;

    @Override
    public PhaseType getPhaseType() {
        return PhaseType.ALERT;
    }

    @Override
    public void updateFromBoard(final Player player, final BoardGUIModel board) {
        this.weapon = (AbstractWeapon) CollectionUtils.find(player.getCharacter().getItems(), new Predicate() {
            @Override
            public boolean evaluate(final Object object) {
                return AlertPhase.this.weapon.toString().equals(object.toString());
            }
        });
    }

    public AbstractWeapon getWeapon() {
        return this.weapon;
    }

    public void setWeapon(final AbstractWeapon weapon) {
        this.weapon = weapon;
    }
}
