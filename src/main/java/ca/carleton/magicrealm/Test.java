package ca.carleton.magicrealm;

import ca.carleton.magicrealm.GUI.board.BoardWindow;
import ca.carleton.magicrealm.control.GameController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 03/02/15
 * Time: 4:45 PM
 */
public class Test {

    private static final Logger LOG = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameController game = new GameController();
            }
        });
    }
}
