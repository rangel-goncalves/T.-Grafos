package grafosatv3;

import java.util.ArrayList;

/**
 *
 * @author Rangel
 */
public class Polygon {
    
    ArrayList<Point> poly;

    public Polygon() {
        poly = new ArrayList();
    }
    
    public Point[] getPoints(){
        Point [] p = new Point[poly.size()];
        poly.toArray(p);
        return p;
    }
         
}
