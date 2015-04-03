import ca.carleton.magicrealm.control.EndGame;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.VictoryCondition;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests if our end-game calculations are correct.
 *
 * Created with IntelliJ IDEA.
 * Date: 03/04/2015
 * Time: 6:05 PM
 */
public class VictoryConditionsTest {

    @Test
    public void canTestVictoryPointsCalculations_one() {

        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        // Test 5 points in gold = 150 gold required.
        final VictoryCondition condition = new VictoryCondition();
        condition.addGold(5);

        // 150 gold, since the 10 is subtracted.
        player.getCharacter().addGold(150);
        player.setVictoryCondition(condition);

        assertThat(EndGame.getTotalScore(player), is(0));
    }

    @Test
    public void canTestVictoryPointsCalculations_two() {

        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        // Test 4 points in gold = 120 gold required. 1 point in treasure, 1 required.
        final VictoryCondition condition = new VictoryCondition();
        condition.addGold(4);
        condition.addGreatTreasures(1);

        // Met the gold value, but not treasure.
        player.getCharacter().addGold(120);
        player.setVictoryCondition(condition);

        // Score is -3 because they didn't make the required amount (-1 * 3 penalty = -3 * 1 point put in = -3).
        assertThat(EndGame.getTotalScore(player), is(-3));
    }

    @Test
    public void canTestVictoryPointsCalculations_three() {

        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        // Test 1 point in each.
        final VictoryCondition condition = new VictoryCondition();
        condition.addGold(1);
        condition.addGreatTreasures(1);
        condition.addSpells(1);
        condition.addNotoriety(1);
        condition.addFame(1);

        // Met no values with 0 of each, so do nothing.
         player.setVictoryCondition(condition);

        // Score is  <current number - required (* penalty if needed)> times factor divided by <factor> times <number put in>
        // Gold: 0 - 1 * 3 = -3 * 30 = -90 / 30 / -3 * 1 = -3
        // Same for the others.
        assertThat(EndGame.getTotalScore(player), is(-15));
    }

}
