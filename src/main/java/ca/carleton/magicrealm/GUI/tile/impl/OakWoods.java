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

        this.clearings[0].setX(346);
        this.clearings[0].setY(218);

        this.clearings[1].setX(139);
        this.clearings[1].setY(284);

        this.clearings[2].setX(194);
        this.clearings[2].setY(125);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.OAK_WOODS;
    }

    @Override
    public TileType getTileType() {
        return TileType.VALLEY;
    }
}
