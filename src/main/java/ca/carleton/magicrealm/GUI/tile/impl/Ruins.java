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
public class Ruins extends AbstractTile {

    public Ruins() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(157);
        this.clearings[0].setY(151);
        this.clearings[0].setName("1");

        this.clearings[1].setX(323);
        this.clearings[1].setY(90);
        this.clearings[1].setName("2");

        this.clearings[2].setX(370);
        this.clearings[2].setY(288);
        this.clearings[2].setName("3");

        this.clearings[3].setX(234);
        this.clearings[3].setY(262);
        this.clearings[3].setName("4");

        this.clearings[4].setX(147);
        this.clearings[4].setY(331);
        this.clearings[4].setName("5");

        this.clearings[5].setX(328);
        this.clearings[5].setY(184);
        this.clearings[5].setName("6");
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.RUINS;
    }

    @Override
    public TileType getTileType() {
        return TileType.CAVE;
    }
}
