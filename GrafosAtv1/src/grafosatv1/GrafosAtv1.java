package grafosatv1;

import java.util.ArrayList;
import java.util.StringTokenizer;
import view.view1;

/**
 *
 * @author Rangel
 * links:
 *      pastas:https://drive.google.com/drive/folders/1s-pYp_1A6mTmS24ktKJNGZ5m83LeNgj8?usp=sharing
 *      .jar:https://drive.google.com/file/d/1MQCthQPqMZbIS2cK4cmFGJDDpQTXbpbn/view?usp=sharing
 */
public class GrafosAtv1 {

    /**
     * @param args the command line arguments
     **/
    public static void main(String[] args) {
        view1 v = new view1();
        v.setVisible(true);
        /*Graph g = new Graph();
        g.readFromTxtOriented();
        Integer [][]m =  g.transforIntoMatix();
        String a= "";
        for (int i = 0; i < g.getVerteces().size(); i++) {
            for (int j = 0; j < g.getVerteces().size(); j++) {
                //System.out.print(m[i][j]+" ");
                if(m[i][j]==999999999){
                    a=a+"INF"+" ";
                }else{
                  a=a+m[i][j]+" ";  
                }
            }
            a= a+"\n";
        }
        System.out.println(a);
        //System.out.println(m.length);
        //g.listEdges();
        //g.floyd(m);
        //g.buscaEmLargura();*/

    }

}
