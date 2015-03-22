package ca.carleton.magicrealm.item.treasure;

/**
 * Created by anvargazizov on 2015-03-19.
 */
public class TreasureCollection {

    public Treasure[] treasures;

    public TreasureCollection(){
        this.treasures = new Treasure[26];
        //Add CLOVEN_HOOF
        this.treasures[0] = new  TableTreasure("ALL", "AFFECTS_CLEARING","ADD_ONE","CLOVEN_HOOF");
        //Add DEFT GLOVES
        this.treasures[1] = new  TableTreasure("LOOT", "NONE","ROLL_ONE_DIE","DEFT_GLOVES");
        //Add LUCKY CHARM
        this.treasures[2] = new  TableTreasure("ALL", "NONE","ROLL_ONE_DIE","LUCKY_CHARM");
        //Add MAP OF LOST CITY
        this.treasures[3] = new  TableTreasure("LOCATE", "NONE","SUBTRACT_ONE","MAP_OF_LOST_CITY");
        //Add MAP OF LOST CASTLE
        this.treasures[4] = new  TableTreasure("LOCATE", "NONE","SUBTRACT_ONE","MAP_OF_LOST_CASTLE");
        //Add MAP OF RUINS
        this.treasures[5] = new  TableTreasure("LOCATE", "NONE","SUBTRACT_ONE","MAP_OF_LOST_RUINS");
        //Add SHOES OF STEALTH
        this.treasures[6] = new  TableTreasure("HIDE", "NONE","ROLL_ONE_DIE","SHOES_OF_STEALTH");
        //Add CRYSTAL BALL
        this.treasures[7] = new PhaseTreasure("PEER","NONE","CRYSTAL_BALL","CAN_SEE_ANY_CLERING");
        //Add ANCIENT TELESCOPE
        this.treasures[8] = new PhaseTreasure("PEER","LOCATION_MOUNTAIN_CLEARING","ANCIENT_TELESCOPE","CAN_SEE_ANY_CLEARING_EXTRA_PHASE");
        //Add CLOACK OF MIST
        this.treasures[9] = new PhaseTreasure("HIDE","NONE","CLOAK_OF_MIST","EXTRA_PHASE");
        //Add MAGIC SPECTACLES SPECTACLES
        this.treasures[10] = new PhaseTreasure("SEARCH","NONE","MAGIC_SPECTACLES","EXTRA_PHASE");
        //Add REGENT OF JEWELS
        this.treasures[11] = new PhaseTreasure("TRADE","NONE","REGENT_OF_JEWELS","EXTRA_PHASE");
        //Add ROYAL SCEPTRE
        this.treasures[12] = new PhaseTreasure("HIRE","NONE","ROYAL_SCEPTRE","EXTRA_PHASE");
        //Add  LEAGUE BOOTS
        this.treasures[13] = new PhaseTreasure("MOVE","NONE","LEAGUE_BOOTS","EXTRA_PHASE");
        //Add SHIELDED LANTERN
        this.treasures[14] = new PhaseTreasure("ANY","LOCATION_CAVE_CLEARING","SHIELDED_LANTERN","EXTRA_PHASE");
        //Add DRAGON ESSENCE
        this.treasures[15] = new PhaseTreasure("MOVE","NONE","DRAGON_ESSENCE","SMOKE_APPEARS_AFTER_EXIT");
        //Add FLOWERS OF REST
        this.treasures[16] = new PhaseTreasure("ALL","NONE","FLOWERS_OF_REST","SKIP");
        //Add POULTICE OF HEALTH
        this.treasures[17] = new PhaseTreasure("REST","NONE","POULTICE_OF_HEALTH","EFFECTX2");
        //Add TIMELESS JEWEL
        this.treasures[18] = new PhaseTreasure("ALL","NONE","TIMELESS_JEWEL","CHOOSE");
        for(int i = 19 ; i < 26; i++){
            this.treasures[i] = new Treasure("GOLD");
        }
    }

}
