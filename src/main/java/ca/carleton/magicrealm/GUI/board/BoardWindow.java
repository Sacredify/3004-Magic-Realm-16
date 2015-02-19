package ca.carleton.magicrealm.GUI.board;

import javax.swing.*;
import java.awt.*;

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

    private BoardModel boardModel;

    private JMenuBar menuBar;

    public BoardWindow() {
        super(WINDOW_NAME);
        this.setLayout(null);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.boardModel = new BoardModel();

        this.boardPanel = new BoardPanel();
        this.boardPanel.drawBoard(this.boardModel);

        JPanel container = new JPanel();
        container.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // TODO: get a friggen scrollbar working

        JScrollPane pane = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(pane);

        this.add(this.boardPanel);
        this.setupMenuBars();

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


}
