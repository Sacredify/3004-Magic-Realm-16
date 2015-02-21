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
public class NutWoods extends AbstractTile {

    public NutWoods() {
        this.clearings = Clearing.initializeClearingsArray(3,this);

        this.clearings[0].setX(187);
        this.clearings[0].setY(112);

        this.clearings[1].setX(353);
        this.clearings[1].setY(149);

        this.clearings[2].setX(246);
        this.clearings[2].setY(277);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.NUT_WOODS;
    }

    @Override
    public TileType getTileType() {
        return TileType.VALLEY;
    }
}
