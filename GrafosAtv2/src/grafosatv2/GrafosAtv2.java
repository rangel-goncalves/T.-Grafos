package grafosatv2;

import org.json.simple.parser.ParseException;

/**
 *
 * @author Rangel
 * Referencias:
 *              calculo das distancias: 
 *              https://thiagovespa.com.br/blog/2010/09/10/distancia-utilizando-coordenadas-geograficas-em-java/
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
        System.out.println(g.havePath(1, 2));
        maxDistance = 500.00;
        g.readGraphFromJSON(maxDistance);
        System.out.println(g.havePath(100, 1));
        /*
            //Gerar todas as possibilidades de caminhos  para a distância escolhido.
            maxDistance = (escolha a distância);
            g.rodarTodosOsCaminhos();
        */
    }
}
