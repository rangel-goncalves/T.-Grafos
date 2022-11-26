/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package grafosatv3;

/**
 *
 * @author Rangel
 */
public class GrafosAtv3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        PointInPolygon h = new PointInPolygon();
        //h.check(polygon, p, 0);
        
        Point p = new Point(0.9,0.9);
        
        double[][] pt = new double [4][2];
        
        double[] point = {0.5,0.5};
        pt[0][0]=0.0;
        pt[0][1]=0.0;
        
        pt[1][0]=1.0;
        pt[1][1]=0.0;
        
        pt[2][0]=0.0;
        pt[2][1]=1.0;
        
        pt[3][0]=1.0;
        pt[3][1]=1.0;
        
         //
        Point a = new Point(pt[0][0],pt[0][1]);
        Point b = new Point(pt[1][0],pt[1][1]);
        Point c = new Point(pt[2][0],pt[2][1]);
        Point d = new Point(pt[3][0],pt[3][1]);
        //
        
        System.out.println(PointInPolygon.pointInPolygon(pt, point));
        Point [] poly = {a,b,c,d};
        h.check(poly, p, 4);
        
        System.out.println("Point P(" + p.x + ", " + p.y
                + ") lies inside polygon1: " + Position_Point_WRT_Polygon.isInside(poly, 4, p));
        
    }
    
}
