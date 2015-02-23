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
public class Ledges extends AbstractTile {

    public Ledges() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(268);
        this.clearings[0].setY(314);
        this.clearings[0].setName("1");

        this.clearings[1].setX(363);
        this.clearings[1].setY(159);
        this.clearings[1].setName("2");

        this.clearings[2].setX(376);
        this.clearings[2].setY(273);
        this.clearings[3].setName("3");

        this.clearings[3].setX(209);
        this.clearings[3].setY(248);
        this.clearings[3].setName("4");

        this.clearings[4].setX(157);
        this.clearings[4].setY(127);
        this.clearings[4].setName("5");

        this.clearings[5].setX(174);
        this.clearings[5].setY(353);
        this.clearings[5].setName("6");
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.LEDGES;
    }

    @Override
    public TileType getTileType() {
        return TileType.MOUNTAIN;
    }
}
