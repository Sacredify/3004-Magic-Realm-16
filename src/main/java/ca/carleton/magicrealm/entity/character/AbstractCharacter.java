package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.Interactable;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.item.Item;
import ca.carleton.magicrealm.item.treasure.Treasure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a playable character within the Magic Realm.
 * <p>
 * Created with IntelliJ IDEA.
 * Date: 10/02/15
 * Time: 8:25 AM
 */
public abstract class AbstractCharacter extends Entity implements Serializable {

    private static final long serialVersionUID = 5076980254158357930L;

    private int currentGold;

    private int currentNotoriety;

    private int currentFame;

    private int currentSpellsCount;

    private int currentGreatTreasuresCount;

    private boolean blocked;

    // Used by combat. Dead resets the character, wounded makes them wound chits. Fatigued if they fatigued themselves. Reset after combat.
    private boolean dead;

    private boolean isFatigued;

    private boolean wounded;

    /**
     * All characters start hidden.
     */ {
        this.setHidden(true);
        this.setBlocked(false);
    }

    /**
     * The relationships this entity has with other entities.
     */
    protected Map<Interactable, Relationship> relationships = new HashMap<Interactable, Relationship>();

    protected List<ActionChit> actionChits = new ArrayList<ActionChit>();

    /**
     * Find the relationship with another interactable entity.
     *
     * @param interactable The given entity to find the relationship with.
     * @return The relationship.
     */
    public Relationship getRelationshipWith(final Interactable interactable) {

        Relationship relationship = this.relationships.get(interactable);

        if (relationship == null) {
            relationship = Relationship.NEUTRAL;
        }

        return relationship;
    }

    public void addRelationship(final Interactable entity, final Relationship relationship) {
        this.relationships.putIfAbsent(entity, relationship);
    }

    @Override
    public void addItem(final Item item) {
        super.addItem(item);
        if (item instanceof Treasure) {
            ((Treasure) item).addWorthToCharacter(this);
        }
    }

    @Override
    public void removeItem(final Item item) {
        super.removeItem(item);
        if (item instanceof Treasure) {
            ((Treasure) item).removeWorthFromCharacter(this);
        }
    }

    public List<ActionChit> getActionChits() {
        return this.actionChits;
    }

    public void addActionChit(final ActionChit actionChit) {
        this.actionChits.add(actionChit);
    }

    public int getCurrentGreatTreasuresCount() {
        return this.currentGreatTreasuresCount;
    }

    public void addGreatTreasure(int currentGreatTreasuresCount) {
        this.currentGreatTreasuresCount += currentGreatTreasuresCount;
    }

    public int getCurrentGold() {
        return this.currentGold;
    }

    public void addGold(int currentGold) {
        this.currentGold += currentGold;
    }

    public int getCurrentNotoriety() {
        return this.currentNotoriety;
    }

    public void addNotoriety(int currentNotoriety) {
        this.currentNotoriety += currentNotoriety;
    }

    public int getCurrentFame() {
        return this.currentFame;
    }

    public void addFame(int currentFame) {
        this.currentFame += currentFame;
    }

    public int getCurrentSpellsCount() {
        return this.currentSpellsCount;
    }

    public void addCurrentSpells(int currentSpellsCount) {
        this.currentSpellsCount += currentSpellsCount;
    }

    @Override
    public boolean equals(final Object rhs) {
        return rhs instanceof AbstractCharacter && this.getEntityInformation() == ((AbstractCharacter) rhs).getEntityInformation();
    }

    @Override
    public int hashCode() {
        return this.getEntityInformation().hashCode();
    }

    public boolean isBlocked() {
        return this.blocked;
    }

    public void setBlocked(final boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isDead() {
        return this.dead;
    }

    public void setDead(final boolean dead) {
        this.dead = dead;
    }

    public boolean isWounded() {
        return this.wounded;
    }

    public void setWounded(final boolean wounded) {
        this.wounded = wounded;
    }

    public boolean isFatigued() {
        return this.isFatigued;
    }

    public void setFatigued(final boolean isFatigued) {
        this.isFatigued = isFatigued;
    }
}
