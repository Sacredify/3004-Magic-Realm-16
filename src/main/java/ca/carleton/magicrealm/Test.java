package ca.carleton.magicrealm;

import ca.carleton.magicrealm.GUI.BoardWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 03/02/15
 * Time: 4:45 PM
 */
public class Test {

    private static final Logger LOG = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        LOG.info("Logged something.");

        BoardWindow board = new BoardWindow();
        board.initWindow();
    }
}
