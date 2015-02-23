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
public class OakWoods extends AbstractTile {

    public OakWoods() {
        this.clearings = Clearing.initializeClearingsArray(3,this);

        this.clearings[0].setX(359);
        this.clearings[0].setY(208);
        this.clearings[0].setName("2");

        this.clearings[1].setX(134);
        this.clearings[1].setY(283);
        this.clearings[1].setName("4");

        this.clearings[2].setX(191);
        this.clearings[2].setY(117);
        this.clearings[2].setName("5");
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.OAK_WOODS;
    }

    @Override
    public TileType getTileType() {
        return TileType.WOODS;
    }
}
