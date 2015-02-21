package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:06 PM
 */
public class HighPass extends AbstractTile {

    public HighPass() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(330);
        this.clearings[0].setY(265);

        this.clearings[1].setX(245);
        this.clearings[1].setY(84);

        this.clearings[2].setX(358);
        this.clearings[2].setY(151);

        this.clearings[3].setX(198);
        this.clearings[3].setY(248);

        this.clearings[4].setX(246);
        this.clearings[4].setY(350);

        this.clearings[5].setX(139);
        this.clearings[5].setY(148);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.HIGH_PASS;
    }
}
