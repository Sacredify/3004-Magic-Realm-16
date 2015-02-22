package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Tony on 18/02/2015.
 * <p/>
 * The view for the game board
 */
public class BoardPanel extends JLayeredPane {
    final static public int WINDOW_HEIGHT = 1500;
    final static public int WINDOW_WIDTH = 1500;
    final static public int TILE_X_OFFSET = 150;
    final static public int TILE_DELTA_X = 300;
    final static public int TILE_DELTA_Y = 85;
    public static final int CHIT_WIDTH = 40;
    public static final int CHIT_HEIGHT = 35;

    private BoardServices boardServices;

    private ArrayList<JButton> moveButtons;

    public BoardPanel() {
        this.boardServices = new BoardServices();
        this.setLayout(null);
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
                    JLabel newTile = this.boardServices.createTileIcon(tile);
                    int tileX = x * TILE_DELTA_X;
                    int tileY = y * TILE_DELTA_Y;
                    if (y % 2 == 0) {
                        newTile.setLocation(tileX, tileY);
                    } else {
                        tileX += TILE_X_OFFSET;
                        newTile.setLocation(tileX, tileY);
                    }
                    newTile.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            iconClickedEvent(e);
                        }
                    });
                    this.add(newTile, JLayeredPane.DEFAULT_LAYER);

                    /** set the x and y locations relative to the board for each clearing (overwrites its current value) **/
                    for (final Clearing clearing : tile.getClearings()) {
                        int clearingNewX = tileX + clearing.getX() - CHIT_WIDTH / 2 ;
                        int clearingNewY = tileY + clearing.getY() - CHIT_HEIGHT / 2;
                        clearing.setX(clearingNewX);
                        clearing.setY(clearingNewY);
                    }

                    /** create the labels for each clearing **/
                    for (final Clearing clearing : tile.getClearings()) {
                        ClearingLabel clearingLabel = new ClearingLabel();
                        clearingLabel.clearing = clearing;
                        clearingLabel.setSize(new Dimension(30, 30));
                        clearingLabel.setLocation(clearing.getX(), clearing.getY());
                        clearingLabel.setEnabled(true);

                        clearingLabel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(final MouseEvent e) {
                                System.out.println("Clicked + " + ((ClearingLabel) e.getSource()).clearing);
                            }
                        });

                        this.add(clearingLabel, JLayeredPane.PALETTE_LAYER);
                    }

                    /** create the chits **/
                    ArrayList<JLabel> newChits = this.boardServices.createChitIconsForTile(tile);
                    for (int i = 0; i < newChits.size(); i++) {
                        if (newChits.get(i) != null) {
                            newChits.get(i).setLocation(tile.getClearingAt(i).getX(),
                                    tile.getClearingAt(i).getY());
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
        System.out.println("clicked: x - " + e.getLocationOnScreen().getX() + " y - " + e.getLocationOnScreen().getY());
    }
    
    public void paintComponent(Graphics g, Image image) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    public void displayMoveButtonsForClearing(ArrayList<JButton> buttons) {
        for (JButton button : this.moveButtons) {
            this.remove(button);
        }
        this.moveButtons = buttons;
        for (JButton button : this.moveButtons) {
            this.add(button);
        }
    }
}
