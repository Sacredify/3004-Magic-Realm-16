package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.Interactable;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.item.treasure.PhaseTreasure;
import ca.carleton.magicrealm.item.treasure.Treasure;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.item.treasure.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jdk.internal.dynalink.support.Guards.isInstance;

/**
 * Represents a playable character within the Magic Realm.
 * <p/>
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

    private ArrayList<Treasure> treasures;

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

    public void pickUpTreasure(Treasure T){
        treasures.add(T);
    }

    public ArrayList<String> getExtraPhases(){
        ArrayList<String> extraPhases = new ArrayList<String>();
        for(int i = 0 ; i < treasures.size(); i++){

            if(treasures.get(i).getClass().getName()=="PhaseTreasure"){
                PhaseTreasure p = (PhaseTreasure) treasures.get(i);
                extraPhases.add(p.getPhase());
            }
        }
        return extraPhases;
    }


    public ArrayList<TableTreasure> getTableTreasures(){
        ArrayList<TableTreasure> tableTreasures = new ArrayList<TableTreasure>();
        for(int i = 0 ; i < treasures.size(); i++){
            if(treasures.get(i).getClass().getName()=="TableTreasure"){
                TableTreasure p = (TableTreasure) treasures.get(i);
                tableTreasures.add(p);
            }
        }
        return tableTreasures;
    }

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
