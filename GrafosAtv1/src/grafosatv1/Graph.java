package grafosatv1;

import java.util.ArrayList;

/**
 *
 * @author Rangel
 * @param <TYPE>
 */
public class Graph<TYPE> {
    private ArrayList<Vertex<TYPE>> verteces;
    private ArrayList<Edge<TYPE>> edges;
    private Integer [][] matrixG;
    final static int INF = 999999999;
    
    public Graph() {
        this.verteces = new ArrayList<Vertex<TYPE>>();
        this.edges =  new ArrayList<Edge<TYPE>>();
    }
  
    public void addVertex(TYPE data){
        if(this.getVertex(data)== null){
            Vertex<TYPE> newVertex = new Vertex(data);
            this.verteces.add(newVertex);
        }
    }
    
    public void addEdge(Double cost, TYPE startData, TYPE endData){
        Vertex<TYPE> start = this.getVertex(startData);
        Vertex<TYPE> end = this.getVertex(endData);
        if((start != null) && (end != null)){
            Edge<TYPE> edge = new Edge<TYPE>(cost,start,end);
            end.addInputEdges(edge);
            start.addExitEdges(edge);
            edges.add(edge);
        }
    }
    
    public Vertex<TYPE> getVertex(TYPE data){
        Vertex<TYPE> finder = null;
        for (int i = 0; i < this.verteces.size(); i++) {
            if(this.verteces.get(i).getData().equals(data)){
                finder = this.verteces.get(i);
                break;
            } 
        }
        return finder;
    }
    public Vertex<TYPE> getVertex1(int data){
        Vertex<TYPE> finder = null;
        for (int i = 0; i < this.verteces.size(); i++) {
            if(Integer.valueOf((String)this.verteces.get(i).getData()).equals(data)){
                finder = this.verteces.get(i);
                break;
            } 
        }
        return finder;
    }
    
    /**
     * use para ler grafos não orientados
     */
    public void readFromTxtUnoriented(){
        ArrayList<String[]> reader  = new ReadDoc().FileUrl1();
        for (String[] string : reader) {
            this.addVertex((TYPE)string[0]);
            this.addVertex((TYPE)string[1]);
            this.addEdge(Double.parseDouble(string[2]),(TYPE)string[0],(TYPE)string[1]);
            this.addEdge(Double.parseDouble(string[2]),(TYPE)string[1],(TYPE)string[0]);
        }
    }
    /**
     * use para ler grafos orientados
     */
    public void readFromTxtOriented(){
        ArrayList<String[]> reader  = new ReadDoc().FileUrl1();
        for (String[] string : reader) {
            this.addVertex((TYPE)string[0]);
            this.addVertex((TYPE)string[1]);
            this.addEdge(Double.parseDouble(string[2]),(TYPE)string[0],(TYPE)string[1]);
        }
    }

    public ArrayList<Vertex<TYPE>> getVerteces() {
        return verteces;
    }

    public ArrayList<Edge<TYPE>> getEdges() {
        return edges;
    }
    
    public void listVerteces(){
        for (Vertex<TYPE> vertece : verteces) {
            System.out.print(vertece.getData()+",");
        }
        System.out.println("");
    }
    
    public void listEdges(){
        System.out.println(edges.size());
        for (Edge<TYPE> edge : edges) {
            System.out.println( edge.getStart().getData() +"-->" + edge.getEnd().getData() + " cost:"+edge.getCost() );
        }
    }
    
    public Integer[][] transforIntoMatix(){
        this.matrixG = new Integer [this.verteces.size()+1][this.verteces.size()+1];
        for (Vertex<TYPE> vertece : verteces) {
            for (int i = 0; i < this.verteces.size()+1; i++) {
                int aux = Integer.valueOf((String)vertece.getData());
                if(aux == this.verteces.size()){
                    //aux -= 1;
                }
                this.matrixG[aux][i] = vertece.getCustExpecificEdge(i);             
            }
        }
        Integer [][] matrixGg = new Integer [this.verteces.size()][this.verteces.size()];
        for (int i = 0; i < this.verteces.size(); i++) {
            for (int j = 0; j < this.verteces.size(); j++) {
                if(this.matrixG[i+1][j+1] == 0){
                    matrixGg [i][j] = INF;
                }else{
                    matrixGg [i][j]=this.matrixG[i+1][j+1];
                }
                
            }
        }
        return matrixGg;
    }
    
    public void buscaEmLargura(){
        ArrayList<Vertex<TYPE>> marcados = new ArrayList<Vertex<TYPE>>();
        ArrayList<Vertex<TYPE>> fila = new ArrayList<Vertex<TYPE>>();
        Vertex<TYPE> atual = this.verteces.get(0);
        marcados.add(atual);
        System.out.println(atual.getData());
        fila.add(atual);
        while(fila.size() > 0){
            Vertex<TYPE> visitado = fila.get(0);
            for(int i=0; i < visitado.getExitEdges().size(); i++){
                Vertex<TYPE> proximo = visitado.getExitEdges().get(i).getEnd();
                if (!marcados.contains(proximo)){ //se o vértice ainda não foi marcado
                    marcados.add(proximo);
                    System.out.println(proximo.getData());
                    fila.add(proximo);
                }
            }
            fila.remove(0);
        }
    }
    //////////////////////
    public void floyd(Integer graph[][])
    {
        int V = graph.length;
        int dist[][] = new int[V][V]; // Matriz de solução
        int r [][] = new int[V][V];
        int i, j, k;
        for (i = 0; i < V; i++){
            for (j = 0; j < V; j++){
                dist[i][j] = graph[i][j];
                    if(dist[i][j]==INF){
                        r[i][j]= 0;
                    }else{
                        r[i][j] = j+1;                   
                    }
            }
        }
        for (k = 0; k < V; k++) {
            for (i = 0; i < V; i++) {
                for (j = 0; j < V; j++) {
                    if (dist[i][k] + dist[k][j]< dist[i][j]){
                        dist[i][j] = dist[i][k] + dist[k][j];
                        r[i][j] = r[i][k];
                    }
                }
            }
        }
        for (int l = 0; l < r.length; l++) {
            for (int m = 0; m < r.length; m++) {
                    System.out.print(r[l][m]+" ");
            }
            System.out.println(" ");
        }
        printMatrixD(dist, V);
    }
    
    void printMatrixD(int dist[][], int V)
    {
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (dist[i][j] == INF)
                    System.out.print("-1 ");
                else
                    System.out.print(dist[i][j] + "   ");
            }
            System.out.println();
        }
    }
    ///////////////////
}
