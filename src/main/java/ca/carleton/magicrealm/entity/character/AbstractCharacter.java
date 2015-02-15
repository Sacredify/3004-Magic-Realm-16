package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.Interactable;
import ca.carleton.magicrealm.entity.Relationship;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a playable character within the Magic Realm.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 10/02/15
 * Time: 8:25 AM
 */
public abstract class AbstractCharacter extends Entity {

    /**
     * The relationships this entity has with other entities.
     */
    protected Map<Interactable, Relationship> relationships = new HashMap<Interactable, Relationship>();

    /**
     * Find the relationship with another entity.
     *
     * @param entity The given entity to find the relationship with.
     * @return The relationship.
     */
    public Relationship getRelationshipWith(final Entity entity) {
        return this.relationships.get(entity);
    }

    public void addRelationship(final Interactable entity, final Relationship relationship) {
        this.relationships.putIfAbsent(entity, relationship);
    }

}
