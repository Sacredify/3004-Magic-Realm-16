package ca.carleton.magicrealm.GUI;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.TileInformation;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Tony on 12/02/2015.
 *
 * Window to display the game board
 */
public class BoardWindow {

    final static public String WINDOW_NAME = "Board";
    final static public int WINDOW_HEIGHT = 1000;
    final static public int WINDOW_WIDTH = 1000;
    final static public int TILE_X_OFFSET = 375;
    final static public int TILE_DELTA_X = 750;
    final static public int TILE_DELTA_Y = 215;
    public static int TILE_WIDTH = 500;
    public static int TILE_HEIGHT = 430;

    private BoardModel boardModel;

    private BoardServices boardServices;

    private ArrayList<ArrayList<JLabel>> boardIcons;

    JFrame frame;

    public BoardWindow() {
        boardIcons = new ArrayList<>();
        boardModel = new BoardModel();

        frame = new JFrame(WINDOW_NAME);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void initWindow() {
        boardServices = new BoardServices();

        frame.setLayout(null);

        drawBoard();

        frame.setVisible(true);
    }

    public void drawBoard() {
        int y = 0;
        for (ArrayList<AbstractTile> row : boardModel.getBoard()) {
            ArrayList<JLabel> rowOfTileIcons = new ArrayList<>();
            int x = 0;
            for (AbstractTile tile : row) {
                if (tile != null) {
                    JLabel newTile = boardServices.createTileIcon(tile);
                    newTile.setSize(TILE_WIDTH, TILE_HEIGHT);
                    /** If the row is odd (even index) don't offset it **/
                    if (y % 2 == 0) {
                        newTile.setLocation(x * TILE_DELTA_X, y * TILE_DELTA_Y);
                    }
                    else {
                        newTile.setLocation(x * TILE_DELTA_X + TILE_X_OFFSET, y * TILE_DELTA_Y);
                    }
                    frame.add(newTile);
                    newTile.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent e) {
                            iconClickedEvent(e);
                        }
                    });
                    rowOfTileIcons.add(newTile);
                }
                x++;
            }
            boardIcons.add(rowOfTileIcons);
            y++;
        }
    }

    public void iconClickedEvent(MouseEvent e) {
        System.out.printf("clicked");
    }


}
