package ca.carleton.magicrealm.GUI.tile;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 12:38 PM
 */
public enum TileInformation {

    CLIFF("image/tiles/cliff1.png"),
    LEDGES("image/tiles/ledges1.png"),
    EVIL_VALLEY("image/tiles/evilvalley1.png"),
    HIGH_PASS("image/tiles/highpass1.png"),
    BORDER_LAND("image/tiles/borderland1.png"),
    CAVERN("image/tiles/cavern1.png"),
    MOUNTAIN("image/tiles/mountain1.png"),
    CAVES("image/tiles/caves1.png"),
    LINDEN_WOOD("image/tiles/lindenwoods1.png"),
    AWFUL_VALLEY("image/tiles/awfulvalley1.png"),
    RUINS("image/tiles/ruins1.png"),
    NUT_WOODS("image/tiles/nutwoods1.png"),
    CURST_VALLEY("image/tiles/curstvalley1.png"),
    DEEP_WOODS("image/tiles/deepwoods1.png"),
    CRAG("image/tiles/crag1.png"),
    DARK_VALLEY("image/tiles/darkvalley1.png"),
    OAK_WOODS("image/tiles/oakwoods1.png"),
    BAD_VALLEY("image/tiles/badvalley1.png"),
    MAPLE_WOODS("image/tiles/maplewoods1.png"),
    PINE_WOODS("image/tiles/pinewoods1.png");

    private final String imageFilePath;

    TileInformation(final String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getPath() {
        return this.imageFilePath;
    }

    public String getEnchantedPath() {
        return this.imageFilePath.replace("1.png", "") + "-e1.png";
    }

}
