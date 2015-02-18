package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:06 PM
 */
public class OakWoods extends AbstractTile {

    public OakWoods() {
        this.clearings = Clearing.initializeClearingsArray(3,this);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.OAK_WOODS;
    }
}
