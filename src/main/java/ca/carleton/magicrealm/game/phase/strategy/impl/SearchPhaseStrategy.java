package ca.carleton.magicrealm.game.phase.strategy.impl;

import ca.carleton.magicrealm.GUI.phaseselector.detailwindows.SearchSelectionPanel;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.Path;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.chit.ColoredChit;
import ca.carleton.magicrealm.game.DiceRoller;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.game.phase.impl.SearchPhase;
import ca.carleton.magicrealm.game.phase.strategy.PhaseStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Tony on 02/04/2015.
 */
public class SearchPhaseStrategy implements PhaseStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(SearchPhaseStrategy.class);

    @Override
    public boolean appliesTo(final AbstractPhase phase) {
        return phase.getPhaseType() == PhaseType.SEARCH;
    }

    @Override
    public void doPhase(final Player player, final AbstractPhase phase) {
        final SearchPhase searchPhase = (SearchPhase) phase;

        int roll = DiceRoller.rollOnce();
        searchPhase.setRoll(roll);

        Clearing currentClearing = searchPhase.getBoard().getClearingForPlayer(player);

        switch (((SearchPhase) phase).getAction()) {
            case SearchSelectionPanel.LOCATE_TEXT:
                if (roll == 1 || roll == 4) {
                    for (ColoredChit chit : currentClearing.getParentTile().getChits()) {
                        if (chit.getClearingNumber() == Integer.parseInt(currentClearing.getName()))
                            player.getDiscoveredThings().add(chit);
                    }
                }
                if (roll == 1 || roll == 2 || roll == 3) {
                    this.discoverPath(currentClearing, player);
                }
                break;
            case SearchSelectionPanel.PEER_TEXT:
                if (roll == 1 || roll == 2 || roll == 3) {
                    this.discoverPath(currentClearing, player);
                }
                if (roll == 1 || roll == 3 || roll == 4) {
                    for (Entity entity : currentClearing.getEntities()) {
                        if (entity.isHidden())
                            player.getDiscoveredThings().add(entity);
                    }
                }

                break;
            case SearchSelectionPanel.LOOT_TEXT:
                break;
        }
    }

    private void discoverPath(final Clearing clearing, final Player player) {
        for (Path path : clearing.getAdjacentPaths()) {
            if (path.isHidden())
                if (!player.getDiscoveredThings().contains(path))
                    player.getDiscoveredThings().add(path);

        }
    }
}
