package ca.carleton.magicrealm.GUI.tile;

import ca.carleton.magicrealm.entity.chit.Dwelling;
import ca.carleton.magicrealm.item.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * <p/>
 * Date: 16/02/15
 * Time: 7:54 PM
 */


public class Clearing implements Serializable {

    public double TILE_SCALEDOWN_MULTIPLIER_X = 2.5;
    public double TILE_SCALEDOWN_MULTIPLIER_Y = 2.5;

    public double ROTATED_TILE_SCALEDOWN_MULTIPLIER_X = 2.21;
    public double ROTATED_TILE_SCALEDOWN_MULTIPLIER_Y = 2;

    private int x;

    private int y;

    private List<Item> items;

    private Dwelling dwelling;

    private AbstractTile parentTile;

    private int index;

    private List<Clearing> adjacentClearings;

    public Clearing(final AbstractTile parentTile,int index) {
        this.parentTile = parentTile;
        this.index  = index;
        this.adjacentClearings = new ArrayList<Clearing>();
    }

    public int getIndex(){
        return index;
    }


    /**
     * Connects the clearing parameter.
     *
     * @param clearing is the clearing to add.
     *                 returns clearing that was passed in as parameter
     *                 ,but with self added to the clearings possible paths list
     */
    public void connectTo(Clearing clearing) {
        this.adjacentClearings.add(clearing);
        clearing.adjacentClearings.add(this);
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

    public static Clearing[] initializeClearingsArray(int size, AbstractTile pTile) {

        Clearing[] placeHolder = new Clearing[size];

        for (int i = 0; i < size; i++) {
            placeHolder[i] = new Clearing(pTile,i);
        }

        return placeHolder;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setScaledXRegular(int x) {
        this.x = (int) Math.round(x / TILE_SCALEDOWN_MULTIPLIER_X);
    }

    public void setScaledYRegular(int y) {
        this.y = (int) Math.round(y / TILE_SCALEDOWN_MULTIPLIER_Y);
    }

    public void setScaledXAngled(int x) {
        this.x = (int) Math.round(x / ROTATED_TILE_SCALEDOWN_MULTIPLIER_X);
    }

    public void setScaledYAngled(int y) {
        this.y = (int) Math.round(y / ROTATED_TILE_SCALEDOWN_MULTIPLIER_Y);
    }

    public Dwelling getDwelling() {
        return dwelling;
    }

    public void setDwelling(Dwelling dwelling) {
        this.dwelling = dwelling;
    }
}

