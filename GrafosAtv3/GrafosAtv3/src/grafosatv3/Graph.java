package grafosatv3;

import java.util.ArrayList;

/**
 * @author Rangel
 * @param 
 */

public class Graph {
    private ReadDoc reader1;
    private ArrayList<Vertex> verteces;
    private ArrayList<Edge> edges;
    private Double [][] matrixG;
    private MatrixFloyd floydResult;
    final static Double INF = 99999.99;
    
    public Graph() {
        this.reader1 = new ReadDoc();
        this.verteces = new ArrayList<Vertex>();
        this.edges = new ArrayList<Edge>();
    }
  
    public boolean addVertex(Vertex v){
        Double [] coordV = {v.getPoint().x, v.getPoint().y};
        if(this.getVertex(coordV)== null){
            this.verteces.add(v);
            return true;
        }
        return false;
    }
    
    public void addEdge(Double cost, Vertex startData, Vertex endData){
        if((startData != null) && (endData != null)){
            Edge edge = new Edge(cost,startData,endData);
            endData.addInputEdges(edge);
            startData.addExitEdges(edge);
            edges.add(edge);
        }
    }
    
    public Vertex getVertex(Double[] data){
        Vertex finder = null;
        for (int i = 0; i < this.verteces.size(); i++) {
            if(this.verteces.get(i).getPoint().x==data[0] && this.verteces.get(i).getPoint().y ==data[1]){
                finder = this.verteces.get(i);
                break;
            } 
        }
        return finder;
    }
    /*
    public Vertex getVertex1(Integer data){
        Vertex finder = null;
        for (int i = 0; i < this.verteces.size(); i++) {
            if(this.verteces.get(i).getRank()==(data)){
                finder = this.verteces.get(i);
                break;
            } 
        }
        return finder;
    }
    */
    
    public void readFromTxtUnoriented(){
        ArrayList<Point> reader  = new ReadDoc().FileUrl1();
        for (Point point : reader) {
            Vertex v = new Vertex(point,this.verteces.size());
            this.addVertex(v);
            //this.addVertex(string[0]);
            //this.addVertex(string[1]);
            //this.addEdge(Double.parseDouble(string[2]),(TYPE)string[0],(TYPE)string[1]);
            //this.addEdge(Double.parseDouble(string[2]),(TYPE)string[1],(TYPE)string[0]);
        }
    }

    public ArrayList<Vertex> getVerteces() {
        return verteces;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }
    
    public void listVerteces(){
        for (Vertex vertece : verteces) {
            System.out.print(vertece.getPoint().x+","+vertece.getPoint().y+".");
        }
        System.out.println("");
    }
    /**
     *@return  Lista todas as arestas no formato (inicio, destino, custo)
     */
    public String listEdges(){
        String a = "";
        for (Edge edge : edges) {
            //a=a+edge.getStart().getRank() +"-->" + edge.getEnd().getRank() + " cost:"+edge.getCost()+"\n";
            System.out.println(edge.getStart().getPoint().x+","+edge.getStart().getPoint().y +"-->"
                                + edge.getEnd().getPoint().x+","+edge.getEnd().getPoint().y + " cost:"+edge.getCost()+"\n");
        }
        return a;
    }
    /**
     * transformando o grafo em uma matriz de adjacencia para facilitar a implementação dos algoritimos de caminho minimo
     * @return 
     */
    public Double[][] transforIntoMatrix(){
        this.matrixG = new Double [this.verteces.size()+1][this.verteces.size()+1];
        for (Vertex vertece : verteces) {
            for (int i = 0; i < this.verteces.size()+1; i++) {
                int aux = vertece.getOrdem();
                this.matrixG[aux][i] = vertece.getCustExpecificEdge(i);             
            }
        }
        /*
            Eliminando a linha e a colula [0,0] so pra não printar tudo INF nela
        */
        Double [][] matrixGg = new Double [this.verteces.size()][this.verteces.size()];
        for (int i = 0; i < this.verteces.size(); i++) {
            for (int j = 0; j < this.verteces.size(); j++) {
                if(this.matrixG[i+1][j+1] == 0){
                    if(i==j){
                        matrixGg [i][j] = 0.00;
                    }else{
                        matrixGg [i][j] = INF;
                    }
                }else{
                    matrixGg [i][j]=this.matrixG[i+1][j+1];
                }
                //System.out.println(matrixGg[i][j]+" ");
            }
            //System.out.println("*****************************");
        }
        System.out.println("Iniciando Algoritimo de Floyd");
        this.floyd(matrixGg);
        System.out.println("Fim do Algoritimo de Floyd");
        return matrixGg;
    }
    /**
     * Era pra ver a leitura do grafo. (NÂO UTILIZADA)
     */
    public void buscaEmLargura(){
        ArrayList<Vertex> marcados = new ArrayList<>();
        ArrayList<Vertex> fila = new ArrayList<>();
        Vertex atual = this.verteces.get(0);
        marcados.add(atual);
        System.out.println(atual.getPoint().x + "," + atual.getPoint().y);
        fila.add(atual);
        while(!fila.isEmpty()){
            Vertex visitado = fila.get(0);
            for(int i=0; i < visitado.getExitEdges().size(); i++){
                Vertex proximo = visitado.getExitEdges().get(i).getEnd();
                if (!marcados.contains(proximo)){ //se o vértice ainda não foi marcado
                    marcados.add(proximo);
                    System.out.println(proximo.getPoint().x + "," + proximo.getPoint().y);
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
    public void floyd(Double graph[][])
    {
        int V = graph.length;
        Double dist[][] = new Double[V][V]; // Matriz de solução
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
    
    /**
     * Esse metodos vai mostrar os resultados para todas as combinações possiveis de caminhos
     * Ex: Caminho de ([para todos]:n ate [para todos]:m)---> |n|,|m| = Verteces().size()
     * vale notar que ele ira verificar o maminho de n ate m e tbm de m ate n 
     * Ex: Caminho de (1 ate 2) e apos (não sequencialmente) o Caminho de (2 ate 1)
     */
    
    public String printedFroydR(){
        int [][]m =  this.floydResult.getR();
        String a= "";
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                //System.out.print(m[i][j]+" ");
                if(m[i][j]==999999999){
                    //a=a+"INF"+" ";
                    System.out.print("INF"+" ");
                }else{
                  //a=a+m[i][j]+" "; 
                    System.out.print(m[i][j]+" ");
                }
            }
            //a= a+"\n";
            System.out.println(" ");
        }
        return a;
    }
    
}
