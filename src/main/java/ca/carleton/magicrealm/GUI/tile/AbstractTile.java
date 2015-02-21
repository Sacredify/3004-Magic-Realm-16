package ca.carleton.magicrealm.GUI.tile;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/02/15
 * Time: 7:54 PM
 */
public abstract class AbstractTile implements Serializable {

    protected Clearing[] clearings;

    private int clearingCount = 0;

    public abstract TileInformation getTileInformation();

    public abstract TileType getTileType();

    // refactor this later
    private int angle;

    /**
     * Add a clearing to th is tile.
     *
     * @param clearing the clearing to add.
     */
    public void addClearing(final Clearing clearing) {
        if (this.clearings == null) {
            System.out.println("Cannot add a clearing to an uninitialized tile.");
        }

        if (this.clearingCount < this.clearings.length) {
            this.clearings[this.clearingCount] = clearing;
            this.clearingCount++;
        } else {
            System.out.println("Clearings are full.");
        }

    }

    /**
     * Return the clearing at the given index.
     *
     * @param index the index.
     * @return the clearing.
     */
    public Clearing getClearingAt(final int index) {
        return this.clearings[index];
    }

    /**
     * Test to see if the tile is full.
     *
     * @return true if it is full
     */
    public boolean isFull() {
        return this.clearings[this.clearings.length - 1] != null;
    }

    public Clearing[] getClearings() {
        return clearings;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
