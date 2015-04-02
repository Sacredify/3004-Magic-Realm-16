package ca.carleton.magicrealm.game.phase.impl;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.phaseselector.detailwindows.SearchSelectionPanel;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.Path;
import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.chit.ColoredChit;
import ca.carleton.magicrealm.game.DiceRoller;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by Tony on 01/04/2015.
 */
public class SearchPhase extends AbstractPhase {

    private static final Logger LOG = LoggerFactory.getLogger(SearchPhase.class);

    private String action;

    @Override
    public PhaseType getPhaseType() {
        return PhaseType.SEARCH;
    }

    @Override
    public void updateFromBoard(final Player player, final BoardModel board) {
        int roll = DiceRoller.rollOnce();

        Clearing currentClearing = board.getClearingForPlayer(player);

        switch (action) {
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
                player.getDiscoveredThings().add(path);

        }
    }

    public void setAction(String action) {
        this.action = action;
    }
}
