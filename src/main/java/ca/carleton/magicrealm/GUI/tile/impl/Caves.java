package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:06 PM
 */
public class Caves extends AbstractTile {

    public Caves() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(354);
        this.clearings[0].setX(278);

        this.clearings[1].setX(128);
        this.clearings[1].setY(280);

        this.clearings[2].setX(262);
        this.clearings[2].setX(189);

        this.clearings[3].setX(250);
        this.clearings[3].setY(335);

        this.clearings[4].setX(127);
        this.clearings[4].setY(150);

        this.clearings[5].setX(329);
        this.clearings[5].setY(80);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.CAVES;
    }
}
