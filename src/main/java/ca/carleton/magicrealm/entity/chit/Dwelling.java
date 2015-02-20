package ca.carleton.magicrealm.entity.chit;

import java.io.Serializable;

/**
 * Created by Tony on 17/02/2015.
 */
public enum Dwelling implements Serializable {
    CHAPEL("image/chits/chapel.png"),
    GUARD("image/chits/guard.png"),
    HOUSE("image/chits/house.png"),
    HUT("image/chits/hut.png"),
    INN("image/chits/inn.png"),
    LARGE_FIRE("image/chits/large_fire.png"),
    SMALL_FIRE("image/chits/small_fire.png");

    private final String imageFilePath;

    Dwelling(final String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getPath() {
        return this.imageFilePath;
    }

}
