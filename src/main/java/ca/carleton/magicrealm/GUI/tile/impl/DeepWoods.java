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
public class DeepWoods extends AbstractTile {

    public DeepWoods() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(159);
        this.clearings[0].setY(111);
        this.clearings[0].setName("1");

        this.clearings[1].setX(378);
        this.clearings[1].setY(217);
        this.clearings[1].setName("2");

        this.clearings[2].setX(300);
        this.clearings[2].setY(351);
        this.clearings[2].setName("3");

        this.clearings[3].setX(88);
        this.clearings[3].setY(220);
        this.clearings[3].setName("4");

        this.clearings[4].setX(148);
        this.clearings[4].setY(323);
        this.clearings[4].setName("5");

        this.clearings[5].setX(242);
        this.clearings[5].setY(243);
        this.clearings[5].setName("6");
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.DEEP_WOODS;
    }

    @Override
    public TileType getTileType() {
        return TileType.MOUNTAIN;
    }
}
