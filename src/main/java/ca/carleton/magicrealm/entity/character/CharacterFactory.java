package ca.carleton.magicrealm.entity.character;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 10:20 AM
 */
public class CharacterFactory {

    public static AbstractCharacter createCharacter(final CharacterType characterType) {

        switch (characterType) {
            case AMAZON:
                return new Amazon();
            case BLACK_KNIGHT:
                return new BlackKnight();
            case CAPTAIN:
                return new Captain();
            case DWARF:
                return new Dwarf();
            case ELF:
                return new Elf();
            case SWORDSMAN:
                return new Swordsman();
            default:
                throw new IllegalArgumentException("Couldn't create character of type " + characterType + ".");
        }
    }
}
