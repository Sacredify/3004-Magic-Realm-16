package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:06 PM
 */
public class Ledges extends AbstractTile {

    public Ledges() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(275);
        this.clearings[0].setY(305);

        this.clearings[1].setX(357);
        this.clearings[1].setY(133);

        this.clearings[2].setX(394);
        this.clearings[2].setY(261);

        this.clearings[3].setX(246);
        this.clearings[3].setY(206);

        this.clearings[4].setX(134);
        this.clearings[4].setY(130);

        this.clearings[5].setX(164);
        this.clearings[5].setY(354);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.LEDGES;
    }
}
