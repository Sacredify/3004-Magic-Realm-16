package ca.carleton.magicrealm.entity;

import ca.carleton.magicrealm.entity.character.CharacterType;

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
    CHARACTER_SWORDSMAN("image/character/swordsman.png"),
    CHARACTER_BERSERKER("image/character/berserker.png"),
    CHARACTER_WHITE_KNIGHT("image/character/white_knight.png"),

    // Native types
    NATIVE_KNIGHT("image/natives/knight_o.png"),
    NATIVE_GREAT_SWORDSMAN("image/natives/great_swordsman_c.png"),
    NATIVE_GREAT_AXEMAN("image/natives/great_axeman_r.png"),
    NATIVE_PIKEMAN("image/natives/pikeman_c.png"),
    NATIVE_SHORT_SWORDSMAN("image/natives/short_swordsman_c.png"),
    NATIVE_CROSSBOW_MAN("image/natives/crossbowman_c.png"),
    NATIVE_LANCER("image/natives/lancer_l.png"),
    NATIVE_RAIDER("image/natives/raider_b.png"),
    NATIVE_ARCHER("image/natives/archer_r.png"),
    NATIVE_SWORDSMAN("image/natives/swordsman_r.png"),
    NATIVE_ASSASSIN("image/natives//assassin_m.png"),

    // Ghosts
    GHOST("image/ghost/ghost.gif"),

    //Monsters
    GIANT("image/monsters/giant.gif"),
    TROLL("image/monsters/troll.gif"),
    SPIDER("image/monsters/spider.gif"),
    SERPENT("image/monsters/serpent.gif"),
    DRAGON("image/monsters/dragon.gif"),
    GIANTBAT("image/monsters/bat.gif");

    private final String imageFilePath;

    EntityInformation(final String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getPath() {
        return this.imageFilePath;
    }

    public CharacterType convertToCharacterType() {
        switch (this) {
            case CHARACTER_AMAZON:
                return CharacterType.AMAZON;
            case CHARACTER_BLACK_KNIGHT:
                return CharacterType.BLACK_KNIGHT;
            case CHARACTER_CAPTAIN:
                return CharacterType.CAPTAIN;
            case CHARACTER_DWARF:
                return CharacterType.DWARF;
            case CHARACTER_ELF:
                return CharacterType.ELF;
            case CHARACTER_SWORDSMAN:
                return CharacterType.SWORDSMAN;
            case CHARACTER_BERSERKER:
                return CharacterType.BERSERKER;
            case CHARACTER_WHITE_KNIGHT:
                return CharacterType.WHITE_KNIGHT;
            default:
                return null;
        }
    }

}
