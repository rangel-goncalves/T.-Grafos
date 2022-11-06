package grafosatv2;

import org.json.simple.parser.ParseException;

/**
 *
 * @author Rangel
 * Referencias:
 *              calculo das distancias: https://thiagovespa.com.br/blog/2010/09/10/distancia-utilizando-coordenadas-geograficas-em-java/
 */
public class GrafosAtv2 {

    /**
     * @param args the command line arguments
     * @throws org.json.simple.parser.ParseException
     */
    public static void main(String[] args) throws ParseException {
        Double maxDistance = 150.00;
        Graph g = new Graph();
        g.readGraphFromJSON(maxDistance);
        //System.out.println(g.havePath(1, 2));
        //maxDistance = 600.00;
        //g.readGraphFromJSON(maxDistance);
        //System.out.println(g.havePath(100, 1));
        for (int i = 1; i < g.getVerteces().size()-1; i++) {
            for (int j = 1; j < g.getVerteces().size()-1; j++) {
                if(i!=j){
                    System.out.println(g.havePath(i, j));
                }
            }
        }
        
    }

}
