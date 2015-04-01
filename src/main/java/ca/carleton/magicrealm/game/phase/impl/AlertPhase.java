package ca.carleton.magicrealm.game.phase.impl;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.item.weapon.AbstractWeapon;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 15/03/15
 * Time: 11:57 PM
 */
public class AlertPhase extends AbstractPhase {

    private static final Logger LOG = LoggerFactory.getLogger(AlertPhase.class);

    private AbstractWeapon weapon;

    @Override
    public PhaseType getPhaseType() {
        return PhaseType.ALERT;
    }

    @Override
    public void updateFromBoard(final Player player, final BoardModel board) {
        final AbstractWeapon update = (AbstractWeapon) CollectionUtils.find(player.getCharacter().getItems(),
                object -> AlertPhase.this.weapon.toString().equals(object.toString()));

        if (update != null) {
            this.weapon = update;
            LOG.info("Updated alert weapon.");
        }
    }

    public AbstractWeapon getWeapon() {
        return this.weapon;
    }

    public void setWeapon(final AbstractWeapon weapon) {
        this.weapon = weapon;
    }
}
