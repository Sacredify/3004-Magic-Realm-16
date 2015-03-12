package ca.carleton.magicrealm.GUI.tile;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.chit.Dwelling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * <p/>
 * Date: 16/02/15
 * Time: 7:54 PM
 */
public class Clearing implements Serializable {;

    private int x;

    private int y;

    private Dwelling dwelling;

    private final AbstractTile parentTile;

    private final int index;

    private final List<Clearing> adjacentClearings;

    private final List<Entity> entities;

    private String name = "";

    public Clearing(final AbstractTile parentTile, int index) {
        this.parentTile = parentTile;
        this.index = index;
        this.adjacentClearings = new ArrayList<Clearing>();
        this.entities = new ArrayList<Entity>();
    }

    public int getIndex() {
        return this.index;
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
     * Return the list of adjacent clearings.
     *
     * @return the adjacent clearings one may move to.
     */
    public List<Clearing> getAdjacentClearings() {
        return this.adjacentClearings;
    }

    /**
     * Gets the tile this clearing is on.
     *
     * @return the parent tile.
     */
    public AbstractTile getParentTile() {
        return this.parentTile;
    }

    public static Clearing[] initializeClearingsArray(int size, AbstractTile pTile) {

        Clearing[] placeHolder = new Clearing[size];

        for (int i = 0; i < size; i++) {
            placeHolder[i] = new Clearing(pTile, i);
        }

        return placeHolder;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void addEntity(final Entity entity) {
        this.entities.add(entity);
    }

    public void removeEntity(final Entity entity) {
        this.entities.remove(entity);
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public Dwelling getDwelling() {
        return this.dwelling;
    }

    public void setDwelling(Dwelling dwelling) {
        this.dwelling = dwelling;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.parentTile.getTileInformation() + " " + this.name + ".";
    }

    @Override
    public boolean equals(final Object rhs) {
        return !(rhs == null || !(rhs instanceof Clearing)) && this.toString().equals(rhs.toString());
    }

}

