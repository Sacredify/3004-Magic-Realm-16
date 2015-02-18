package ca.carleton.magicrealm.GUI.board;

import javax.swing.*;

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

    private JFrame boardFrame;

    public BoardWindow() {
        boardFrame = new JFrame(WINDOW_NAME);
        boardFrame.setLayout(null);
        boardFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        boardFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        boardModel = new BoardModel();

        boardPanel = new BoardPanel();
        boardPanel.drawBoard(boardModel);
        
        boardFrame.add(boardPanel);

        boardFrame.setVisible(true);
    }


}
