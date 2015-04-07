package ca.carleton.magicrealm.entity.chit;

import ca.carleton.magicrealm.GUI.tile.Discoverable;
import ca.carleton.magicrealm.item.Item;

import java.util.ArrayList;
import java.util.List;


/**
 * A gold chit represents treasure sites on the map.
 *
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 12:02 PM
 */
public class GoldChit extends ColoredChit implements Discoverable {

    private static final long serialVersionUID = 345563872761671359L;

    private final List<Item> treasures = new ArrayList<Item>();

    public GoldChit(final int clearingNumber, final String treasureSiteName) {
        this.clearingNumber = clearingNumber;
        this.description = treasureSiteName;

    }

    public void addTreasure(final Item item){
        this.treasures.add(item);
    }

    public List<Item> getTreasure() {
        return this.treasures;
    }

    @Override
    public ChitColor getChitColor() {
        return ChitColor.GOLD;
    }

    @Override
    public String toString() {
        return this.description + " " + this.clearingNumber + " : hidden --> " + this.hidden;
    }
}
