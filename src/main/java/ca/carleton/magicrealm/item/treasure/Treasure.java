package ca.carleton.magicrealm.item.treasure;

import ca.carleton.magicrealm.item.Item;
import ca.carleton.magicrealm.item.ItemInformation;

import java.util.Random;


public class Treasure extends Item {

    private static final long serialVersionUID = -7160719805476333887L;

    private static final Random random = new Random();

    protected int notoriety;

    protected int fame;

    protected ItemInformation treasureInformation;

    public Treasure(final ItemInformation treasureInformation) {
        this.treasureInformation = treasureInformation;
        this.goldValue = 10 + random.nextInt(41);

        if (this.treasureInformation != ItemInformation.GOLD) {
            this.notoriety = 1 + random.nextInt(20);
            this.fame = -5 + random.nextInt(36);
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
}
