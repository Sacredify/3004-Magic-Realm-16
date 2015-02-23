package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.infodialog.InfoDialog;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static ca.carleton.magicrealm.GUI.board.BoardServices.imageToBufferedImage;

/**
 * Created by Tony on 18/02/2015.
 * <p/>
 * The view for the game board
 */
public class BoardPanel extends JLayeredPane {

    final static public int WINDOW_HEIGHT = 1200;
    final static public int WINDOW_WIDTH = 720;
    final static public int TILE_X_OFFSET = 150;
    final static public int TILE_DELTA_X = 300;
    final static public int TILE_DELTA_Y = 85;
    public static final int CHIT_WIDTH = 40;
    public static final int CHIT_HEIGHT = 35;

    private BoardServices boardServices;

    private ArrayList<JLabel> characterIcons;

    private JLabel statusLabel;

    private boolean firstDraw;

    public BoardPanel() {
        firstDraw = true;
        this.boardServices = new BoardServices();
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
    }

    public Dimension getPreferredSize() {
        return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
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
            for (final AbstractTile tile : row) {
                if (tile != null) {
                    /** create the tile **/
                    JLabel newTile = this.boardServices.createTileIcon(tile, firstDraw);
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
                            BoardPanel.this.iconClickedEvent(e, tile);
                        }
                    });
                    this.add(newTile, JLayeredPane.DEFAULT_LAYER);

                    if (firstDraw) {
                        /** set the x and y locations relative to the board for each clearing (overwrites its current value) **/
                        for (final Clearing clearing : tile.getClearings()) {
                            int clearingNewX = tileX + clearing.getX() - CHIT_WIDTH / 2;
                            int clearingNewY = tileY + clearing.getY() - CHIT_HEIGHT / 2;
                            clearing.setX(clearingNewX);
                            clearing.setY(clearingNewY);
                        }
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

        this.setupCharacterIcons(boardGUIModel.getPlayers());
        firstDraw = false;
    }

    public void iconClickedEvent(MouseEvent e, AbstractTile tile) {
        System.out.println("clicked: x - " + e.getLocationOnScreen().getX() + " y - " + e.getLocationOnScreen().getY());
        new InfoDialog(tile).displayWindow();
    }

    public void setupCharacterIcons(final java.util.List<Player> otherPlayers) {
        this.characterIcons = new ArrayList<>();
        for (Player player : otherPlayers) {
            JLabel newCharacterIcon = new JLabel();
            newCharacterIcon.setSize(CHIT_WIDTH, CHIT_HEIGHT);
            newCharacterIcon.setLocation(player.getCurrentClearing().getX(),
                    player.getCurrentClearing().getY());


            ImageIcon newIcon = this.boardServices.createImageIcon(player.getCharacter().getEntityInformation().getPath());
            BufferedImage newImage = imageToBufferedImage(newIcon.getImage());
            newImage = BoardServices.resize(newImage, CHIT_WIDTH, CHIT_HEIGHT);
            newIcon.setImage(newImage);

            newCharacterIcon.setIcon(newIcon);
            this.add(newCharacterIcon, JLayeredPane.PALETTE_LAYER);
        }

    }

    public void updateCharacterIcons(final ArrayList<Player> otherPlayers) {
        for (int i = 0; i < otherPlayers.size(); i++) {
            this.characterIcons.get(i).setLocation(otherPlayers.get(i).getCurrentClearing().getX(),
                    otherPlayers.get(i).getCurrentClearing().getY());
        }
    }

    public void setupStatusLabel() {
        this.statusLabel = new JLabel();

        this.statusLabel.setSize(500, 50);
        this.statusLabel.setLocation(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
        add(this.statusLabel, JLayeredPane.PALETTE_LAYER);
    }

    public void setStatusText(final String text) {
        this.statusLabel.setText(text);
    }

    public void showStatusLabel() {
        this.statusLabel.setVisible(true);
    }

    public void hideStatusLabel() {
        this.statusLabel.setVisible(false);
    }
}
