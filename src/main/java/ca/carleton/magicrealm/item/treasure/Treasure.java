package ca.carleton.magicrealm.item.treasure;

import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.item.Item;
import ca.carleton.magicrealm.item.ItemInformation;

import java.util.Random;

/**
 * Represents all treasures within the magic realm.
 */
public class Treasure extends Item {

    private static final long serialVersionUID = -7160719805476333887L;

    private static final Random random = new Random();

    protected int notoriety;

    protected int fame;

    protected ItemInformation treasureInformation;

    protected boolean greatTreasure;

    public Treasure(final ItemInformation treasureInformation) {
        this.treasureInformation = treasureInformation;
        this.goldValue = 10 + random.nextInt(41);

        if (this.treasureInformation != ItemInformation.GOLD) {
            this.notoriety = 1 + random.nextInt(20);
            this.fame = -5 + random.nextInt(36);
            // ASSUMPTION - A great treasure is simply a treasure with high notoriety or fame.
            this.greatTreasure = this.notoriety > 15 || this.fame > 25 || this.goldValue > 30;
        }
    }

    public void addWorthToCharacter(final AbstractCharacter character) {
        character.addGold(this.goldValue);
        character.addNotoriety(this.notoriety);
        character.addFame(this.fame);
        if (this.isGreatTreasure()) {
            character.addGreatTreasure(1);
        }
    }

    public void removeWorthFromCharacter(final AbstractCharacter character) {
        character.addGold(-this.goldValue);
        character.addNotoriety(-this.notoriety);
        character.addFame(-this.fame);
        if (this.isGreatTreasure()) {
            character.addGreatTreasure(-1);
        }
    }

    @Override
    public ItemInformation getItemInformation() {
        return this.treasureInformation;
    }

    public int getNotoriety() {
        return this.notoriety;
    }

    public int getFame() {
        return this.fame;
    }

    public boolean isGreatTreasure() {
        return this.greatTreasure;
    }
}
