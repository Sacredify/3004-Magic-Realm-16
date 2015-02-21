package ca.carleton.magicrealm.entity.chit;

/**
 * A red chit represents the sound chits, for monster spawns.
 *
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 12:19 PM
 */
public class RedChit extends ColoredChit {

    public RedChit(final String sound) {
        this.clearingNumber = -1;
        this.description = sound;
    }

    @Override
    public ChitColor getChitColor() {
        return ChitColor.RED;
    }
}
