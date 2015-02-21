package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:06 PM
 */
public class DeepWoods extends AbstractTile {

    public DeepWoods() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(215);
        this.clearings[0].setY(284);

        this.clearings[1].setX(242);
        this.clearings[1].setY(178);

        this.clearings[2].setX(320);
        this.clearings[2].setY(346);

        this.clearings[3].setX(117);
        this.clearings[3].setY(214);

        this.clearings[4].setX(330);
        this.clearings[4].setY(248);

        this.clearings[5].setX(321);
        this.clearings[5].setY(87);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.DEEP_WOODS;
    }
}
