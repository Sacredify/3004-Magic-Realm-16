package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;
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
        this.clearings[0].setY(85);

        this.clearings[1].setX(135);
        this.clearings[1].setY(150);

        // 4
        this.clearings[2].setX(248);
        this.clearings[2].setY(282);

        // 5
        this.clearings[3].setX(359);
        this.clearings[3].setY(154);
        this.clearings[3].setDwelling(Dwelling.HOUSE);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.CURST_VALLEY;
    }
}
