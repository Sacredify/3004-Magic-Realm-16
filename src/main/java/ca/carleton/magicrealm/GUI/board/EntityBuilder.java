package ca.carleton.magicrealm.GUI.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 23/02/15
 * Time: 4:03 AM
 */
public class EntityBuilder  {

    private static final Logger LOG = LoggerFactory.getLogger(EntityBuilder.class);

    /**
     * Test runner.
     */
    public static void main(String[] args) {
        ChitBuilder.placeChits(new BoardGUIModel());
    }

    /**
     * Places the entities on the given board.
     *
     * @param board the board.
     */
    public static void placeEntities(final BoardGUIModel board) {

    }

}
