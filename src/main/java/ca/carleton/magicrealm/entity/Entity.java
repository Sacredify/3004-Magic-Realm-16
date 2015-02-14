package ca.carleton.magicrealm.entity;

import ca.carleton.magicrealm.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Entity within the Magic Realm
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 09/02/15
 * Time: 5:45 PM
 */
public abstract class Entity {

    /**
     * The vulnerability of an entity is their health. Determined by their character.
     */
    protected Vulnerability vulnerability;

    /**
     * The weight of an entity is the same as their vulnerability. Maybe we can move this out later.
     */
    protected Vulnerability weight;

    /**
     * The list of items the entity is currently holding.
     * TODO Is this relevant for all entities? Or only characters?
     */
    private List<Item> items = new ArrayList<Item>();

    public abstract EntityInformation getEntityInformation();

    public Vulnerability getVulnerability() {
        return this.vulnerability;
    }

    public Vulnerability getWeight() {
        return this.weight;
    }

    public void addItem(final Item item) {
        this.items.add(item);
    }

}
