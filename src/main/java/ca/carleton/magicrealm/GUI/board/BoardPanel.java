package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.board.characterinfo.CharacterInfoDialog;
import ca.carleton.magicrealm.GUI.infodialog.InfoDialog;
import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.entity.natives.AbstractNative;
import ca.carleton.magicrealm.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public static final int WINDOW_HEIGHT = 1200;
    public static final int WINDOW_WIDTH = 720;
    public static final int TILE_X_OFFSET = 150;
    public static final int TILE_DELTA_X = 300;
    public static final int TILE_DELTA_Y = 85;
    public static final int CHIT_WIDTH = 44;
    public static final int CHIT_HEIGHT = 44;
    public static final int GAME_INFO_PANEL_WIDTH = 300;
    public static final int GAME_INFO_PANEL_HEIGHT = 500;

    public static final String CHARACTER_INFO_BUTTON_TEXT = "Character Info";

    private BoardServices boardServices;

    private ArrayList<JLabel> characterIcons;

    private ArrayList<JLabel> nativeIcons;

    private JLabel statusLabel;

    private JLabel gameInformationLabel;

    private boolean firstDraw;

    private int maximumX;

    private JButton displayCharacterInformationButton;

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
    public void drawBoard(BoardGUIModel boardGUIModel, AbstractCharacter character) {
        this.removeAll();
        maximumX = 0;

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

                    if (tileX + BoardServices.TILE_WIDTH > maximumX) {
                        maximumX = tileX + BoardServices.TILE_WIDTH;
                    }

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
        this.setupGameInfoLabel();
        this.setupCharacterInfoButton(character);
        this.firstDraw = false;
    }

    public void iconClickedEvent(MouseEvent e, AbstractTile tile) {
        System.out.println("clicked: x - " + e.getLocationOnScreen().getX() + " y - " + e.getLocationOnScreen().getY());
        new InfoDialog(tile).displayWindow();
    }

    public void setupCharacterInfoButton(AbstractCharacter character) {
        this.displayCharacterInformationButton = new JButton(CHARACTER_INFO_BUTTON_TEXT);
        this.displayCharacterInformationButton.setSize(50,30);
        this.displayCharacterInformationButton.setLocation(maximumX, -100);
        this.displayCharacterInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CharacterInfoDialog(character);
            }
        });
        this.add(displayCharacterInformationButton);
    }

    /**
     * Setup the information panel for the game
     */
    public void setupGameInfoLabel() {
        this.gameInformationLabel = new JLabel();
        this.gameInformationLabel.setSize(GAME_INFO_PANEL_WIDTH, GAME_INFO_PANEL_HEIGHT);
        this.gameInformationLabel.setLocation(maximumX, 0);
        this.add(gameInformationLabel);
    }

    public void setGameInfoText(final String text) {
        this.gameInformationLabel.setText(text);
    }

    public void setupCharacterIcons(final java.util.List<Player> otherPlayers) {
        this.characterIcons = new ArrayList<>();
        for (Player player : otherPlayers) {
            JLabel newCharacterIcon = new JLabel();
            newCharacterIcon.setSize(CHIT_WIDTH, CHIT_HEIGHT);

            newCharacterIcon.setLocation(player.getCurrentClearing().getX() - CHIT_WIDTH/2,
                    player.getCurrentClearing().getY() - CHIT_HEIGHT/2);


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
            this.characterIcons.get(i).setLocation(otherPlayers.get(i).getCurrentClearing().getX() - CHIT_WIDTH/2,
                    otherPlayers.get(i).getCurrentClearing().getY() - CHIT_HEIGHT/2);
        }
    }

    /**
     * Method to set up the list of natives on the board
     *
     * @param nativeList the list of natives on the board
     */
    public void setupNativeIcons(final java.util.List<AbstractNative> nativeList) {
        this.nativeIcons = new ArrayList<>();
        for (AbstractNative abstractNative : nativeList) {
            JLabel newNativeIcon = new JLabel();
            newNativeIcon.setSize(CHIT_WIDTH, CHIT_HEIGHT);

            newNativeIcon.setLocation(abstractNative.getStartingClearing().getX() - CHIT_WIDTH/2,
                                      abstractNative.getStartingClearing().getY() - CHIT_HEIGHT/2);


            ImageIcon newIcon = this.boardServices.createImageIcon(abstractNative.getEntityInformation().getPath());
            BufferedImage newImage = imageToBufferedImage(newIcon.getImage());
            newImage = BoardServices.resize(newImage, CHIT_WIDTH, CHIT_HEIGHT);
            newIcon.setImage(newImage);

            newNativeIcon.setIcon(newIcon);
            this.add(newNativeIcon, JLayeredPane.PALETTE_LAYER);
        }
    }

    /**
     * Method to update all the natives' icons on the board
     *
     * @param nativeList the list of natives on the board
     */
    public void updateNativeIcons(final java.util.List<AbstractNative> nativeList) {
        for (int i = 0; i < nativeList.size(); i++) {
            this.nativeIcons.get(i).setLocation(nativeList.get(i).getCurrentClearing().getX() - CHIT_WIDTH/2,
                                                nativeList.get(i).getCurrentClearing().getY() - CHIT_HEIGHT/2);
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
