package ca.carleton.magicrealm.entity.chit;
import ca.carleton.magicrealm.item.treasure.Treasure;
import java.util.ArrayList;

import java.util.Random;


/**
 * A gold chit represents treasure sites on the map.
 *
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 12:02 PM
 */
public class GoldChit extends ColoredChit {

    int weight;
    int value;
    ArrayList<Treasure> treasure;

    public GoldChit(final int clearingNumber, final String treasureSiteName) {
        this.clearingNumber = clearingNumber;
        this.description = treasureSiteName;

    }

    public void addTreasure(Treasure t){
        treasure.add(t);
    }

    @Override
    public ChitColor getChitColor() {
        return ChitColor.GOLD;
    }
}
