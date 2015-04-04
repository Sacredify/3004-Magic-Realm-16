package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.entity.character.AbstractCharacter;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Tony on 12/02/2015.
 * <p>
 * Window to display the game board
 */
public class BoardWindow extends JFrame {

    final static public String WINDOW_NAME = "Team 16 - Magic Realm";
    final static public int WINDOW_HEIGHT = 1050;
    final static public int WINDOW_WIDTH = 1000;

    private final BoardPanel boardPanel;

    private BoardModel boardModel;

    public BoardWindow() {
        super(WINDOW_NAME);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.boardPanel = new BoardPanel();
        this.add(this.boardPanel, BorderLayout.CENTER);

        this.pack();

        this.setVisible(true);
    }

    public void refresh(final BoardModel model, final AbstractCharacter character, final boolean isSunset) {
        this.boardModel = model;
        this.boardPanel.drawBoard(this.boardModel, character, isSunset);
        this.add(this.boardPanel);
    }

    public void setupStatusLabel() {
        this.boardPanel.setupStatusLabel();
    }

    public void setStatusText(final String text) {
        this.boardPanel.setStatusText(text);
    }

    public BoardModel getBoardModel() {
        return this.boardModel;
    }

    public void showStatusLabel() {
        boardPanel.showStatusLabel();
    }

    public void hideStatusLabel() {
        boardPanel.hideStatusLabel();
    }

    public void setGameInfoText(String text) {
        this.boardPanel.setGameInfoText(text);
    }

}
