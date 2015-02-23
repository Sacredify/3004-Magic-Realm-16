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
public class AwfulValley extends AbstractTile {

    public AwfulValley() {
        this.clearings = Clearing.initializeClearingsArray(4,this);

        this.clearings[0].setX(129);
        this.clearings[0].setY(153);
        this.clearings[0].setName("1");

        this.clearings[1].setX(129);
        this.clearings[1].setY(277);
        this.clearings[1].setName("2");

        this.clearings[2].setX(306);
        this.clearings[2].setY(253);
        this.clearings[2].setName("4");

        this.clearings[3].setX(246);
        this.clearings[3].setY(88);
        this.clearings[3].setName("5");

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
