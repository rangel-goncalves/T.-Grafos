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
        g.readVertexFromJSON1(500.00);
        g.transforIntoMatix();
        g.havePath(1, 2);
        g.readVertexFromJSON1(600.00);
        g.transforIntoMatix();
        g.havePath(2, 1);
        /*g = new Graph();
        g.readVertexFromJSON(700.00);
        g.transforIntoMatix();
        g.havePath(1, 2);*/

    }

}
