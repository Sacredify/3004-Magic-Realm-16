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
public class Mountain extends AbstractTile {

    public Mountain() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(178);
        this.clearings[0].setY(169);
        this.clearings[0].setName("1");

        this.clearings[1].setX(122);
        this.clearings[1].setY(291);
        this.clearings[1].setName("2");

        this.clearings[2].setX(239);
        this.clearings[2].setY(288);
        this.clearings[2].setName("3");

        this.clearings[3].setX(249);
        this.clearings[3].setY(70);
        this.clearings[3].setName("4");

        this.clearings[4].setX(372);
        this.clearings[4].setY(288);
        this.clearings[4].setName("5");

        this.clearings[5].setX(343);
        this.clearings[5].setY(163);
        this.clearings[5].setName("6");
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.MOUNTAIN;
    }

    @Override
    public TileType getTileType() {
        return TileType.MOUNTAIN;
    }
}
