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
public class BadValley extends AbstractTile {

    public BadValley() {
        this.clearings = Clearing.initializeClearingsArray(4,this);

        this.clearings[0].setX(282);
        this.clearings[0].setY(363);

        this.clearings[1].setX(135);
        this.clearings[1].setY(283);

        this.clearings[2].setX(181);
        this.clearings[2].setY(104);

        this.clearings[3].setX(360);
        this.clearings[3].setY(152);
        this.clearings[3].setDwelling(Dwelling.INN);
    }

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.BAD_VALLEY;
    }

    @Override
    public TileType getTileType() {
        return TileType.VALLEY;
    }
}
