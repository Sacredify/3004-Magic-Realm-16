package ca.carleton.magicrealm.GUI.tile;

import ca.carleton.magicrealm.entity.chit.ColoredChit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/02/15
 * Time: 7:54 PM
 */
public abstract class AbstractTile implements Serializable {

    private static final long serialVersionUID = -5228583595459151067L;

    protected Clearing[] clearings;

    private final List<ColoredChit> chits = new ArrayList<ColoredChit>();

    public abstract TileInformation getTileInformation();

    public abstract TileType getTileType();

    // refactor this later
    private int angle;

    public Clearing[] getClearings() {
        return this.clearings;
    }

    public int getAngle() {
        return this.angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public List<ColoredChit> getChits() {
        return this.chits;
    }

    public void addChit(final ColoredChit chit) {
        this.chits.add(chit);
    }
}
