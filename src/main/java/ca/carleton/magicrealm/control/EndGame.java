package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.game.GameResult;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.VictoryCondition;
import ca.carleton.magicrealm.network.ServerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 03/04/2015
 * Time: 5:00 PM
 */
public class EndGame {

    private static final Logger LOG = LoggerFactory.getLogger(EndGame.class);

    /**
     * Calculate the final scores for the players.
     *
     * @param clients the clients.
     * @return the client mapped to their result.
     */
    public static Map<ServerThread, GameResult> calculateFinalScores(final List<ServerThread> clients) {

        final Map<ServerThread, Integer> scores = new HashMap<>();
        clients.stream().forEach(client -> scores.putIfAbsent(client, getTotalScore(client.getPlayer())));

        // Replace with Game Results
        final Map<ServerThread, GameResult> results = new HashMap<>();

        for (final Map.Entry<ServerThread, Integer> score : scores.entrySet()) {
            if (score.getValue() >= 0) {
                results.putIfAbsent(score.getKey(), GameResult.WINNER);
            } else {
                results.putIfAbsent(score.getKey(), GameResult.LOSER);
            }
            LOG.info("{} scored {} and was marked as a {}.", score.getKey().getPlayer().getCharacter(), score.getValue(), results.get(score.getKey()));
        }
        // Replace highest with Victor.
        final ServerThread winner = scores.entrySet().stream().max(Map.Entry.comparingByValue(Integer::compareTo)).get().getKey();
        results.put(winner, GameResult.VICTOR);
        LOG.info("{} had the highest score and is the victor!", winner.getPlayer().getCharacter());

        return results;
    }

    // Method is only public for testing purposes.
    public static Integer getTotalScore(final Player player) {

        final VictoryCondition condition = player.getVictoryCondition();
        final AbstractCharacter character = player.getCharacter();

        LOG.info("Starting end-game calculations for {}.", character);
        LOG.info("Their distribution: --> gold: {} | notoriety: {} | fame: {} | treasure: {} | spells: {}.",
                condition.getGold(), condition.getNotoriety(), condition.getFame(), condition.getGreatTreasuresCount(), condition.getSpellsCount());

        // values subtracted by the victory condition
        int gold = character.getCurrentGold() - 10 - (condition.getGold() * 30);
        int notoriety = character.getCurrentNotoriety() - (condition.getNotoriety() * 20);
        int fame = character.getCurrentFame() - (condition.getFame() * 10);
        int treasure = character.getCurrentGreatTreasuresCount() - condition.getGreatTreasuresCount();
        int spells = character.getCurrentSpellsCount() - (condition.getSpellsCount() * 2);

        // If negative, multiply by 3 as a penalty
        if (gold < 0) {
            gold *= 3;
        }
        if (notoriety < 0) {
            notoriety *= 3;
        }
        if (fame < 0) {
            fame *= 3;
        }
        if (treasure < 0) {
            treasure *= 3;
        }
        if (spells < 0) {
            spells *= 3;
        }

        LOG.info("Base values --> gold: {} | notoriety: {} | fame: {} | treasure: {} | spells: {}.", gold, notoriety, fame, treasure, spells);

        // Conversion to victory points makes his final score (divide by the factor, then multiple that score by the number of victory points assigned).
        final int finalGold = ((int) Math.floor(Math.round((double) gold / 30))) * condition.getGold();
        final int finalNotoriety = ((int) Math.floor(Math.round((double) notoriety / 20))) * condition.getNotoriety();
        final int finalFame = ((int) Math.floor(Math.round((double) fame / 10))) * condition.getFame();
        final int finalTreasure = treasure * condition.getGreatTreasuresCount();
        final int finalSpells = ((int) Math.floor(Math.round((double) spells / 2))) * condition.getSpellsCount();

        LOG.info("Final values --> gold: {} | notoriety: {} | fame: {} | treasure: {} | spells: {}.", finalGold, finalNotoriety, finalFame, finalTreasure, finalSpells);

        return finalGold + finalNotoriety + finalFame + finalTreasure + finalSpells;
    }

}
