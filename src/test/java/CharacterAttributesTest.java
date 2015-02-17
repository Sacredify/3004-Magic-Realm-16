import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.entity.character.Swordsman;
import ca.carleton.magicrealm.entity.natives.Knight;
import ca.carleton.magicrealm.entity.natives.NativeType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 4:13 PM
 */
public class CharacterAttributesTest {

    @Test
    public void canCreateCharacterThroughFactory() throws Exception {

        final AbstractCharacter swordsman = CharacterFactory.createCharacter(CharacterType.SWORDSMAN);
        assertThat(swordsman, is(instanceOf(Swordsman.class)));
    }

    @Test
    public void canGetCharacterRelationship() throws Exception {

        final AbstractCharacter amazon = CharacterFactory.createCharacter(CharacterType.AMAZON);
        final Knight nativeKnight = new Knight(NativeType.BASHKARS);

        assertThat(amazon.getRelationshipWith(nativeKnight.getFaction()), is(Relationship.UNFRIENDLY));
    }

}
