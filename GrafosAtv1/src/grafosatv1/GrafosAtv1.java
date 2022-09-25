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
        g.readFromTxt();
        g.listVerteces();
    }

}
