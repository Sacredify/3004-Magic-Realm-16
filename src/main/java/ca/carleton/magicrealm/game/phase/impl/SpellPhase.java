package ca.carleton.magicrealm.game.phase.impl;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 02/04/2015
 * Time: 2:30 AM
 */
public class SpellPhase extends AbstractPhase {

    private static final Logger LOG = LoggerFactory.getLogger(SpellPhase.class);

    private AbstractTile tileToEnchant;

    @Override
    public PhaseType getPhaseType() {
        return PhaseType.SPELL;
    }

    @Override
    public void updateFromBoard(final Player player, final BoardModel board) {
        board.getAllTiles().stream().filter(tile -> tile.getTileInformation() == this.tileToEnchant.getTileInformation()).forEach(tile -> {
            this.tileToEnchant = tile;
            LOG.info("Updated tile to enchant from board.");
        });
    }

    public AbstractTile getTileToEnchant() {
        return this.tileToEnchant;
    }

    public void setTileToEnchant(final AbstractTile tile) {
        this.tileToEnchant = tile;
    }
}
