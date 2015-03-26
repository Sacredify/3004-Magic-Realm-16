package ca.carleton.magicrealm.GUI.phaseselector.detailwindows;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;

import javax.swing.*;
import java.util.*;

/**
 * Created by Tony on 22/02/2015.
 */
public class MoveSelectionMenu extends JDialog {

    public static final String MOVE_SELECTION_WINDOW_NAME = "Where do you want to move to?";

    public static final int WINDOW_WIDTH = 300;

    public static final int WINDOW_HEIGHT = 200;

    private static final long serialVersionUID = 1521004700465700561L;

    private final MoveSelectionPanel moveSelectionPanel;

    public MoveSelectionMenu(final BoardModel board) {
        this.setTitle(MOVE_SELECTION_WINDOW_NAME);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        final List<Clearing> allClearings = new ArrayList<Clearing>();

        for (final AbstractTile tile : board.getAllTiles()) {
            allClearings.addAll(Arrays.asList(tile.getClearings()));
        }

        Collections.sort(allClearings, new Comparator<Clearing>() {
            @Override
            public int compare(final Clearing o1, final Clearing o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });

        this.moveSelectionPanel = new MoveSelectionPanel();
        this.moveSelectionPanel.setupClearingList(allClearings);
        this.add(this.moveSelectionPanel);
        this.setVisible(true);
    }

    public MoveSelectionPanel getMoveSelectionPanel() {
        return this.moveSelectionPanel;
    }

}
