package ca.carleton.magicrealm.entity.character;

public enum CharacterType {
    AMAZON("Amazon"),
    BLACK_KNIGHT("Black Knight"),
    CAPTAIN("Captain"),
    DWARF("Dwarf"),
    ELF("Elf"),
    SWORDSMAN("Swordsman");

    private final String displayName;

    CharacterType(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}