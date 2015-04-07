package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.board.infoDialogs.CharacterInfoDialog;
import ca.carleton.magicrealm.GUI.board.infoDialogs.GameInfoDialog;
import ca.carleton.magicrealm.GUI.infodialog.TileInfoDialog;
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
    public static final int TILE_X_OFFSET = 120;
    public static final int TILE_DELTA_X = 240;
    public static final int TILE_DELTA_Y = 68;

    public static final String CHARACTER_INFO_BUTTON_TEXT = "Character Info";

    public static final String GENERAL_GAME_INFO_BUTTON_TEXT= "Game Info";

    private final BoardServices boardServices;

    private JLabel statusLabel;

    private boolean firstDraw;

    private int maximumX;

    private String gameInfoText;

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

        this.setupCharacterInfoButton(character);
        this.setupGeneralInfoButton(character);
        this.firstDraw = false;
    }

    public void iconClickedEvent(MouseEvent e, AbstractTile tile) {
        System.out.println("clicked: x - " + e.getLocationOnScreen().getX() + " y - " + e.getLocationOnScreen().getY());
        new TileInfoDialog(tile).displayWindow();
    }

    /**
     * Setup the button to display the info dialog for the player
     * @param character Character to be displayed in the dialog
     */
    public void setupCharacterInfoButton(final AbstractCharacter character) {
        final JButton displayCharacterInformationButton = new JButton(CHARACTER_INFO_BUTTON_TEXT);
        displayCharacterInformationButton.setSize(150, 30);
        displayCharacterInformationButton.setLocation(this.maximumX, 0);
        displayCharacterInformationButton.addActionListener(e ->
                new CharacterInfoDialog(character));
        this.add(displayCharacterInformationButton, DEFAULT_LAYER);
    }

    public void setupGeneralInfoButton(final AbstractCharacter character) {
        final JButton displayGeneralInformationButton = new JButton(GENERAL_GAME_INFO_BUTTON_TEXT);
        displayGeneralInformationButton.setSize(150, 30);
        displayGeneralInformationButton.setLocation(this.maximumX, 30);
        displayGeneralInformationButton.addActionListener(e ->
                new GameInfoDialog(this.gameInfoText, character));
        this.add(displayGeneralInformationButton, DEFAULT_LAYER);
    }

    public void setGameInfoText(final String text) {
        this.gameInfoText = text;
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
