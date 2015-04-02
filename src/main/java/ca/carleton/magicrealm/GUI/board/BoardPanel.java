package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.board.characterinfo.CharacterInfoDialog;
import ca.carleton.magicrealm.GUI.infodialog.InfoDialog;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Tony on 18/02/2015.
 * <p>
 * The view for the game board
 */
public class BoardPanel extends JLayeredPane {

    public static final int WINDOW_HEIGHT = 1200;
    public static final int WINDOW_WIDTH = 720;
    public static final int TILE_X_OFFSET = 150;
    public static final int TILE_DELTA_X = 300;
    public static final int TILE_DELTA_Y = 85;
    public static final int GAME_INFO_PANEL_WIDTH = 400;
    public static final int GAME_INFO_PANEL_HEIGHT = 600;

    public static final String CHARACTER_INFO_BUTTON_TEXT = "Character Info";

    private final BoardServices boardServices;

    private JLabel statusLabel;

    private JLabel gameInformationLabel;

    private boolean firstDraw;

    private int maximumX;

    public BoardPanel() {
        this.firstDraw = true;
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
     * @param boardModel board to draw
     */
    public void drawBoard(final BoardModel boardModel, final AbstractCharacter character, final boolean isSunset) {
        this.removeAll();
        this.maximumX = 0;

        int y = 0;
        for (ArrayList<AbstractTile> row : boardModel.getBoard()) {
            int x = 0;
            for (final AbstractTile tile : row) {
                if (tile != null) {
                    /** create the tile **/
                    JLabel newTile = this.boardServices.createTileIcon(tile, this.firstDraw);
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

                    if (tileX + BoardServices.TILE_WIDTH > this.maximumX) {
                        this.maximumX = tileX + BoardServices.TILE_WIDTH;
                    }

                    /** create the chits **/
                    this.boardServices.createChitIconsForTile(tile, this, boardModel, tileX, tileY, character, isSunset);
                }
                x++;
            }
            y++;
        }

        this.setupGameInfoLabel();
        this.setupCharacterInfoButton(character);
        this.firstDraw = false;
    }

    public void iconClickedEvent(MouseEvent e, AbstractTile tile) {
        System.out.println("clicked: x - " + e.getLocationOnScreen().getX() + " y - " + e.getLocationOnScreen().getY());
        new InfoDialog(tile).displayWindow();
    }

    public void setupCharacterInfoButton(final AbstractCharacter character) {
        final JButton displayCharacterInformationButton = new JButton(CHARACTER_INFO_BUTTON_TEXT);
        displayCharacterInformationButton.setSize(150, 30);
        displayCharacterInformationButton.setLocation(this.maximumX, 0);
        displayCharacterInformationButton.addActionListener(e ->
                new CharacterInfoDialog(character));
        this.add(displayCharacterInformationButton, DEFAULT_LAYER);
    }

    /**
     * Setup the information panel for the game
     */
    public void setupGameInfoLabel() {
        this.gameInformationLabel = new JLabel();
        this.gameInformationLabel.setSize(GAME_INFO_PANEL_WIDTH, GAME_INFO_PANEL_HEIGHT);
        this.gameInformationLabel.setLocation(this.maximumX, 25);
        this.add(this.gameInformationLabel, DEFAULT_LAYER);
    }

    public void setGameInfoText(final String text) {
        this.gameInformationLabel.setText(text);
    }

    public void setupStatusLabel() {
        this.statusLabel = new JLabel();

        this.statusLabel.setSize(500, 50);
        this.statusLabel.setLocation(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
        this.add(this.statusLabel, JLayeredPane.PALETTE_LAYER);
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
