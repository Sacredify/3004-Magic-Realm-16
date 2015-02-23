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
public class LindenWoods extends AbstractTile {

    public LindenWoods() {
        this.clearings = Clearing.initializeClearingsArray(3,this);

        this.clearings[0].setX(247);
        this.clearings[0].setY(277);
        this.clearings[0].setName("2");

        this.clearings[1].setX(131);
        this.clearings[1].setY(153);
        this.clearings[1].setName("4");

        this.clearings[2].setX(297);
        this.clearings[2].setY(125);
        this.clearings[2].setName("5");
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.LINDEN_WOOD;
    }

    @Override
    public TileType getTileType() {
        return TileType.WOODS;
    }
}
