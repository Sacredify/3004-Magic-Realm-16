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

    private BoardModel board;

    private int roll;

    @Override
    public PhaseType getPhaseType() {
        return PhaseType.SEARCH;
    }

    @Override
    public void updateFromBoard(final Player player, final BoardModel board) {
        this.board = board;

    }

    public BoardModel getBoard() {
        return board;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }
}
