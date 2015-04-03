package ca.carleton.magicrealm.entity.chit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 12:53 PM
 */
public class LostCastle extends ColoredChit {

    public List<RedChit> soundChits = new ArrayList<RedChit>();

    public List<GoldChit> treasureChits = new ArrayList<GoldChit>();

    public LostCastle(final int clearingNumber, final String name) {
        this.clearingNumber = clearingNumber;
        this.description = name;
    }

    @Override
    public ChitColor getChitColor() {
        return ChitColor.LOST_CASTLE;
    }

    public List<GoldChit> getTreasureChits() {
        return treasureChits;
    }
}
