package grafosatv2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
     */
    public static void main(String[] args) throws ParseException {
        Graph g = new Graph();
        g.readVertexFromJSON(4000.00);
        //System.out.println(g.listEdges());
        /*JSONArray JSONObject ;
        JSONParser parser = new JSONParser();
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            try {
                JSONObject = (JSONArray) parser.parse(new FileReader(selectedFile));
                System.out.println(JSONObject.get(1).toString());
                System.out.println(JSONObject.size());
                int k = 0;
                for (Object object : JSONObject) {
                    k++;
                }
                System.out.println(k);
                JSONObject a = (JSONObject) JSONObject.get(0);
                Double b = (Double)a.get("latitude");
                String s = (String)a.get("city");
                System.out.println(s);
                DistanceCalculation d = new DistanceCalculation();
                System.out.println("dist = " + d.latAndLgnToDistance(40.7127837, -74.0059413, 40.7127837, -74.0059413));
                Graph g = new Graph();
                g.readVertexFromJSON();
            } catch (IOException ex) {
                System.out.println("ERRO: " + ex.getMessage());
            }
	}*/
        
        
    }

}
