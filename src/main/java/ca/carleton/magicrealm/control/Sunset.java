package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.Path;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.entity.chit.ColoredChit;
import ca.carleton.magicrealm.entity.chit.Dwelling;
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
        for (Denizen denizen : boardModel.getDenizens()) {
            LOG.info("Checking {} to see if they will be moving...", denizen);
            if (denizen.isProwling()) {
                int roll = DiceRoller.rollOnce();
                LOG.info("{} is prowling. Rolled {}.", denizen, roll);

                if (roll > 4) {
                    LOG.info("Rolled > 4 and will move.");
                    List<Clearing> possibleClearings = denizen.getCurrentClearing().getAdjacentPaths().stream()
                            .filter(path -> path.getToClearing().getParentTile() == denizen.getCurrentClearing().getParentTile()).map(Path::getToClearing).collect(Collectors.toList());

                    int randomPathIndex = RANDOM.nextInt(possibleClearings.size());
                    denizen.getCurrentClearing().removeEntity(denizen);
                    denizen.setCurrentClearing(possibleClearings.get(randomPathIndex));
                    denizen.getCurrentClearing().addEntity(denizen);
                }

                boolean isMovingToPlayer = false;
                Clearing moveTarget = null;
                LOG.info("Checking {} for player presence...", denizen.getCurrentClearing().getParentTile());
                for (Clearing clearing : denizen.getCurrentClearing().getParentTile().getClearings()) {
                    for (final Entity entity : clearing.getEntities()) {
                        if (entity instanceof AbstractCharacter) {
                            isMovingToPlayer = true;
                            moveTarget = clearing;
                            LOG.info("{} was on the same tile! Moving {} to their clearing. [{}]", entity, denizen, clearing);
                            break;
                        }
                    }
                }

                if (isMovingToPlayer) {
                    // Remove self
                    denizen.getCurrentClearing().removeEntity(denizen);
                    denizen.setCurrentClearing(moveTarget);
                    moveTarget.getEntities().add(denizen);
                } else {
                    LOG.info("No player was on the tile.");
                }

            } else {
                LOG.info("They are not prowling and will not move this turn.");
            }
        }

        if (boardModel.getClearingOfDwelling(Dwelling.SMALL_FIRE) == null) {
            /** Check if player has discovered any substitute chits **/
            Clearing smallCampireClearing = boardModel.getClearingOfChit("SMOKE", TileType.WOODS);

            // Check the small campire's tile
            for (Clearing clearing : smallCampireClearing.getParentTile().getClearings()) {
                if (!clearing.getEntities().stream().filter(entity -> entity instanceof AbstractCharacter).collect(Collectors.toList()).isEmpty()) {
                    ColoredChit smokeChit = clearing.getParentTile().getChits().stream()
                            .filter(chit -> chit.getDescription().equals("SMOKE")).collect(Collectors.toList()).get(0);
                    smallCampireClearing.getParentTile().getChits().remove(smokeChit);
                    smallCampireClearing.setDwelling(Dwelling.SMALL_FIRE);
                }
            }
        }

        if (boardModel.getClearingOfDwelling(Dwelling.LARGE_FIRE) == null) {
            Clearing largeCampireClearing = boardModel.getClearingOfChit("STINK", TileType.WOODS);

            for (Clearing clearing : largeCampireClearing.getParentTile().getClearings()) {
                if (!clearing.getEntities().stream().filter(entity -> entity instanceof AbstractCharacter).collect(Collectors.toList()).isEmpty()) {
                    ColoredChit stinkChit = clearing.getParentTile().getChits().stream()
                            .filter(chit -> chit.getDescription().equals("STINK")).collect(Collectors.toList()).get(0);
                    largeCampireClearing.getParentTile().getChits().remove(stinkChit);
                    largeCampireClearing.setDwelling(Dwelling.SMALL_FIRE);
                }

            }
        }
    }
}
