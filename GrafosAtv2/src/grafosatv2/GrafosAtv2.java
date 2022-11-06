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
        Graph g = new Graph();
        g.readGraphFromJSON(500.00);
        g.transforIntoMatix();
        System.out.println(g.havePath(1, 2));
        g.readGraphFromJSON(600.00);
        g.transforIntoMatix();
        System.out.println(g.havePath(100, 1));
        
    }

}
