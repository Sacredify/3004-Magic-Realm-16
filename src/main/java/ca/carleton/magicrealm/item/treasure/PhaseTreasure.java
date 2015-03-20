package ca.carleton.magicrealm.item.treasure;

import java.util.Random;

/**
 * Created by anvargazizov on 2015-03-19.
 */
public class PhaseTreasure extends Treasure{
    String phase;
    String condition;
    String effect;
    int notoriety;
    int fame;


    public PhaseTreasure(String phase,String condition,String name,String effect){
        super(name);
        this.effect = effect;
        this.condition = condition;
        this.phase = phase;
        Random r = new Random();
        this.notoriety = 1 +r.nextInt(20);
        this.fame = -5 + r.nextInt(36);
    }


}
