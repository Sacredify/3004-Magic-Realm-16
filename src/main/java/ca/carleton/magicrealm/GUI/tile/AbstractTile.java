package ca.carleton.magicrealm.GUI.tile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/02/15
 * Time: 7:54 PM
 */
public abstract class AbstractTile {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractTile.class);

    protected Clearing[] clearings;

    private int clearingCount = 0;

    public abstract TileInformation getTileInformation();

    /**
     * Add a clearing to th is tile.
     *
     * @param clearing the clearing to add.
     */
    public void addClearing(final Clearing clearing) {
        if (this.clearings == null) {
            LOG.warn("Cannot add a clearing to an uninitialized tile.");
        }

        if (this.clearingCount < this.clearings.length) {
            this.clearings[this.clearingCount] = clearing;
            this.clearingCount++;
        } else {
            LOG.warn("Clearings are full.");
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

}
