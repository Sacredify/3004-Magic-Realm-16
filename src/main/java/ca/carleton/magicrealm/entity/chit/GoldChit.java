package ca.carleton.magicrealm.entity.chit;

/**
 * A gold chit represents treasure sites on the map.
 *
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 12:02 PM
 */
public class GoldChit extends ColoredChit {

    public GoldChit(final int clearingNumber, final String treasureSiteName) {
        this.clearingNumber = clearingNumber;
        this.description = treasureSiteName;
    }

    @Override
    public ChitColor getChitColor() {
        return ChitColor.GOLD;
    }
}
