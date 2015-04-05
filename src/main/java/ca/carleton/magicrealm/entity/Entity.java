package ca.carleton.magicrealm.entity;

import ca.carleton.magicrealm.GUI.tile.Discoverable;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.Item;
import ca.carleton.magicrealm.item.ItemInformation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Entity within the Magic Realm
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 09/02/15
 * Time: 5:45 PM
 */
public abstract class Entity implements Serializable, Discoverable {

    private static final long serialVersionUID = -2942027905717935113L;

    /**
     * The vulnerability of an entity is their health. Determined by their character.
     */
    protected Harm vulnerability;

    /**
     * Whether or not the entity is hidden on the tile.
     */
    protected boolean hidden;

    // Used by combat. Dead resets the character, wounded makes them wound chits. Fatigued if they fatigued themselves. Reset after combat.
    protected boolean dead;

    /**
     * The list of items the entity is currently holding.
     */
    private final List<Item> items = new ArrayList<Item>();

    public abstract EntityInformation getEntityInformation();

    public Harm getVulnerability() {
        return this.vulnerability;
    }

    public void addItem(final Item item) {
        this.items.add(item);
    }

    public void removeItem(final Item item) {
        this.items.remove(item);
    }

    public List<Item> getItems() {
        return this.items;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Whether or not the player has a given item (by the info).
     *
     * @param itemInformation the item information.
     * @return true if they do.
     */
    public boolean hasItem(final ItemInformation itemInformation) {
        return this.items.stream().filter(item -> item.getItemInformation() == itemInformation).count() > 0;
    }

    public boolean isDead() {
        return this.dead;
    }

    public void setDead(final boolean dead) {
        this.dead = dead;
    }


    @Override
    public String toString() {
        return this.getEntityInformation().toString();
    }

    @Override
    public boolean equals(final Object rhs) {
        return rhs instanceof Entity && this.getEntityInformation() == ((Entity) rhs).getEntityInformation();
    }

}
