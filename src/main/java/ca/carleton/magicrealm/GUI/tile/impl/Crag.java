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
public class Crag extends AbstractTile {

    public Crag() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(227);
        this.clearings[0].setY(367);
        this.clearings[0].setName("1");

        this.clearings[1].setX(248);
        this.clearings[1].setY(84);
        this.clearings[1].setName("2");

        this.clearings[2].setX(171);
        this.clearings[2].setY(174);
        this.clearings[2].setName("3");

        this.clearings[3].setX(320);
        this.clearings[3].setY(288);
        this.clearings[3].setName("4");

        this.clearings[4].setX(312);
        this.clearings[4].setY(187);
        this.clearings[4].setName("5");

        this.clearings[5].setX(164);
        this.clearings[5].setY(282);
        this.clearings[5].setName("6");
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.CRAG;
    }

    @Override
    public TileType getTileType() {
        return TileType.MOUNTAIN;
    }
}
