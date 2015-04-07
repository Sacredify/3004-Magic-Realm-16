package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.entity.chit.ColoredChit;
import ca.carleton.magicrealm.entity.monster.AbstractMonster;
import ca.carleton.magicrealm.entity.natives.AbstractNative;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Created by Tony on 14/02/2015.
 * <p>
 * Service class to store methods used by a view
 */
public class BoardServices {

    private static final Logger LOG = LoggerFactory.getLogger(BoardServices.class);

    public static final int ORIGINAL_TILE_WIDTH = 500;
    public static final int ORIGINAL_TILE_HEIGHT = 430;

    public static final int TILE_WIDTH = 160;
    public static final int TILE_HEIGHT = 138;

    public static final int RESIZE_TILE_WIDTH = 181;
    public static final int RESIZE_TILE_HEIGHT = 172;

    public static final int CHIT_WIDTH = 32;
    public static final int CHIT_HEIGHT = 28;

    public static final double TILE_SCALEDOWN_MULTIPLIER_X = 3.125;
    public static final double TILE_SCALEDOWN_MULTIPLIER_Y = 3.125;

    public static final double ROTATED_TILE_SCALEDOWN_MULTIPLIER_X = 2.7625;
    public static final double ROTATED_TILE_SCALEDOWN_MULTIPLIER_Y = 2.5;

