package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;
import ca.carleton.magicrealm.GUI.tile.TileType;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 12:37 PM
 */
public class Cliff extends AbstractTile {

    public Cliff() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(128);
        this.clearings[0].setY(152);
        this.clearings[0].setName("1");

        this.clearings[1].setX(131);
        this.clearings[1].setY(287);
        this.clearings[1].setName("2");

        this.clearings[2].setX(220);
        this.clearings[2].setY(244);
        this.clearings[2].setName("3");

        this.clearings[3].setX(360);
        this.clearings[3].setY(150);
        this.clearings[3].setName("4");

        this.clearings[4].setX(363);
        this.clearings[4].setY(283);
        this.clearings[4].setName("5");

        this.clearings[5].setX(249);
        this.clearings[5].setY(87);
        this.clearings[5].setName("6");
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.CLIFF;
    }

    @Override
    public TileType getTileType() {
        return TileType.MOUNTAIN;
    }
}
