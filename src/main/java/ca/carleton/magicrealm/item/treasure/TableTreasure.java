package ca.carleton.magicrealm.item.treasure;

import java.util.Random;

/**
 * Created by anvargazizov on 2015-03-19.
 */
public class TableTreasure extends Treasure{
    String affectedTable;
    String specialAttribute;
    String effect;
    int notoriety;
    int fame;

    public TableTreasure(String affectedTable, String specialAttribute, String effect,String name) {
        super(name);
        this.affectedTable = affectedTable;
        this.specialAttribute = specialAttribute;
        this.effect = effect;
        Random r = new Random();
        this.notoriety = 1 +r.nextInt(20);
        this.fame = -5 + r.nextInt(36);


    }
}
