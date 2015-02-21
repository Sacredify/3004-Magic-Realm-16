package ca.carleton.magicrealm.entity.chit;

import ca.carleton.magicrealm.GUI.tile.TileType;

/**
 * A yellow chit represents a warning chit on the map.
 *
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 12:28 PM
 */
public class YellowChit extends ColoredChit {

    private final TileType tileType;

    public YellowChit(final String warning, final TileType tileType) {
        this.clearingNumber = -1; //garbage, since they can appear anywhere in the tile.
        this.description = warning;
        this.tileType = tileType;
    }

    @Override
    public ChitColor getChitColor() {
        return ChitColor.YELLOW;
    }

    public TileType getTileType() {
        return this.tileType;
    }
}
