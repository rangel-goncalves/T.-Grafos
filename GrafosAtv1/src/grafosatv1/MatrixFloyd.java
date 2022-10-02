package grafosatv1;

/**
 *
 * @author Rangel
 */
public class MatrixFloyd {
    private int [][] r;
    private int [][]d;
    private int [] sumD;

    public MatrixFloyd(int[][] r, int[][] d) {
        this.r = r;
        this.d = d;
    }

    public int[][] getR() {
        return r;
    }

    public int[][] getD() {
        return d;
    }

    public String getSumD() {
        String a = "";
        for (int i = 0; i < d.length; i++) {
            int result=0;
            for (int j = 0; j < d.length; j++) {
                if(d[i][j]==999999999){
                    result = 999999999;
                    break;
                }
                result +=d[i][j];
            }
            if(result>=999999999){
                a = a+"INF"+"\n";
            }else{
                a = a+result+"\n";
            }
        }
        //System.out.println(a);
        return a;
    }
    
}
