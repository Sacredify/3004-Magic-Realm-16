package ca.carleton.magicrealm.item.treasure;

import ca.carleton.magicrealm.game.phase.PhaseType;
import ca.carleton.magicrealm.item.ItemInformation;

/**
 * The treasures in the game.
 */
public class TreasureCollection {

    public Treasure[] treasures;

    public TreasureCollection() {
        this.treasures = new Treasure[26];
        this.treasures[0] = new Treasure(ItemInformation.CLOVEN_HOOF);
        this.treasures[1] = new Treasure(ItemInformation.DEFT_GLOVES);
        this.treasures[2] = new Treasure(ItemInformation.LUCKY_CHARM);
        this.treasures[3] = new Treasure(ItemInformation.MAP_OF_LOST_CITY);
        this.treasures[4] = new Treasure(ItemInformation.MAP_OF_LOST_CASTLE);
        this.treasures[5] = new Treasure(ItemInformation.MAP_OF_RUINS);
        this.treasures[6] = new Treasure(ItemInformation.SHOES_OF_STEALTH);
        this.treasures[7] = new Treasure(ItemInformation.CRYSTAL_BALL);
        this.treasures[8] = new PhaseTreasure(PhaseType.SEARCH, ItemInformation.ANCIENT_TELESCOPE);
        this.treasures[9] = new PhaseTreasure(PhaseType.HIDE, ItemInformation.CLOAK_OF_MIST);
        this.treasures[10] = new PhaseTreasure(PhaseType.SEARCH, ItemInformation.MAGIC_SPECTACLES);
        this.treasures[11] = new PhaseTreasure(PhaseType.TRADE, ItemInformation.REGENT_OF_JEWELS);
        this.treasures[12] = new PhaseTreasure(null, ItemInformation.ROYAL_SCEPTRE);
        this.treasures[13] = new PhaseTreasure(PhaseType.MOVE, ItemInformation.LEAGUE_BOOTS);
        this.treasures[14] = new PhaseTreasure(null, ItemInformation.SHIELDED_LANTERN);
        this.treasures[15] = new PhaseTreasure(PhaseType.MOVE, ItemInformation.DRAGON_ESSENCE);
        this.treasures[16] = new PhaseTreasure(null, ItemInformation.FLOWERS_OF_REST);
        this.treasures[17] = new PhaseTreasure(PhaseType.REST, ItemInformation.POULTICE_OF_HEALTH);
        this.treasures[18] = new PhaseTreasure(null, ItemInformation.TIMELESS_JEWEL);
        for (int i = 19; i < 26; i++) {
            this.treasures[i] = new Treasure(ItemInformation.GOLD);
        }
    }

}
