package ca.carleton.magicrealm.entity.chit;
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

    public GoldChit(final int clearingNumber, final String treasureSiteName) {
        this.clearingNumber = clearingNumber;
        this.description = treasureSiteName;
        Random randomGenerator = new Random();
        this.value = 50 - randomGenerator.nextInt(41);

    }

    @Override
    public ChitColor getChitColor() {
        return ChitColor.GOLD;
    }
}
