package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:06 PM
 */
public class Cavern extends AbstractTile {

    public Cavern() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(355);
        this.clearings[0].setY(149);

        this.clearings[1].setX(251);
        this.clearings[1].setY(86);

        this.clearings[2].setX(230);
        this.clearings[2].setY(208);

        this.clearings[3].setX(250);
        this.clearings[3].setY(355);

        this.clearings[4].setX(134);
        this.clearings[4].setY(151);

        this.clearings[5].setX(323);
        this.clearings[5].setY(278);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.CAVERN;
    }
}
