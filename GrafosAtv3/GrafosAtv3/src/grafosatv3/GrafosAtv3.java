/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package grafosatv3;

import java.util.ArrayList;

/**
 * falta muito, ainda falta muito mas falta menos
 * @author Rangel
 */
public class GrafosAtv3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Graph g = new Graph();
        
        g.readFromTxtUnoriented();
        
        
        
        g.createEdges();
        g.listEdges();
        
        //h.check(polygon, p, 0);
        
        Point p = new Point(3.200000000000002,1.9919999999999938);
        Point p1 = new Point(0.5,0.5);
        if(p==p1){
            System.out.println("sim");
        }
        double[][] pt = new double [4][2];
        
        double[] point = {0.5,0.5};
        pt[0][0]=2.0;
        pt[0][1]=3.5;
        
        pt[1][0]=1.5;
        pt[1][1]=1.5;
        
        pt[2][0]=3.5;
        pt[2][1]=0.9;
        
        pt[3][0]=4.5;
        pt[3][1]=2.0;
        
         //
        Point a = new Point(pt[0][0],pt[0][1]);
        Point b = new Point(pt[1][0],pt[1][1]);
        Point c = new Point(pt[2][0],pt[2][1]);
        Point d = new Point(pt[3][0],pt[3][1]);
        //
        
        Point [] poly = {a,b,c,d};
        ArrayList<Point> polyArray = new ArrayList();
        polyArray.add(a);
        polyArray.add(b);
        polyArray.add(c);
        polyArray.add(d);
        
        System.out.println("Point P(" + p.x + ", " + p.y
                + ") lies inside polygon1: " + Position_Point_WRT_Polygon.isInside(poly, 4, p));
        System.out.println("Point P(" + p.x + ", " + p.y
                + ") lies inside polygon1: " + Position_Point_WRT_Polygon.isInside(g.getPolygons().get(0).getPoints(), 4, p));
        
    }
    
}
