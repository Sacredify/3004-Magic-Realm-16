package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:06 PM
 */
public class Ruins extends AbstractTile {

    public Ruins() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(129);
        this.clearings[0].setY(153);

        this.clearings[1].setX(312);
        this.clearings[1].setY(86);

        this.clearings[2].setX(355);
        this.clearings[2].setY(325);

        this.clearings[3].setX(222);
        this.clearings[3].setY(228);

        this.clearings[4].setX(153);
        this.clearings[4].setY(309);

        this.clearings[5].setX(330);
        this.clearings[5].setY(202);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.RUINS;
    }
}
