package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 12:37 PM
 */
public class Cliff extends AbstractTile {

    public Cliff() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].addAdjacentClearing(this.clearings[5]);
        this.clearings[1].addAdjacentClearing(this.clearings[2]);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.CLIFF;
    }
}
