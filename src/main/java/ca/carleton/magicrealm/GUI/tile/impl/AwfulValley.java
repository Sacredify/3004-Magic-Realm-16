package ca.carleton.magicrealm.GUI.tile.impl;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 1:06 PM
 */
public class AwfulValley extends AbstractTile {

    public AwfulValley() {
        this.clearings = Clearing.initializeClearingsArray(4,this);
        
    }
    

    @Override
    public TileInformation getTileInformation() {
        return TileInformation.AWFUL_VALLEY;
    }
}
