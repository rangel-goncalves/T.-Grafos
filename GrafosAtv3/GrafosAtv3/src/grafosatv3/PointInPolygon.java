package grafosatv3;

/**
 *
 * @author Rangel
 */
public class PointInPolygon {
    /**
     * Performs the even-odd-rule Algorithm to find out whether a point is in a given polygon.
     * This runs in O(n) where n is the number of edges of the polygon.
     *
     * @param polygon an array representation of the polygon where polygon[i][0] is the x Value of the i-th point and polygon[i][1] is the y Value.
     * @param point   an array representation of the point where point[0] is its x Value and point[1] is its y Value
     * @return whether the point is in the polygon (not on the edge, just turn < into <= and > into >= for that)
     */
    public static boolean pointInPolygon(double[][] polygon, double[] point) {
        //A point is in a polygon if a line from the point to infinity crosses the polygon an odd number of times
        boolean odd = false;
        // int totalCrosses = 0; // this is just used for debugging
        //For each edge (In this case for each point of the polygon and the previous one)
        for (int i = 0, j = polygon.length - 1; i < polygon.length; i++) { // Starting with the edge from the last to the first node
            //If a line from the point into infinity crosses this edge
            if (((polygon[i][1] >= point[1]) != (polygon[j][1] >= point[1])) // One point needs to be above, one below our y coordinate
                    // ...and the edge doesn't cross our Y corrdinate before our x coordinate (but between our x coordinate and infinity)
                    && (point[0] <= (polygon[j][0] - polygon[i][0]) * (point[1] - polygon[i][1]) / (polygon[j][1] - polygon[i][1]) + polygon[i][0])) {
                // Invert odd
                 System.out.println("Point crosses edge " + (j + 1));
                // totalCrosses++;
                odd = !odd;
                //return odd;
            }
            else {System.out.println("Point does not cross edge " + (j + 1));}
            j = i;
        }
        // System.out.println("Total number of crossings: " + totalCrosses);
        //If the number of crossings was odd, the point is in the polygon
        return odd;
    }
    
    

public boolean onLine(Line l1, Point p){
	// Check whether p is on the line or not
	if (p.x <= Math.max(l1.p1.x, l1.p2.x)
		&& p.x <= Math.min(l1.p1.x, l1.p2.x)
		&& (p.y <= Math.max(l1.p1.y, l1.p2.y)
			&& p.y <= Math.min(l1.p1.y, l1.p2.y)))
		return true;

	return false;
}

public int direction(Point a, Point b, Point c){
	double val = (b.y - a.y) * (c.x - b.x)
			- (b.x - a.x) * (c.y - b.y);

	if (val == 0)

		// Colinear
		return 0;

	else if (val < 0)

		// Anti-clockwise direction
		return 2;

	// Clockwise direction
	return 1;
}

public boolean isIntersect(Line l1, Line l2){
	// Four direction for two lines and points of other line
	int dir1 = direction(l1.p1, l1.p2, l2.p1);
	int dir2 = direction(l1.p1, l1.p2, l2.p2);
	int dir3 = direction(l2.p1, l2.p2, l1.p1);
	int dir4 = direction(l2.p1, l2.p2, l1.p2);

	// When intersecting
	if (dir1 != dir2 && dir3 != dir4)
		return true;

	// When p2 of line2 are on the line1
	if (dir1 == 0 && onLine(l1, l2.p1))
		return true;

	// When p1 of line2 are on the line1
	if (dir2 == 0 && onLine(l1, l2.p2))
		return true;

	// When p2 of line1 are on the line2
	if (dir3 == 0 && onLine(l2, l1.p1))
		return true;

	// When p1 of line1 are on the line2
	if (dir4 == 0 && onLine(l2, l1.p2))
		return true;

	return false;
}

public boolean checkInside(Point poly[], int n, Point p){

	// When polygon has less than 3 edge, it is not polygon
	if (n < 3)
		return false;

	// Create a point at infinity, y is same as point p
	Line exline = new Line( p, new Point(9999.00, p.y) );
	int count = 0;
	int i = 0;
	do {

		// Forming a line from two consecutive points of
		// poly
		Line side = new Line(poly[i], poly[(i + 1) % n]);
		if (isIntersect(side, exline)) {

			// If side is intersects exline
			if (direction(side.p1, p, side.p2) == 0)
				return onLine(side, p);
			count++;
		}
		i = (i + 1) % n;
	} while (i != 0);

	// When count is odd
	return (false);
}

// Driver code
public void check(Point[] polygon,Point p, int n ){
	if (checkInside(polygon, n, p))
		      System.out.println("Point is inside.");
	else
		      System.out.println("Point is outside."); 
}

}