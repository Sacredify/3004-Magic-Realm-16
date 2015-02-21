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

        this.clearings[0].setX(125);
        this.clearings[0].setY(151);

        this.clearings[1].setX(128);
        this.clearings[1].setY(284);

        this.clearings[2].setX(247);
        this.clearings[2].setY(217);

        this.clearings[3].setX(359);
        this.clearings[3].setY(146);

        this.clearings[4].setX(359);
        this.clearings[4].setY(285);

        this.clearings[5].setX(249);
        this.clearings[5].setY(84);
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
