package ca.carleton.magicrealm.entity.chit;

/**
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 12:01 PM
 */
public enum ChitColor {
    RED("image/chits/redchit.jpg"),
    GOLD("image/chits/goldchit.jpg"),
    YELLOW("image/chits/yellowchit.jpg"),
    LOST_CITY("image/chits/lostcity.jpg"),
    LOST_CASTLE("image/chits/lostcastle.jpg");

    private final String imageFilePath;

    ChitColor(final String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }
}
