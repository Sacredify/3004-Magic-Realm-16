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

    public abstract EntityType getEntityType();

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
}
