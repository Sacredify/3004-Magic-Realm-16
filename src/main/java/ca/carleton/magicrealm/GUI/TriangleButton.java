package ca.carleton.magicrealm.GUI;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by Tony on 10/02/2015.
 */
public class TriangleButton extends JButton{

    private Shape triangle = createTriangle();

    public void paintBorder( Graphics g ) {
        ((Graphics2D)g).draw(triangle);
    }
    public void paintComponent( Graphics g ) {
        ((Graphics2D)g).fill(triangle);
    }
    public Dimension getPreferredSize() {
        return new Dimension(200,100);
    }
    public boolean contains(int x, int y) {
        return triangle.contains(x, y);
    }

    private Shape createTriangle() {
        Polygon p = new Polygon();
        p.addPoint( 0   , 100 );
        p.addPoint( 100 , 0   );
        p.addPoint( 200 ,100  );
        return p;
    }
}
