package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.entity.monster.Denizen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Tony on 30/03/2015.
 */
public class Sunset {

    private static final Logger LOG = LoggerFactory.getLogger(Sunset.class);

    public static void doSunset(final BoardModel boardModel) {
        /** Set all prowling monsters to whatever clearing a nearby player is on **/
        for (Denizen monster : boardModel.getDenizens()) {
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
