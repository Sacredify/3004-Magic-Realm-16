package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Tony on 18/02/2015.
 */
public class BoardPanel extends JPanel {
    final static public int WINDOW_HEIGHT = 1000;
    final static public int WINDOW_WIDTH = 1000;
    final static public int TILE_X_OFFSET = 225;
    final static public int TILE_DELTA_X = 450;
    final static public int TILE_DELTA_Y = 130;
    public static final int TILE_WIDTH = 250;
    public static final int TILE_HEIGHT = 250;
    public static final int CHIT_WIDTH = 61;
    public static final int CHIT_HEIGHT = 52;

    private BoardServices boardServices;

    public BoardPanel() {
        boardServices = new BoardServices();
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLayout(null);
    }

    public void drawBoard(BoardModel boardModel) {
        int y = 0;
        for (ArrayList<AbstractTile> row : boardModel.getBoard()) {
            int x = 0;
            for (AbstractTile tile : row) {
                if (tile != null) {
                    /** create the tile **/
                    JLabel newTile = boardServices.createTileIcon(tile);
                    int tileX = x * TILE_DELTA_X;
                    int tileY = y * TILE_DELTA_Y;
                    if (y % 2 == 0) {
                        newTile.setLocation(tileX, tileY);
                    }
                    else {
                        tileX += TILE_X_OFFSET;
                        newTile.setLocation(tileX, tileY);
                    }
                    this.add(newTile);
                    //add a listener to the tile
                    newTile.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent e) {
                            iconClickedEvent(e);
                        }
                    });
                    this.add(newTile);

                    /** create the chits **/
                    ArrayList<JButton> newChits = boardServices.createChitIconsForTile(tile);
                    for (int i = 0; i < newChits.size(); i++) {
                        if (newChits.get(i) != null) {
                            newChits.get(i).setLocation(tileX + tile.getClearingAt(i).getX(),
                                    tileY + tile.getClearingAt(i).getY());
                            this.add(newChits.get(i));
                        }
                    }

                }
                x++;
            }
            y++;
        }
    }

    public void iconClickedEvent(MouseEvent e) {
        System.out.printf("clicked");
    }

}
