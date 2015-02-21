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
public class BorderLand extends AbstractTile {

    public BorderLand() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(199);
        this.clearings[0].setY(71);

        this.clearings[1].setX(413);
        this.clearings[1].setY(217);

        this.clearings[2].setX(320);
        this.clearings[2].setY(200);

        this.clearings[3].setX(235);
        this.clearings[3].setY(324);

        this.clearings[4].setX(147);
        this.clearings[4].setY(264);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.BORDER_LAND;
    }

    @Override
    public TileType getTileType() {
        return TileType.CAVE;
    }
}
