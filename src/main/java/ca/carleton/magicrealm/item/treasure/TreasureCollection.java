package ca.carleton.magicrealm.item.treasure;

/**
 * Created by anvargazizov on 2015-03-19.
 */
public class TreasureCollection {
    Treasure[] treasures;




    public TreasureCollection(){
        treasures = new Treasure[26];
        //Add CLOVEN_HOOF
        treasures[0] = new  TableTreasure("ALL", "AFFECTS_CLEARING","ADD_ONE","CLOVEN_HOOF");
        //Add DEFT GLOVES
        treasures[1] = new  TableTreasure("LOOT", "NONE","ROLL_ONE_DIE","DEFT_GLOVES");
        //Add LUCKY CHARM
        treasures[2] = new  TableTreasure("ALL", "NONE","ROLL_ONE_DIE","LUCKY_CHARM");
        //Add MAP OF LOST CITY
        treasures[2] = new  TableTreasure("LOCATE", "NONE","SUBTRACT_ONE","MAP_OF_LOST_CITY");
        //Add MAP OF LOST CASTLE
        treasures[3] = new  TableTreasure("LOCATE", "NONE","SUBTRACT_ONE","MAP_OF_LOST_CASTLE");
        //Add MAP OF RUINS
        treasures[4] = new  TableTreasure("LOCATE", "NONE","SUBTRACT_ONE","MAP_OF_LOST_RUINS");
        //Add SHOES OF STEALTH
        treasures[5] = new  TableTreasure("HIDE", "NONE","ROLL_ONE_DIE","SHOES_OF_STEALTH");
        //Add CRYSTAL BALL
        treasures[6] = new PhaseTreasure("PEER","NONE","CRYSTAL_BALL","CAN_SEE_ANY_CLERING");
        //Add ANCIENT TELESCOPE
        treasures[6] = new PhaseTreasure("PEER","LOCATION_MOUNTAIN_CLEARING","ANCIENT_TELESCOPE","CAN_SEE_ANY_CLEARING_EXTRA_PHASE");
        //Add CLOACK OF MIST
        treasures[7] = new PhaseTreasure("HIDE","NONE","CLOAK_OF_MIST","EXTRA_PHASE");
        //Add MAGIC SPECTACLES SPECTACLES
        treasures[8] = new PhaseTreasure("SEARCH","NONE","MAGIC_SPECTACLES","EXTRA_PHASE");
        //Add REGENT OF JEWELS
        treasures[9] = new PhaseTreasure("TRADE","NONE","REGENT_OF_JEWELS","EXTRA_PHASE");
        //Add ROYAL SCEPTRE
        treasures[10] = new PhaseTreasure("HIRE","NONE","ROYAL_SCEPTRE","EXTRA_PHASE");
        //Add  LEAGUE BOOTS
        treasures[11] = new PhaseTreasure("MOVE","NONE","LEAGUE_BOOTS","EXTRA_PHASE");
        //Add SHIELDED LANTERN
        treasures[12] = new PhaseTreasure("ANY","LOCATION_CAVE_CLEARING","SHIELDED_LANTERN","EXTRA_PHASE");
        //Add DRAGON ESSENCE
        treasures[13] = new PhaseTreasure("MOVE","NONE","DRAGON_ESSENCE","SMOKE_APPEARS_AFTER_EXIT");
        //Add FLOWERS OF REST
        treasures[14] = new PhaseTreasure("ALL","NONE","FLOWERS_OF_REST","SKIP");
        //Add POULTICE OF HEALTH
        treasures[15] = new PhaseTreasure("REST","NONE","POULTICE_OF_HEALTH","EFFECTX2");
        //Add TIMELESS JEWEL
        treasures[16] = new PhaseTreasure("ALL","NONE","TIMELESS_JEWEL","CHOOSE");
        for(int i = 17 ; i < 26; i++){
            treasures[i] = new Treasure("GOLD");
        }
    }

}
