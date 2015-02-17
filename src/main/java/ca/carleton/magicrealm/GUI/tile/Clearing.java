package ca.carleton.magicrealm.GUI.tile;

import ca.carleton.magicrealm.item.Item;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/02/15
 * Time: 7:54 PM
 */
public class Clearing {

    private int x;

    private int y;

    private List<Item> items;

    //private Dwelling dwelling;

    private AbstractTile parentTile;

    private List<Clearing> adjacentClearings;

    public Clearing(final AbstractTile parentTile) {
        this.parentTile = parentTile;
    }

    /**
     * Add an item to this clearing.
     *
     * @param item the item to add.
     */
    public void addItem(final Item item) {
        this.items.add(item);
    }

    /**
     * Add a clearing that is adjacent to this one.
     *
     * @param clearing the clearing to add.
     */
    public void addAdjacentClearing(final Clearing clearing) {
        this.adjacentClearings.add(clearing);
    }

    /**
     * Return the list of adjacent clearings.
     *
     * @return the adjacent clearings one may move to.
     */
    public List<Clearing> getAdjacentClearings() {
        return this.adjacentClearings;
    }

    /**
     * The list of items on this clearing.
     *
     * @return the list of items.
     */
    public List<Item> getItems() {
        return this.items;
    }

    /**
     * Gets the tile this clearing is on.
     *
     * @return the parent tile.
     */
    public AbstractTile getParentTime() {
        return this.parentTile;
    }
}
