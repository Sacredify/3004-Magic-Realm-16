package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.EntityType;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.item.Item;

import java.util.List;
import java.util.Map;

/**
 * Represents a playable character within the Magic Realm.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 10/02/15
 * Time: 8:25 AM
 */
public abstract class Character extends Entity {

    public static final EntityType entityType = EntityType.CHARACTER;

    private List<Item> items;

    /**
     * The relationships this entity has with other entities.
     */
    public Map<Entity, Relationship> relationships;

    /**
     * Find the relationship with another entity.
     *
     * @param entity The given entity to find the relationship with.
     * @return The relationship.
     */
    public Relationship getRelationshipWith(final Entity entity) {
        return this.relationships.get(entity);
    }

    public void addRelationship(final Entity entity, final Relationship relationship) {
        this.relationships.putIfAbsent(entity, relationship);
    }

    @Override
    public EntityType getEntityType() {
        return entityType;
    }
}
