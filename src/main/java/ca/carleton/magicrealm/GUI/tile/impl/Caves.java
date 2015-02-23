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
public class Caves extends AbstractTile {

    public Caves() {
        this.clearings = Clearing.initializeClearingsArray(5,this);

        this.clearings[0].setX(361);
        this.clearings[0].setY(286);
        this.clearings[0].setName("1");

        this.clearings[1].setX(135);
        this.clearings[1].setY(282);
        this.clearings[1].setName("2");

        this.clearings[2].setX(228);
        this.clearings[2].setX(220);
        this.clearings[2].setName("3");

        this.clearings[3].setX(275);
        this.clearings[3].setY(350);
        this.clearings[3].setName("4");

        this.clearings[4].setX(134);
        this.clearings[4].setY(157);
        this.clearings[4].setName("5");
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.CAVES;
    }

    @Override
    public TileType getTileType() {
        return TileType.CAVE;
    }
}
