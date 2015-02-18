package ca.carleton.magicrealm.entity;

/**
 * File names mapping to the image in the image package items.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:49 AM
 */
public enum EntityInformation {

    // Characters
    CHARACTER_AMAZON("image/character/amazon.png"),
    CHARACTER_BLACK_KNIGHT("image/character/black_knight.png"),
    CHARACTER_CAPTAIN("image/character/captain.png"),
    CHARACTER_DWARF("image/character/dwarf.png"),
    CHARACTER_ELF("image/character/elf.png"),
    CHARACTER_SWORDSMAN("image/character/elf.png"),

    // Native types
    NATIVE_KNIGHT(null),
    NATIVE_GREAT_SWORDSMAN(null),
    NATIVE_GREAT_AXEMAN(null),
    NATIVE_PIKEMAN(null),
    NATIVE_SHORT_SWORDSMAN(null),
    NATIVE_CROSSBOW_MAN(null),
    NATIVE_LANCER(null),
    NATIVE_RAIDER(null),
    NATIVE_ARCHER(null),
    NATIVE_SWORDSMAN(null),
    NATIVE_ASSASSIN(null);

    private final String imageFilePath;

    EntityInformation(final String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getPath() {
        return this.imageFilePath;
    }

}
