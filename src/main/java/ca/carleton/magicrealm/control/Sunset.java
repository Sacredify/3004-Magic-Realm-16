package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.entity.monster.Monster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Tony on 30/03/2015.
 */
public class Sunset {

    private static final Logger LOG = LoggerFactory.getLogger(Sunset.class);

    public static void doSunset(final BoardModel boardModel) {
        /** Set all prowling monsters to whatever clearing a nearby player is on **/
        for (Monster monster : boardModel.getMonsters()) {
            if (monster.isProwling()) {
                for (Clearing clearing : monster.getCurrentClearing().getParentTile().getClearings()) {
                    for (Entity entity: clearing.getEntities()) {
                        if (entity instanceof AbstractCharacter) {
                            monster.setCurrentClearing(clearing);
                        }
                    }
                }
            }
        }


    }
}
