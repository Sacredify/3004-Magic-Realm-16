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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
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
                LOG.info("{} is prowling. Rolled {}.", abstractMonster);

                if (roll > 4) {
                    LOG.info("Rolled >= 4 and will move.");
                    ArrayList<Clearing> possibleClearings = new ArrayList<>();
                    for (Path path : abstractMonster.getCurrentClearing().getAdjacentPaths()) {
                        if (path.getToClearing().getParentTile() == abstractMonster.getCurrentClearing().getParentTile())
                            possibleClearings.add(path.getToClearing());
                    }

                    int randomPathIndex = RANDOM.nextInt(possibleClearings.size());
                    abstractMonster.getCurrentClearing().removeEntity(abstractMonster);
                    abstractMonster.setCurrentClearing(possibleClearings.get(randomPathIndex));
                    abstractMonster.getCurrentClearing().addEntity(abstractMonster);
                }

                for (Clearing clearing : abstractMonster.getCurrentClearing().getParentTile().getClearings()) {

                    final Iterator<Entity> iterator = clearing.getEntities().iterator();
                    while (iterator.hasNext()) {
                        final Entity element = iterator.next();
                        if (element instanceof AbstractCharacter) {
                            abstractMonster.getCurrentClearing().removeEntity(abstractMonster);
                            abstractMonster.setCurrentClearing(clearing);
                            abstractMonster.getCurrentClearing().addEntity(abstractMonster);
                            LOG.info("{} was on the same tile! Moved {} to their clearing. [{}]", element, abstractMonster, clearing);
                        }
                    }
                }
            }
        }
    }
}
