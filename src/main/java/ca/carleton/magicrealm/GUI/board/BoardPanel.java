package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Tony on 18/02/2015.
 *
 * The view for the game board
 */
public class BoardPanel extends JLayeredPane {
    final static public int WINDOW_HEIGHT = 1500;
    final static public int WINDOW_WIDTH = 1500;
    final static public int TILE_X_OFFSET = 188;
    final static public int TILE_DELTA_X = 375;
    final static public int TILE_DELTA_Y = 108;
    public static final int CHIT_WIDTH = 40;
    public static final int CHIT_HEIGHT = 35;

    private BoardServices boardServices;

    private ArrayList<JButton> moveButtons;

    public BoardPanel() {
        boardServices = new BoardServices();
        this.setAutoscrolls(true);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
    }

    /**
     * Method to draw the board's current state, based on the given BoardModel
     *
     * @param boardGUIModel board to draw
     */
    public void drawBoard(BoardGUIModel boardGUIModel) {
        this.removeAll();

        int y = 0;
        for (ArrayList<AbstractTile> row : boardGUIModel.getBoard()) {
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
                    //add a listener to the tile
                    newTile.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent e) {
                            iconClickedEvent(e);
                        }
                    });
                    this.add(newTile, JLayeredPane.DEFAULT_LAYER);

                    /** create the chits **/
                    ArrayList<JButton> newChits = boardServices.createChitIconsForTile(tile);
                    for (int i = 0; i < newChits.size(); i++) {
                        if (newChits.get(i) != null) {
                            newChits.get(i).setLocation(tileX + tile.getClearingAt(i).getX() - CHIT_WIDTH/2,
                                    tileY + tile.getClearingAt(i).getY() - CHIT_HEIGHT/2);
                            this.add(newChits.get(i), JLayeredPane.PALETTE_LAYER);
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

    public void paintComponent(Graphics g, Image image){
        super.paintComponent(g);
        g.drawImage(image , 0, 0, this);
    }

    public void displayMoveButtonsForClearing(Clearing clearing) {
        for (JButton button : moveButtons) {
            this.remove(button);
        }
        this.moveButtons = boardServices.getMoveButtonsForClearing(clearing);
        for (JButton button : moveButtons) {
            this.add(button);
        }
    }
}
