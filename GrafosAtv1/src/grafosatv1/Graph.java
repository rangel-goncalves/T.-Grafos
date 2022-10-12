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
    private MatrixFloyd floydResult;
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
     * @return Um grafo lido direto de um arquivo .txt
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
    /**
     *@return  Lista todas as arestas no formato (inicio, destino, custo)
     */
    public String listEdges(){
        String a = "";
        for (Edge<TYPE> edge : edges) {
            a=a+edge.getStart().getData() +"-->" + edge.getEnd().getData() + " cost:"+edge.getCost()+"\n";
        }
        return a;
    }
    /**
     * transformando o grafo em uma matriz de adjacencia para facilitar a implementação dos algoritimos de caminho minimo
     */
    public Integer[][] transforIntoMatix(){
        this.matrixG = new Integer [this.verteces.size()+1][this.verteces.size()+1];
        for (Vertex<TYPE> vertece : verteces) {
            for (int i = 0; i < this.verteces.size()+1; i++) {
                int aux = Integer.valueOf((String)vertece.getData());
                this.matrixG[aux][i] = vertece.getCustExpecificEdge(i);             
            }
        }
        /*
            Eliminando a linha e a colula [0,0] so pra não printar tudo INF nela
        */
        Integer [][] matrixGg = new Integer [this.verteces.size()][this.verteces.size()];
        for (int i = 0; i < this.verteces.size(); i++) {
            for (int j = 0; j < this.verteces.size(); j++) {
                if(this.matrixG[i+1][j+1] == 0){
                    if(i==j){
                        matrixGg [i][j] = 0;
                    }else{
                        matrixGg [i][j] = INF;
                    }
                }else{
                    matrixGg [i][j]=this.matrixG[i+1][j+1];
                }
                
            }
        }
        return matrixGg;
    }
    /**
     * Era pra ver a leitura do grafo. (NÂO UTILIZADA)
     */
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
    ////////////////////// FLOYD /////////////////////////////////
    /**
     * Algoritimo para obter o menor caminho em um grafo
     * @param graph 
     */
    public void floyd(Integer graph[][])
    {
        int V = graph.length;
        Integer dist[][] = new Integer[V][V]; // Matriz de solução
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
                    if(i==j){
                        continue;
                    }
                    if (dist[i][k] + dist[k][j]< dist[i][j]){
                        dist[i][j] = dist[i][k] + dist[k][j];
                        r[i][j] = r[i][k];
                    }
                }
            }
        }
        this.floydResult = new MatrixFloyd(r,dist); // Objeto que vai guardar as informações gerada pelo algoritimo
    }
    
    void printMatrixD(int dist[][],int [][]r, int V)
    {
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (dist[i][j] == INF){
                    System.out.print("-1 ");
                }else{
                    System.out.print(dist[i][j] + "   ");
                }
            }
            System.out.println();
        }
    }
    /**
     * Utilizando para interface 
     */
    public String printedFroydR(){
        int [][]m =  this.floydResult.getR();
        String a= "";
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                //System.out.print(m[i][j]+" ");
                if(m[i][j]==999999999){
                    a=a+"INF"+" ";
                }else{
                  a=a+m[i][j]+" ";  
                }
            }
            a= a+"\n";
        }
        return a;
    }
    /**
     * Utilizando para interface 
     */
    public String printedFroydD(){
       Integer [][]m =  this.floydResult.getD();
        String a= "";
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                //System.out.print(m[i][j]+" ");
                if(m[i][j]==999999999){
                    a=a+"INF"+" ";
                }else{
                  a=a+m[i][j]+" ";  
                }
            }
            a= a+"\n";
        }
        return a; 
    }
    /**
     * Utilizando para interface
     * Na view chamar sempre apos a sumFroydD() pois somente apos o somatorios podemos obter os minimos
     * posso por uma chmada direto ja dentro do função de somatorio la no MatrixFoyd mas deixei assim mesmo
     * @return O melhor vertece central
     */
    public int minimofloyd(){
        return this.floydResult.getBestCentralVertex();
    }
    /**
     * Utilizando para interface 
     * @return O vetor dos somatorios de cada linha da Matriz D
     */
    public String sumFroydD(){
        return this.floydResult.getSumD();
    }
    
    /////////////////// FIM do FLOYD ///////////////////////////
}
