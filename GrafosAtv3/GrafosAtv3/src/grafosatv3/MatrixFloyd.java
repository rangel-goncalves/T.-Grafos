package grafosatv3;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Rangel
 */
public class MatrixFloyd {
    private int [][] r;
    private Double [][]d;
    private ArrayList<Double> sumD;

    public MatrixFloyd(int[][] r, Double[][] d) {
        this.r = r;
        this.d = d;
        this.sumD = new ArrayList();
    }

    public int[][] getR() {
        return r;
    }

    public Double[][] getD() {
        return d;
    }

    public String getSumD() {
        String a = "";
        
        for (int i = 0; i < d.length; i++) {
            Double result=0.00;
            for (int j = 0; j < d.length; j++) {
                if(d[i][j]==99999.99 && i!=j){
                    result = 99999.99;
                    
                    break;
                }
                result +=d[i][j];
                
            }
            this.sumD.add(result); // sera utilizado na escolha do melhor vertice central
            if(result>=99999.99){
                a = a+"INF"+"\n";
            }else{
                a = a+result+"\n";
            }
        }
        return a;
    }
    
    public int getBestCentralVertex(){
        /*
            encontra o(os) vertices com o menor somatorio dos pesos do caminho
        */
        Double min = Collections.min(this.sumD);
        ArrayList<Integer> minimals =  new ArrayList();
        for (int i = 0; i < this.sumD.size()-1; i++) {
            if(this.sumD.get(i).equals(min)){
                minimals.add(i);
            }
        }
        /*
            em caso de empate essa parte desempata usando o requisito de menor peso para o vertece mais distante
        */
        int result = 99999;
        for (Integer minimal : minimals) {
            Double []a  = d[minimal];
            System.out.println(minimal+1); // o + 1 é pq o contador começa do 0
            ArrayList<Integer> b =  new ArrayList(Arrays.asList(a));
            if(result>Collections.max(b)){  // se o resultado anterior for maior que o atualmente verificado atualiso o valor
                result = minimal+1;
            }
        }
        System.out.println("melhor: "+ result);
        return result;
    }
    
}
