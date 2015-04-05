package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.Path;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.entity.monster.AbstractMonster;
import ca.carleton.magicrealm.game.DiceRoller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Sunset class. Handles moving monsters around (and prowling).
 * <p>
 * Created by Tony on 30/03/2015.
 */
public class Sunset {

    private static final Logger LOG = LoggerFactory.getLogger(Sunset.class);

    private static final Random RANDOM = new Random();

    public static void doSunset(final BoardModel boardModel) {
        /** Set all prowling monsters to whatever clearing a nearby player is on **/
        for (AbstractMonster abstractMonster : boardModel.getAbstractMonsters()) {
            LOG.info("Checking {} to see if they will be moving...", abstractMonster);
            if (abstractMonster.isProwling()) {
                int roll = DiceRoller.rollOnce();
                LOG.info("{} is prowling. Rolled {}.", abstractMonster, roll);

                if (roll > 4) {
                    LOG.info("Rolled > 4 and will move.");
                    List<Clearing> possibleClearings = abstractMonster.getCurrentClearing().getAdjacentPaths().stream()
                            .filter(path -> path.getToClearing().getParentTile() == abstractMonster.getCurrentClearing().getParentTile()).map(Path::getToClearing).collect(Collectors.toList());

                    int randomPathIndex = RANDOM.nextInt(possibleClearings.size());
                    abstractMonster.getCurrentClearing().removeEntity(abstractMonster);
                    abstractMonster.setCurrentClearing(possibleClearings.get(randomPathIndex));
                    abstractMonster.getCurrentClearing().addEntity(abstractMonster);
                }

                boolean isMovingToPlayer = false;
                Clearing moveTarget = null;
                LOG.info("Checking {} for player presence...", abstractMonster.getCurrentClearing().getParentTile());
                for (Clearing clearing : abstractMonster.getCurrentClearing().getParentTile().getClearings()) {
                    for (final Entity entity : clearing.getEntities()) {
                        if (entity instanceof AbstractCharacter) {
                            isMovingToPlayer = true;
                            moveTarget = clearing;
                            LOG.info("{} was on the same tile! Moving {} to their clearing. [{}]", entity, abstractMonster, clearing);
                            break;
                        }
                    }
                }

                if (isMovingToPlayer) {
                    // Remove self
                    abstractMonster.getCurrentClearing().removeEntity(abstractMonster);
                    abstractMonster.setCurrentClearing(moveTarget);
                    moveTarget.getEntities().add(abstractMonster);
                } else {
                    LOG.info("No player was on the tile.");
                }

            } else {
                LOG.info("They are not prowling and will not move this turn.");
            }
        }
    }
}
