package ca.carleton.magicrealm.GUI.board;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Tony on 12/02/2015.
 *
 * Window to display the game board
 */
public class BoardWindow extends JFrame{

    final static public String WINDOW_NAME = "Board";
    final static public int WINDOW_HEIGHT = 1000;
    final static public int WINDOW_WIDTH = 1000;

    private BoardPanel boardPanel;

    private BoardModel boardModel;

    public BoardWindow() {
        this.setName(WINDOW_NAME);
        this.setLayout(null);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        boardModel = new BoardModel();

        boardPanel = new BoardPanel();
        boardPanel.drawBoard(boardModel);

        JPanel container = new JPanel();
        container.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // TODO: get a friggen scrollbar working

        JScrollPane pane = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(pane);

        this.add(boardPanel);

        this.setVisible(true);
    }


}
