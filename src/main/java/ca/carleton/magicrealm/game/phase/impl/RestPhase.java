package ca.carleton.magicrealm.game.phase.impl;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.PhaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 30/03/2015
 * Time: 9:00 AM
 */
public class RestPhase extends AbstractPhase {

    private static final Logger LOG = LoggerFactory.getLogger(RestPhase.class);

    private ActionChit selectedChit;

    @Override
    public PhaseType getPhaseType() {
        return PhaseType.REST;
    }

    @Override
    public void updateFromBoard(final Player player, final BoardModel board) {
        player.getCharacter().getActionChits().stream().filter(this.selectedChit::equals).forEach(actionChit -> {
                    this.selectedChit = actionChit;
                    LOG.info("Updated selected chit to rest.");
                }
        );
    }

    public ActionChit getSelectedChit() {
        return this.selectedChit;
    }

    public void setSelectedChit(final ActionChit actionChit) {
        this.selectedChit = actionChit;
    }
}
