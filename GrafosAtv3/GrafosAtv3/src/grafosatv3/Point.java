package grafosatv3;

/**
 *
 * @author Rangel
 */
public class Point {
    
    public Double x, y;

    public Point() {
    }
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }  

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }
    
}