    public ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = this.getClass().getClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            LOG.error("Couldn't find file: " + path);
            return null;
        }
    }

    public JLabel createTileIcon(AbstractTile tile, final boolean firstDraw) {
        JLabel newTile = new JLabel();
        newTile.setSize(TILE_WIDTH, TILE_HEIGHT);
        ImageIcon newIcon = this.createImageIcon(tile.isEnchanted() ? tile.getTileInformation().getEnchantedPath() : tile.getTileInformation().getPath());
        BufferedImage newImage = imageToBufferedImage(newIcon.getImage());
        newImage = this.applyTransformations(newImage, newTile, tile, firstDraw);

        newIcon.setImage(newImage);
        newTile.setIcon(newIcon);
        return newTile;
    }

    /**
     * Method to create the chits on the board.
     * This is called from the drawBoard method in BoardPanel
     *
     * @param tile        the tile we're going to draw on.
     * @param panel       the parent panel.
     * @param tileOffsetX offset X.
     * @param tileOffsetY offset Y.
     * @param character   the client character.
     */
    public void createChitIconsForTile(AbstractTile tile, final BoardPanel panel, final BoardModel boardModel, final int tileOffsetX, final int tileOffsetY, final AbstractCharacter character, final boolean isSunset) {

        for (Clearing clearing : tile.getClearings()) {
            JLabel newChit;
            ImageIcon newIcon;
            int clearingX;
            int clearingY;

            if (tile.getAngle() % 180 != 0) {
                double[] point = new double[2];
                point[0] = clearing.getX();
                point[1] = clearing.getY();
                rotatePoint(point, tile.getAngle());

                clearingX = this.getScaledXAngled((int) Math.round(point[0]));
                clearingY = this.getScaledYAngled((int) Math.round(point[1]));
            } else if (tile.getAngle() % 180 == 0) {
                double[] point = new double[2];
                point[0] = clearing.getX();
                point[1] = clearing.getY();
                rotatePoint(point, tile.getAngle());

                clearingX = this.getScaledXRegular((int) Math.round(point[0]));
                clearingY = this.getScaledYRegular((int) Math.round(point[1]));
            } else {
                clearingX = this.getScaledXRegular(clearing.getX());
                clearingY = this.getScaledYRegular(clearing.getY());
            }

            if (clearing.getDwelling() != null) {
                newChit = new JLabel();
                newChit.setSize(CHIT_WIDTH, CHIT_HEIGHT);
                newIcon = this.createImageIcon(clearing.getDwelling().getPath());

                if (newChit != null && newIcon != null) {
                    BufferedImage newImage = imageToBufferedImage(newIcon.getImage());
                    newImage = resize(newImage, CHIT_WIDTH, CHIT_HEIGHT);
                    newIcon.setImage(newImage);
                    newChit.setIcon(newIcon);
                    newChit.setEnabled(true);
                    newChit.setLocation(this.applyTileXOffset(clearingX, tileOffsetX), this.applyTileYOffset(clearingY, tileOffsetY));
                    panel.add(newChit, JLayeredPane.PALETTE_LAYER);
                }
            }
            if (isSunset && boardModel.getClearingForCharacter(character).getParentTile().equals(tile)) {
                for (ColoredChit chit : tile.getChits()) {
                    // Ghetto fix for problem pertaining to chit for a tile being on every clearing
                    if (chit.getClearingNumber() == Integer.parseInt(clearing.getName())) {

                        LOG.info("Sunset - Displaying chits on the tile of the player.");

                        newChit = new JLabel();
                        newChit.setSize(CHIT_WIDTH, CHIT_HEIGHT);
                        newIcon = this.createImageIcon(chit.getChitColor().getImageFilePath());

                        if (newChit != null && newIcon != null) {
                            BufferedImage newImage = imageToBufferedImage(newIcon.getImage());
                            newImage = resize(newImage, CHIT_WIDTH, CHIT_HEIGHT);
                            newIcon.setImage(newImage);
                            newChit.setIcon(newIcon);
                            newChit.setEnabled(true);
                            if (chit.getClearingNumber() == -1) {
                                newChit.setLocation(this.applyTileXOffset(TILE_WIDTH / 2, tileOffsetX), this.applyTileYOffset(TILE_HEIGHT / 2, tileOffsetY));
                            } else {
                                newChit.setLocation(this.applyTileXOffset(clearingX, tileOffsetX), this.applyTileYOffset(clearingY, tileOffsetY));
                            }
                            panel.add(newChit, JLayeredPane.PALETTE_LAYER);
                        }
                    }
                }
            }
            if (!clearing.getEntities().isEmpty()) {
                for (Entity drawable : clearing.getEntities()) {
                    if (drawable instanceof AbstractNative) {
                        continue;
                    }

                    if (drawable instanceof AbstractCharacter) {
                        // Let's hide people from others if they're hidden...
                        final AbstractCharacter drawableCharacter = (AbstractCharacter) drawable;
                        // Only skip if the entity is hidden and the character isn't the client's.
                        if (drawable.isHidden() && !(drawableCharacter.getEntityInformation() == character.getEntityInformation())) {
                            LOG.info("Skipping draw of {} because they are hidden.", drawable.getEntityInformation());
                            continue;
                        }
                    } else if (drawable.isHidden() && drawable instanceof AbstractMonster) {
                        LOG.info("Skipping draw of {} because they are hidden.", drawable.getEntityInformation());
                        continue;
                    }

                    newChit = new JLabel();
                    newChit.setSize(CHIT_WIDTH, CHIT_HEIGHT);
                    newIcon = this.createImageIcon(drawable.getEntityInformation().getPath());

                    if (newChit != null && newIcon != null) {
                        BufferedImage newImage = imageToBufferedImage(newIcon.getImage());
                        newImage = resize(newImage, CHIT_WIDTH, CHIT_HEIGHT);
                        newIcon.setImage(newImage);
                        newChit.setIcon(newIcon);
                        newChit.setEnabled(true);
                        newChit.setLocation(this.applyTileXOffset(clearingX, tileOffsetX), this.applyTileYOffset(clearingY, tileOffsetY));
                        panel.add(newChit, JLayeredPane.PALETTE_LAYER);
                    }
                }
            }
        }

    }

    /**
     * Method to resize a BufferedImage
     *
     * @param image  image to resize
     * @param width  width to resize to
     * @param height height to resize to
     * @return
     */
    public static BufferedImage resize(BufferedImage image, final int width, final int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }

    /**
     * Convert an Image to a BufferedImage
     *
     * @param image image to convert
     * @return a BufferedImage of the image given
     */
    public static BufferedImage imageToBufferedImage(Image image) {
        BufferedImage bi = new BufferedImage
                (image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(image, 0, 0, null);
        bg.dispose();
        return bi;
    }

    public BufferedImage applyTransformations(BufferedImage bufferedImage, JLabel label, AbstractTile tile, final Boolean firstDraw) {
        bufferedImage = rotateBufferedImage(bufferedImage, tile.getAngle());
        if (tile.getAngle() % 180 != 0) {
            bufferedImage = resize(bufferedImage, RESIZE_TILE_WIDTH, RESIZE_TILE_HEIGHT);
            label.setSize(RESIZE_TILE_WIDTH, RESIZE_TILE_HEIGHT);
        } else {
            bufferedImage = resize(bufferedImage, TILE_WIDTH, TILE_HEIGHT);
            label.setSize(TILE_WIDTH, TILE_HEIGHT);
        }
        return bufferedImage;
    }

    /**
     * Rotate the Icon of a BufferedImage
     *
     * @param bufferedImage image to be rotated
     * @param angle         angle to rotate by
     */
    public static BufferedImage rotateBufferedImage(BufferedImage bufferedImage, final double angle) {
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(angle), ORIGINAL_TILE_WIDTH / 2, ORIGINAL_TILE_HEIGHT / 2);

        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_BILINEAR);
        return op.filter(bufferedImage, null);

    }

    /**
     * Rotate a point around the center of an icon
     *
     * @param point (x,y) point to rotate
     */
    public static void rotatePoint(final double[] point, final double angle) {
        AffineTransform.getRotateInstance(Math.toRadians(angle), ORIGINAL_TILE_WIDTH / 2, ORIGINAL_TILE_HEIGHT / 2)
                .transform(point, 0, point, 0, 1); // specifying to use this double[] to hold coords
    }

    public int applyTileXOffset(final int clearingX, final int tileOffsetX) {
        return tileOffsetX + clearingX - CHIT_WIDTH / 2;
    }

    public int applyTileYOffset(final int clearingY, final int tileOffsetY) {
        return tileOffsetY + clearingY - CHIT_HEIGHT / 2;
    }

    public int getScaledXRegular(int x) {
        return (int) Math.round(x / TILE_SCALEDOWN_MULTIPLIER_X);
    }

    public int getScaledYRegular(int y) {
        return (int) Math.round(y / TILE_SCALEDOWN_MULTIPLIER_Y);
    }

    public int getScaledXAngled(int x) {
        return (int) Math.round(x / ROTATED_TILE_SCALEDOWN_MULTIPLIER_X);
    }

    public int getScaledYAngled(int y) {
        return (int) Math.round(y / ROTATED_TILE_SCALEDOWN_MULTIPLIER_Y);
    }


}
