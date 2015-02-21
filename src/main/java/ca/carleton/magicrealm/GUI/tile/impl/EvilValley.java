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
public class EvilValley extends AbstractTile {

    public EvilValley() {
        this.clearings = Clearing.initializeClearingsArray(4,this);

        this.clearings[0].setX(187);
        this.clearings[0].setY(321);

        this.clearings[1].setX(133);
        this.clearings[1].setY(153);

        this.clearings[2].setX(306);
        this.clearings[2].setY(121);

        this.clearings[3].setX(358);
        this.clearings[3].setY(287);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.EVIL_VALLEY;
    }

    @Override
    public TileType getTileType() {
        return TileType.VALLEY;
    }
}
