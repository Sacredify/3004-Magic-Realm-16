package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:06 PM
 */
public class Crag extends AbstractTile {

    public Crag() {
        this.clearings = Clearing.initializeClearingsArray(6,this);

        this.clearings[0].setX(204);
        this.clearings[0].setY(353);

        this.clearings[1].setX(269);
        this.clearings[1].setY(90);

        this.clearings[2].setX(177);
        this.clearings[2].setY(148);

        this.clearings[3].setX(287);
        this.clearings[3].setY(384);

        this.clearings[4].setX(312);
        this.clearings[4].setY(187);

        this.clearings[5].setX(163);
        this.clearings[5].setY(272);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.CRAG;
    }
}
