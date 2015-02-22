package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.game.Player;

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
    final static public int WINDOW_HEIGHT = 700;
    final static public int WINDOW_WIDTH = 1200;

    private BoardPanel boardPanel;

    private BoardGUIModel boardModel;

    public BoardWindow() {
        super(WINDOW_NAME);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.boardPanel = new BoardPanel();

        this.add(this.addScrollPane(), BorderLayout.CENTER);

        this.pack();

        this.setVisible(true);
    }

    public JScrollPane addScrollPane() {
        JScrollPane pane = new JScrollPane(this.boardPanel);
        pane.setBackground(Color.WHITE);
        pane.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.setViewportView(this.boardPanel);

        this.add(pane, BorderLayout.CENTER);

        return pane;
    }

    public void refresh(final BoardGUIModel model) {
        this.boardModel = model;
        this.boardPanel.drawBoard( this.boardModel);
        this.add(this.addScrollPane(), BorderLayout.CENTER);
    }

    public void updateCharacterIcons(ArrayList<Player> otherPlayers) {
        this.boardPanel.updateCharacterIcons(otherPlayers);
    }

    public void setupStatusLabel() {
        this.boardPanel.setupStatusLabel();
    }

    public void setStatusText(final String text) {
        this.boardPanel.setStatusText(text);
    }

    public void setupMoveButtons(ArrayList<JButton> buttons) {
        this.boardPanel.displayMoveButtonsForClearing(buttons);
    }

    public void setupCharacterIcons() {
       this.boardPanel.setupCharacterIcons(this.boardModel.getPlayers());
    }

    public BoardGUIModel getBoardModel() {
        return this.boardModel;
    }

    public void showStatusLabel() {
        boardPanel.showStatusLabel();
    }

    public void hideStatusLabel() {
        boardPanel.hideStatusLabel();
    }

}
