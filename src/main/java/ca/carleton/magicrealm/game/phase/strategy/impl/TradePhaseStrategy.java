package ca.carleton.magicrealm.game.phase.strategy.impl;

import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.game.phase.impl.TradePhase;
import ca.carleton.magicrealm.game.phase.strategy.PhaseStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 11/03/15
 * Time: 4:43 PM
 */
public class TradePhaseStrategy implements PhaseStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(TradePhaseStrategy.class);

    @Override
    public boolean appliesTo(final AbstractPhase phase) {
        return phase.getPhaseType() == PhaseType.TRADE;
    }

    @Override
    public void doPhase(final Player player, final AbstractPhase phase) {
        final TradePhase trade = (TradePhase) phase;

        // If we're selling our item to the native.
        if (trade.isSelling()) {
            player.getCharacter().removeItem(trade.getItemToTrade());
            player.getCharacter().addGold(trade.getItemToTrade().getGoldValue());
            trade.getTradeTarget().addItem(trade.getItemToTrade());
            LOG.info("Removed {} from {}'s inventory.", trade.getItemToTrade(), player.getCharacter().getEntityInformation().convertToCharacterType());
            LOG.info("Added {} to {}'s inventory.", trade.getItemToTrade(), trade.getTradeTarget().getEntityInformation());
        } else {
            trade.getTradeTarget().removeItem(trade.getItemToTrade());
            // If another player, remove gold from the player.
            if (trade.getTradeTarget() instanceof AbstractCharacter) {
                ((AbstractCharacter) trade.getTradeTarget()).addGold(trade.getItemToTrade().getGoldValue());
            }
            player.getCharacter().addItem(trade.getItemToTrade());
            player.getCharacter().addGold(-trade.getItemToTrade().getGoldValue());
            LOG.info("Added {} to {}'s inventory.", trade.getItemToTrade(), player.getCharacter().getEntityInformation().convertToCharacterType());
            LOG.info("Removed {} to {}'s inventory.", trade.getItemToTrade(), trade.getTradeTarget().getEntityInformation());
        }

    }
}
