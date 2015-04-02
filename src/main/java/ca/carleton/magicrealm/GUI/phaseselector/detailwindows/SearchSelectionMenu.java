package ca.carleton.magicrealm.GUI.phaseselector.detailwindows;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.game.Player;

import javax.swing.*;

/**
 * Created by Tony on 01/04/2015.
 */
public class SearchSelectionMenu extends JDialog{

    public static final String SEARCH_SELECTION_WINDOW_NAME = "Search for paths and treasures";

    public static final int WINDOW_WIDTH = 300;

    public static final int WINDOW_HEIGHT = 200;

    private SearchSelectionPanel searchSelectionPanel;

    public SearchSelectionMenu() {
        this.setTitle(SEARCH_SELECTION_WINDOW_NAME);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        this.searchSelectionPanel = new SearchSelectionPanel();
        this.add(this.searchSelectionPanel);
        this.setVisible(true);
    }

    public SearchSelectionPanel getSearchSelectionPanel() {
        return searchSelectionPanel;
    }
}
