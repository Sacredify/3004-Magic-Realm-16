package ca.carleton.magicrealm.entity.chit;

import java.io.Serializable;

/**
 * Created by Tony on 17/02/2015.
 */
public enum Dwelling implements Serializable {
    HOUSE("image/chits/house.png");

    private final String imageFilePath;

    Dwelling(final String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getPath() {
        return this.imageFilePath;
    }

}
