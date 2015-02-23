package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Tony on 14/02/2015.
 *
 * Service class to store methods used by a view
 */
public class BoardServices {
    public static final int ORIGINAL_TILE_WIDTH = 500;
    public static final int ORIGINAL_TILE_HEIGHT = 430;

    public static final int TILE_WIDTH = 200;
    public static final int TILE_HEIGHT = 172;

    public static final int RESIZE_TILE_WIDTH = 226;
    public static final int RESIZE_TILE_HEIGHT = 215;

    public static final int CHIT_WIDTH = 40;
    public static final int CHIT_HEIGHT = 35;

    public ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = this.getClass().getClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public JLabel createTileIcon(AbstractTile tile, final boolean firstDraw) {
        JLabel newTile = new JLabel();
        newTile.setSize(TILE_WIDTH, TILE_HEIGHT);
        ImageIcon newIcon = this.createImageIcon(tile.getTileInformation().getPath());
        BufferedImage newImage = imageToBufferedImage(newIcon.getImage());
        newImage = this.applyTransformations(newImage, newTile, tile, firstDraw);

        newIcon.setImage(newImage);
        newTile.setIcon(newIcon);
        return newTile;
    }

    public ArrayList<JLabel> createChitIconsForTile(AbstractTile tile) {
        ArrayList<JLabel> iconList = new ArrayList<JLabel>();

        for (Clearing clearing : tile.getClearings()) {
            JLabel newChit = null;
            ImageIcon newIcon = null;
            if (clearing.getDwelling() != null) {
                newChit = new JLabel();
                newChit.setSize(CHIT_WIDTH, CHIT_HEIGHT);
                newIcon = this.createImageIcon(clearing.getDwelling().getPath());
            } else if (!clearing.getEntities().isEmpty()) {
                newChit = new JLabel();
                newChit.setSize(CHIT_WIDTH, CHIT_HEIGHT);
                newIcon = this.createImageIcon(clearing.getEntities().get(0).getEntityInformation().getPath());
            }
            if (newChit != null && newIcon != null) {
                BufferedImage newImage = imageToBufferedImage(newIcon.getImage());

                newImage = resize(newImage, CHIT_WIDTH, CHIT_HEIGHT);
                newIcon.setImage(newImage);
                newChit.setIcon(newIcon);
                newChit.setEnabled(true);
            }
            iconList.add(newChit);
        }

        return iconList;
    }

    /**
     * Method to resize a BufferedImage
     * @param image image to resize
     * @param width width to resize to
     * @param height height to resize to
     * @return
     */
    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }

    /**
     * Convert an Image to a BufferedImage
     * @param image image to convert
     * @return a BufferedImage of the image given
     */
    public static BufferedImage imageToBufferedImage(Image image) {
        BufferedImage bi = new BufferedImage
                (image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
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
            if (firstDraw) {
                for (Clearing clearing : tile.getClearings()) {
                    double[] point = new double[2];
                    point[0] = clearing.getX();
                    point[1] = clearing.getY();
                    rotatePoint(point, tile.getAngle());

                    clearing.setScaledXAngled((int) Math.round(point[0]));
                    clearing.setScaledYAngled((int) Math.round(point[1]));
                }
            }
        }
        else {
            bufferedImage = resize(bufferedImage, TILE_WIDTH, TILE_HEIGHT);
            label.setSize(TILE_WIDTH, TILE_HEIGHT);

            if (firstDraw) {
                for (Clearing clearing : tile.getClearings()) {
                    clearing.setScaledXRegular(clearing.getX());
                    clearing.setScaledYRegular(clearing.getY());
                }
            }
        }
        return bufferedImage;
    }

    /**
     * Rotate the Icon of a BufferedImage
     * @param bufferedImage image to be rotated
     * @param angle angle to rotate by
     */
    public static BufferedImage rotateBufferedImage(BufferedImage bufferedImage, double angle) {
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
    public static void rotatePoint(double[] point, double angle) {
        AffineTransform.getRotateInstance(Math.toRadians(angle), ORIGINAL_TILE_WIDTH/2, ORIGINAL_TILE_HEIGHT/2)
                .transform(point, 0, point, 0, 1); // specifying to use this double[] to hold coords
    }





}
