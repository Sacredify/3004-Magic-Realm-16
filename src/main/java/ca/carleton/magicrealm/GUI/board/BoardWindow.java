package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.tile.Clearing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Tony on 12/02/2015.
 * <p/>
 * Window to display the game board
 */
public class BoardWindow extends JFrame {

    final static public String WINDOW_NAME = "Board";
    final static public int WINDOW_HEIGHT = 1000;
    final static public int WINDOW_WIDTH = 1000;

    private BoardPanel boardPanel;

    private BoardGUIModel boardGUIModel;

    private JMenuBar menuBar;

    public BoardWindow() {
        super(WINDOW_NAME);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        boardGUIModel = new BoardGUIModel();

        boardPanel = new BoardPanel();
        boardPanel.drawBoard(boardGUIModel);

        // TODO: get a friggen scrollbar working
        JScrollPane pane = new JScrollPane(boardPanel);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(this.boardPanel, BorderLayout.NORTH);
        this.add(pane, BorderLayout.CENTER);
        this.setupMenuBars();

        this.pack();

        this.setVisible(true);
    }

    private void setupMenuBars() {
        this.menuBar = new JMenuBar();

        final JMenu networkMenu = new JMenu("Network");
        networkMenu.getAccessibleContext().setAccessibleDescription("Perform network operations.");

        final JMenuItem connect = new JMenuItem("Connect");
        connect.getAccessibleContext().setAccessibleDescription("Connect to a given ip address.");
        networkMenu.add(connect);

        this.menuBar.add(networkMenu);

        this.setJMenuBar(this.menuBar);
    }

    public void setupMoveButtons(ArrayList<JButton> buttons) {
        this.boardPanel.displayMoveButtonsForClearing(buttons);
    }


}
