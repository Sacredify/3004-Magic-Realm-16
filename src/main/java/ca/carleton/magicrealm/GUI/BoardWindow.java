package ca.carleton.magicrealm.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Tony on 12/02/2015.
 *
 * Window to display the game board
 */
public class BoardWindow {

    final static public String WINDOW_NAME = "Board";
    final static public int WINDOW_HEIGHT = 1000;
    final static public int WINDOW_WIDTH = 1000;

    JFrame frame;

    public BoardWindow() {

    }

    public void initWindow() {
        BoardServices boardServices = new BoardServices();

        frame = new JFrame(WINDOW_NAME);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel testLabel = new JLabel();
        testLabel.setLocation(50,50);
        testLabel.setSize(500,400);
        //ImageIcon icon = new ImageIcon("../../../../assets/tiles/awfulvalley1.gif");

        ImageIcon icon = new ImageIcon("main/assets/tiles/awfulvalley1.png");
        testLabel.setIcon(boardServices.createImageIcon("tiles/awfulvalley1.png"));
        frame.add(testLabel);

        HexagonButton testHex = new HexagonButton(400,250);
        for (Component component : testHex.getComponents()) {
            frame.add(component);
        }

        frame.setVisible(true);
    }


}
