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
public class HighPass extends AbstractTile {

    public HighPass() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(336);
        this.clearings[0].setY(256);
        this.clearings[0].setName("1");

        this.clearings[1].setX(244);
        this.clearings[1].setY(87);
        this.clearings[1].setName("2");

        this.clearings[2].setX(350);
        this.clearings[2].setY(153);
        this.clearings[2].setName("3");

        this.clearings[3].setX(217);
        this.clearings[3].setY(233);
        this.clearings[3].setName("4");

        this.clearings[4].setX(250);
        this.clearings[4].setY(348);
        this.clearings[4].setName("5");

        this.clearings[5].setX(133);
        this.clearings[5].setY(150);
        this.clearings[5].setName("6");
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.HIGH_PASS;
    }

    @Override
    public TileType getTileType() {
        return TileType.CAVE;
    }
}
