package ca.carleton.magicrealm.item.treasure;

import ca.carleton.magicrealm.item.Item;
import java.util.Random;
import ca.carleton.magicrealm.item.ItemInformation;

/**
 * Created by anvargazizov on 2015-03-19.
 */
public class Treasure extends Item {

    int cost;
    String name;

    @Override
    public ItemInformation getItemInformation() {
        return null;
    }

    public Treasure(String name){
        this.name = name;
        Random rn = new Random();
        cost  = 10 + rn.nextInt(41);
    }
}
