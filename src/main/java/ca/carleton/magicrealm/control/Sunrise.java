package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Groupings of methods to handle sunrise operations.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 23/02/15
 * Time: 5:48 AM
 */
public class Sunrise {

    private static final Logger LOG = LoggerFactory.getLogger(Sunrise.class);

    /**
     * Execute sunrise for the day.
     * <p/>
     * 1. Mark which natives are now prowling.
     * 2. Reset position of all prowling and un-hired natives and monsters (even if killed)) are returned to where they started if 7th day of week.
     *
     * @param boardModel the model to update.
     * @param currentDay the current day.
     */
    public static void doSunrise(final BoardGUIModel boardModel, final int currentDay) {

    }

}
