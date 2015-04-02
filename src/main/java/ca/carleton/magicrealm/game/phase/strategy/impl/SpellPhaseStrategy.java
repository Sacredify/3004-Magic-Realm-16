package ca.carleton.magicrealm.game.phase.strategy.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.game.phase.impl.SpellPhase;
import ca.carleton.magicrealm.game.phase.strategy.PhaseStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enchants and un-enchants tiles.
 *
 * Created with IntelliJ IDEA.
 * Date: 02/04/2015
 * Time: 2:33 AM
 */
public class SpellPhaseStrategy implements PhaseStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(SpellPhaseStrategy.class);

    @Override
    public boolean appliesTo(final AbstractPhase phase) {
        return phase.getPhaseType() == PhaseType.SPELL;
    }

    @Override
    public void doPhase(final Player player, final AbstractPhase phase) {
        final SpellPhase spellPhase = (SpellPhase) phase;
        final AbstractTile toEnchant = spellPhase.getBoard().getClearingForPlayer(player).getParentTile();
        toEnchant.toggleEnchanted();
        LOG.info("{} now enchanted: {}.", toEnchant, toEnchant.isEnchanted());
    }
}
