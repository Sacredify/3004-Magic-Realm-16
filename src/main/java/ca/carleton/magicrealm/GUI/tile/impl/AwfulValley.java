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
public class AwfulValley extends AbstractTile {

    public AwfulValley() {
        this.clearings = Clearing.initializeClearingsArray(4,this);

        this.clearings[0].setX(138);
        this.clearings[0].setY(156);

        this.clearings[1].setX(135);
        this.clearings[1].setY(281);

        this.clearings[2].setX(306);
        this.clearings[2].setY(253);

        this.clearings[3].setX(246);
        this.clearings[3].setY(85);
        this.clearings[3].setDwelling(Dwelling.CHAPEL);

    }
    

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.AWFUL_VALLEY;
    }

    @Override
    public TileType getTileType() {
        return TileType.VALLEY;
    }
}
