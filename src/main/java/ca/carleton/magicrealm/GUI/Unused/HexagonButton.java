package ca.carleton.magicrealm.GUI.Unused;

import ca.carleton.magicrealm.GUI.board.BoardServices;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 11/02/2015.
 *
 * A button in the shape of a hexagon, composed of a square and two triangle buttons.
 *
 *
 */
public class HexagonButton extends JPanel{

    public static int WIDTH = 500;
    public static int HEIGHT = 430;
    public static int SQUARE_HEIGHT = 430;
    public static int SQUARE_WIDTH = 300;
    public static int TRIANGLE_WIDTH = 100;

    BoardServices boardServices = new BoardServices();

    private JLabel pictureLabel;

    private TriangleButton rightTriangleButton;

    private TriangleButton leftTriangleButton;

    private JLabel middleSquareButton;

    private int xPrimePos;

    private int yPrimePos;

    public HexagonButton(int x, int y) {
        setLayout(null);

        this.setLocation(x, y);
        this.setSize(WIDTH, HEIGHT);

        xPrimePos = x;
        yPrimePos = y;

        /** Set the middle square **/

        middleSquareButton = new JLabel();
        middleSquareButton.setSize(WIDTH, SQUARE_HEIGHT);
        middleSquareButton.setLocation(xPrimePos, yPrimePos);

        /** Set the left triangle **/

        List<Integer> pointA = new ArrayList<>();
        pointA.add(xPrimePos - TRIANGLE_WIDTH);
        pointA.add(yPrimePos + SQUARE_HEIGHT/2);

        List<Integer> pointB = new ArrayList<>();
        int xPoint = xPrimePos;
        int yPoint = yPrimePos + SQUARE_HEIGHT;
        pointB.add(xPoint);
        pointB.add(yPoint);

        List<Integer> pointC = new ArrayList<>();
        xPoint = xPrimePos;
        yPoint = yPrimePos;
        pointC.add(xPoint);
        pointC.add(yPoint);

        leftTriangleButton = new TriangleButton(pointA, pointB, pointC);

        /** Set the right triangle **/

        pointA = new ArrayList<>();
        pointA.add(xPrimePos + SQUARE_WIDTH);
        pointA.add(xPrimePos - TRIANGLE_WIDTH);

        pointB = new ArrayList<>();
        pointB.add(xPrimePos + SQUARE_WIDTH);
        pointB.add(yPrimePos + SQUARE_HEIGHT);

        pointC = new ArrayList<>();
        pointC.add(xPrimePos + SQUARE_WIDTH + TRIANGLE_WIDTH);
        pointC.add(yPrimePos + SQUARE_HEIGHT/2);

        rightTriangleButton = new TriangleButton(pointA, pointB, pointC);

        // TODO: For now
        ImageIcon icon = this.boardServices.createImageIcon("image/tiles/awfulvalley1.gif");
        pictureLabel = new JLabel();
        pictureLabel.setSize(WIDTH, HEIGHT);
        // TODO: change to actual loc
        pictureLabel.setLocation(xPrimePos, yPrimePos);
        pictureLabel.setIcon(icon);

        /** add the componenets of the hex **/
        this.add(leftTriangleButton);
        this.add(middleSquareButton);
        this.add(rightTriangleButton);
        this.add(pictureLabel);
    }

    public TriangleButton getRightTriangleButton() {
        return rightTriangleButton;
    }

    public TriangleButton getLeftTriangleButton() {
        return leftTriangleButton;
    }

    public JLabel getMiddleSquareButton() {
        return middleSquareButton;
    }

    public JLabel getPictureLabel() {
        return pictureLabel;
    }

    public void setPictureLabel(JLabel pictureLabel) {
        this.pictureLabel = pictureLabel;
    }
}
