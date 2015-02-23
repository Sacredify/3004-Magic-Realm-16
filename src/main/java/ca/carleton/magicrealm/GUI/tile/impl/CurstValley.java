package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.entity.chit.Dwelling;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:06 PM
 */
public class CurstValley extends AbstractTile {

    public CurstValley() {
        this.clearings = Clearing.initializeClearingsArray(4,this);
        this.clearings[0].setX(246);
        this.clearings[0].setY(83);
        this.clearings[0].setName("1");

        this.clearings[1].setX(133);
        this.clearings[1].setY(149);
        this.clearings[1].setName("2");

        // 4
        this.clearings[2].setX(243);
        this.clearings[2].setY(285);
        this.clearings[2].setName("2");

        // 5
        this.clearings[3].setX(359);
        this.clearings[3].setY(153);
        this.clearings[3].setName("3");
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.CURST_VALLEY;
    }

    @Override
    public TileType getTileType() {
        return TileType.VALLEY;
    }
}
