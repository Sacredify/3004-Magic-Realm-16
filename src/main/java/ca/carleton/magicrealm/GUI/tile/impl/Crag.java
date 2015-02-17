package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:06 PM
 */
public class Crag extends AbstractTile {

    public Crag() {
        this.clearings = new Clearing[6];
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.CRAG;
    }
}
