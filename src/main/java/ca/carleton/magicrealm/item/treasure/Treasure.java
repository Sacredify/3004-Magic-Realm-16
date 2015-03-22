package ca.carleton.magicrealm.item.treasure;

import ca.carleton.magicrealm.item.Item;
import ca.carleton.magicrealm.item.ItemInformation;

import java.util.Random;

/**
 * Created by anvargazizov on 2015-03-19.
 */
public class Treasure extends Item {

    private static final long serialVersionUID = -7160719805476333887L;

    protected static final Random random = new Random();

    private String name;

    @Override
    public ItemInformation getItemInformation() {
        return null;
    }

    public Treasure(String name){
        this.name = name;
        this.goldValue = 10 + random.nextInt(41);
    }

    public String getName() {
        return this.name;
    }
}
