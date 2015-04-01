package ca.carleton.magicrealm.game.phase.strategy.impl;

import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.chit.ActionType;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.game.phase.impl.AlertPhase;
import ca.carleton.magicrealm.game.phase.strategy.PhaseStrategy;
import ca.carleton.magicrealm.item.ItemInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 15/03/15
 * Time: 11:59 PM
 */
public class AlertPhaseStrategy implements PhaseStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(AlertPhaseStrategy.class);

    @Override
    public boolean appliesTo(final AbstractPhase phase) {
        return phase.getPhaseType() == PhaseType.ALERT;
    }

    @Override
    public void doPhase(final Player player, final AbstractPhase phase) {
        final AlertPhase alert = (AlertPhase) phase;

        if (alert.getWeapon().getItemInformation() == ItemInformation.BERSERKER_BERSERK) {
            LOG.info("Berserker played his berserk chit during alert phase! Updating his health and fatiguing chit.");
            player.getCharacter().getActionChits().stream()
                    .filter(chit -> chit.getAction() == ActionType.BERSERK)
                    .forEach(chit -> chit.setFatigued(true));
        } else {
            alert.getWeapon().setAlert(true);
            LOG.info("Alerted {}'s {}.", player.getCharacter().getEntityInformation().convertToCharacterType(), alert.getWeapon());
        }
    }
}
