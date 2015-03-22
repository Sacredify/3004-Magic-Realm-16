package ca.carleton.magicrealm.entity.chit;

import ca.carleton.magicrealm.item.treasure.Treasure;

import java.util.ArrayList;
import java.util.List;


/**
 * A gold chit represents treasure sites on the map.
 *
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 12:02 PM
 */
public class GoldChit extends ColoredChit {

    private static final long serialVersionUID = 345563872761671359L;

    private List<Treasure> treasure = new ArrayList<Treasure>();

    public GoldChit(final int clearingNumber, final String treasureSiteName) {
        this.clearingNumber = clearingNumber;
        this.description = treasureSiteName;

    }

    public void addTreasure(Treasure t){
        this.treasure.add(t);
    }

    public List<Treasure> getTreasure() {
        return this.treasure;
    }

    @Override
    public ChitColor getChitColor() {
        return ChitColor.GOLD;
    }
}
