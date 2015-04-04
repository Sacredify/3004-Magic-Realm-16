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
import java.util.Random;

/**
 * Created by Tony on 30/03/2015.
 */
public class Sunset {

    private static final Logger LOG = LoggerFactory.getLogger(Sunset.class);

    public static void doSunset(final BoardModel boardModel) {
        /** Set all prowling monsters to whatever clearing a nearby player is on **/
        for (AbstractMonster abstractMonster : boardModel.getAbstractMonsters()) {
            if (abstractMonster.isProwling()) {
                int roll = DiceRoller.rollOnce();

                if (roll > 4) {
                    Random random = new Random();
                    ArrayList<Clearing> possibleClearings = new ArrayList<>();

                    for (Path path : abstractMonster.getCurrentClearing().getAdjacentPaths()) {
                        if (path.getToClearing().getParentTile() == abstractMonster.getCurrentClearing().getParentTile())
                           possibleClearings.add(path.getToClearing());
                    }

                    int randomPathIndex = random.nextInt(possibleClearings.size());
                    abstractMonster.setCurrentClearing(possibleClearings.get(randomPathIndex));
                }

                for (Clearing clearing : abstractMonster.getCurrentClearing().getParentTile().getClearings()) {
                    for (Entity entity: clearing.getEntities()) {
                        if (entity instanceof AbstractCharacter) {
                            LOG.debug("Monster moved to player clearing");
                            abstractMonster.setCurrentClearing(clearing);
                        }
                    }
                }
            }
        }
    }
}
