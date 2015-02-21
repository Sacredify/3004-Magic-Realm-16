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
public class DarkValley extends AbstractTile {

    public DarkValley() {
        this.clearings = Clearing.initializeClearingsArray(4,this);

        this.clearings[0].setX(348);
        this.clearings[0].setY(153);

        this.clearings[1].setX(249);
        this.clearings[1].setY(92);

        // 4
        this.clearings[2].setX(191);
        this.clearings[2].setY(244);

        // 4
        this.clearings[3].setX(359);
        this.clearings[3].setY(280);
        //this.clearings[3].setDwelling(Dwelling.GUARD);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.DARK_VALLEY;
    }

    @Override
    public TileType getTileType() {
        return TileType.VALLEY;
    }
}
