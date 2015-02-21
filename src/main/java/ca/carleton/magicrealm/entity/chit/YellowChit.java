package ca.carleton.magicrealm.entity.chit;

/**
 * A yellow chit represents a warning chit on the map.
 *
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 12:28 PM
 */
public class YellowChit extends ColoredChit {

    public YellowChit(final int clearingNumber, final String warning) {
        this.clearingNumber = clearingNumber;
        this.description = warning;
    }

    @Override
    public ChitColor getChitColor() {
        return ChitColor.YELLOW;
    }
}
