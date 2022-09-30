package grafosatv1;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Rangel
 */
public class GrafosAtv1 {

    /**
     * @param args the command line arguments
     **/
    public static void main(String[] args) {
        
        Graph g = new Graph();
        g.readFromTxtOriented();
        Integer [][]m =  g.transforIntoMatix();
        /*for (int i = 0; i < g.getVerteces().size(); i++) {
            for (int j = 0; j < g.getVerteces().size(); j++) {
                System.out.print(m[i][j]+" ");
            }
            System.out.println(" ");
        }*/
        //System.out.println(m.length);
        //g.listEdges();
        g.floyd(m);
        //g.buscaEmLargura();

    }

}
