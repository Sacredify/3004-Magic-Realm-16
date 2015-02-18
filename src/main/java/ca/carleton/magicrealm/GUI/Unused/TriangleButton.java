package ca.carleton.magicrealm.GUI.Unused;

import java.awt.*;
import javax.swing.JButton;

/**
 * Created by Tony on 10/02/2015.
 */
public class TriangleButton extends JButton{

    public static int HEIGHT = 200;
    public static int WIDTH = 100;

    private Shape triangle;

    private java.util.List<Integer> pointA;
    private java.util.List<Integer> pointB;
    private java.util.List<Integer> pointC;

    public TriangleButton(java.util.List<Integer> a, java.util.List<Integer> b, java.util.List<Integer> c) {
        pointA = a;
        pointB = b;
        pointC = c;

        triangle = createTriangle();
    }

    private Shape createTriangle() {
        Polygon p = new Polygon();
        p.addPoint(pointA.get(0), pointA.get(1));
        p.addPoint(pointB.get(0), pointB.get(1));
        p.addPoint(pointC.get(0), pointC.get(1));
        return p;
    }

    public void paintBorder( Graphics g ) {
        ((Graphics2D)g).draw(triangle);
    }
    public void paintComponent( Graphics g ) {
        ((Graphics2D)g).fill(triangle);
    }
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH,HEIGHT);
    }
    public boolean contains(int x, int y) {
        return triangle.contains(x, y);
    }
}
