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

        this.clearings[0].setX(364);
        this.clearings[0].setY(152);
        this.clearings[0].setName("1");

        this.clearings[1].setX(253);
        this.clearings[1].setY(86);
        this.clearings[1].setName("2");

        // 4
        this.clearings[2].setX(185);
        this.clearings[2].setY(252);
        this.clearings[2].setName("4");

        // 4
        this.clearings[3].setX(361);
        this.clearings[3].setY(281);
        this.clearings[3].setName("5");
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
