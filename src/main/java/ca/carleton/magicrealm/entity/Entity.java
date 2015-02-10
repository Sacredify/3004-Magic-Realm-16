package ca.carleton.magicrealm.entity;

import java.util.Map;

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
    private Vulnerability vulnerability;

    /**
     * The weight of an entity is the same as their vulnerability. Maybe we can move this out later.
     */
    private Vulnerability weight;

    /**
     * The relationships this entity has with other entities.
     */
    public Map<Entity, Relationship> relationships;

    public abstract EntityType getEntityType();

    /**
     * Find the relationship with another entity.
     *
     * @param entity The given entity to find the relationship with.
     * @return The relationship.
     */
    public Relationship getRelationshipWith(final Entity entity) {
        return this.relationships.get(entity);
    }

    public Vulnerability getVulnerability() {
        return this.vulnerability;
    }

    public void setVulnerability(final Vulnerability vulnerability) {
        this.vulnerability = vulnerability;
    }

    public Vulnerability getWeight() {
        return this.weight;
    }

    public void setWeight(final Vulnerability weight) {
        this.weight = weight;
    }

    public void addRelationship(final Entity entity, final Relationship relationship) {
        this.relationships.putIfAbsent(entity, relationship);
    }
}
