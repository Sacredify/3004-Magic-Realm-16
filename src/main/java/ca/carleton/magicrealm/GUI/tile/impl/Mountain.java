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

        this.clearings[0].setX(188);
        this.clearings[0].setY(186);

        this.clearings[1].setX(126);
        this.clearings[1].setY(280);

        this.clearings[2].setX(265);
        this.clearings[2].setY(290);

        this.clearings[3].setX(245);
        this.clearings[3].setY(78);

        this.clearings[4].setX(366);
        this.clearings[4].setY(290);
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
