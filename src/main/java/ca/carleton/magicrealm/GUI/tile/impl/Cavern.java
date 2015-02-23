package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;
import ca.carleton.magicrealm.GUI.tile.TileType;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:06 PM
 */
public class Cavern extends AbstractTile {

    public Cavern() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(368);
        this.clearings[0].setY(145);
        this.clearings[0].setName("1");

        this.clearings[1].setX(245);
        this.clearings[1].setY(79);
        this.clearings[1].setName("2");

        this.clearings[2].setX(242);
        this.clearings[2].setY(180);
        this.clearings[2].setName("3");

        this.clearings[3].setX(252);
        this.clearings[3].setY(360);
        this.clearings[3].setName("4");

        this.clearings[4].setX(134);
        this.clearings[4].setY(151);
        this.clearings[4].setName("5");

        this.clearings[5].setX(296);
        this.clearings[5].setY(270);
        this.clearings[5].setName("6");
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.CAVERN;
    }

    @Override
    public TileType getTileType() {
        return TileType.CAVE;
    }
}
