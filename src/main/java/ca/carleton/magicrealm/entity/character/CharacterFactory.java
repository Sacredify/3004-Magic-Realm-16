package ca.carleton.magicrealm.entity.character;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 10:20 AM
 */
public class CharacterFactory {

    public static AbstractCharacter createCharacter(final CharacterType characterType) {

        if (characterType == CharacterType.AMAZON) {
            return Amazon.createAmazon();
        }

        throw new IllegalArgumentException("Let's try to not fail when using this...");
    }
}
