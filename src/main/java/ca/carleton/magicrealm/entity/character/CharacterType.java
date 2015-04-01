package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.chit.Dwelling;

public enum CharacterType {
    AMAZON("Amazon", new Dwelling[] {Dwelling.INN}),
    BLACK_KNIGHT("Black Knight", new Dwelling[] {Dwelling.INN}),
    CAPTAIN("Captain", new Dwelling[] {Dwelling.INN, Dwelling.GUARD, Dwelling.HOUSE}),
    DWARF("Dwarf", new Dwelling[] {Dwelling.INN, Dwelling.GUARD}),
    ELF("Elf", new Dwelling[] {Dwelling.INN}),
    SWORDSMAN("Swordsman", new Dwelling[] {Dwelling.INN}),
    BERSERKER("Berserker", new Dwelling[] {Dwelling.INN}),
    WHITE_KNIGHT("White Knight", new Dwelling[] {Dwelling.INN, Dwelling.CHAPEL});

    private final String displayName;

    private final Dwelling[] startingLocations;

    CharacterType(final String displayName, final Dwelling[] startingLocations) {
        this.displayName = displayName;
        this.startingLocations = startingLocations;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Dwelling[] getStartingLocations() {
        return this.startingLocations;
    }
}