package ca.carleton.magicrealm.entity;

/**
 * File names mapping to the images in the image package items.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:49 AM
 */
public enum EntityInformation {

    // Characters
    AMAZON(null),
    BLACK_KNIGHT(null),
    CAPTAIN(null),
    DWARF(null),
    ELF(null),
    SWORDSMAN(null),

    // Natives
    COMPANY(null),
    BASHKARS(null),
    ROGUES(null),
    GUARD(null),
    LANCERS(null),
    WOODFOLK(null),
    PATROL(null),
    SOLDIERS(null);

    private final String imageFilePath;

    EntityInformation(final String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getPath() {
        return this.imageFilePath;
    }

}
