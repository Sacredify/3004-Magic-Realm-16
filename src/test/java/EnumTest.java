import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.game.combat.Harm;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created with IntelliJ IDEA.
 * Date: 01/04/2015
 * Time: 1:25 AM
 */
public class EnumTest {

    @Test
    public void canIncreaseAndDecreaseHarm() {
        final Harm negligible = Harm.NEGLIGIBLE;
        final Harm tremendous = Harm.TREMENDOUS;
        final Harm medium = Harm.MEDIUM;

        assertThat(negligible.decrease(), is(Harm.NEGLIGIBLE));
        assertThat(tremendous.increase(), is(Harm.TREMENDOUS));
        assertThat(medium.increase(), is(Harm.HEAVY));
    }

    @Test
    public void canGetRelationshipAfterBuyingDrinks() {
        final Relationship enemy = Relationship.ENEMY;
        final Relationship ally = Relationship.ALLY;
        final Relationship neutral = Relationship.NEUTRAL;

        assertThat(enemy.getRelationshipAfterDrinksBought(), is(Relationship.UNFRIENDLY));
        assertThat(ally.getRelationshipAfterDrinksBought(), is(Relationship.ALLY));
        assertThat(neutral.getRelationshipAfterDrinksBought(), is(Relationship.FRIENDLY));
    }

}
