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

        this.clearings[0].setX(170);
        this.clearings[0].setY(71);
        this.clearings[0].setName("1");

        this.clearings[1].setX(386);
        this.clearings[1].setY(204);
        this.clearings[1].setName("2");

        this.clearings[2].setX(316);
        this.clearings[2].setY(93);
        this.clearings[2].setName("3");

        this.clearings[3].setX(170);
        this.clearings[3].setY(363);
        this.clearings[3].setName("4");

        this.clearings[4].setX(187);
        this.clearings[4].setY(271);
        this.clearings[4].setName("5");

        this.clearings[5].setX(224);
        this.clearings[5].setY(183);
        this.clearings[5].setName("6");
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
