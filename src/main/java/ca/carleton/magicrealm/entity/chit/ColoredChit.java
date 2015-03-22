package ca.carleton.magicrealm.entity.chit;

import java.io.Serializable;

/**
 * Represents one of the colored (gold, red, yellow) chits to be displayed on the map.
 *
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 12:00 PM
 */
public abstract class ColoredChit implements Serializable {

    private static final long serialVersionUID = 6504002032717018780L;

    /**
     * The clearing number to place the chit in.
     */
    protected int clearingNumber;

    /**
     * The string in the rules that identifies the chit.
     */
    protected String description;

    /**
     * Whether or not it has been found.
     */
    protected boolean hidden = true;

    /**
     * The color of the chit.
     */
    public abstract ChitColor getChitColor();

    public int getClearingNumber() {
        return this.clearingNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void reveal() {
        this.hidden = false;
    }

    @Override
    public String toString() {
        return this.description + " : hidden --> " + this.hidden;
    }

}

