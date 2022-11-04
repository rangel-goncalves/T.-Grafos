package grafosatv2;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 *
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
        this.verteces = new ArrayList<>();
        this.edges =  new ArrayList<>();
        this.reader1 = new ReadDoc();
    }
  
    public boolean addVertex(Vertex v){
        if(this.getVertex(v.getRank())== null){
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
    
    public Vertex getVertex(Integer data){
        Vertex finder = null;
        for (int i = 0; i < this.verteces.size(); i++) {
            if(this.verteces.get(i).getRank()==(data)){
                finder = this.verteces.get(i);
                break;
            } 
        }
        return finder;
    }
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
    /**
     * Sempre que chamar esta função ira ser aberto uma janela para escolher um arquivo JSON
     * @param maxDistance
     * @throws ParseException 
     */
    public void readVertexFromJSON(Double maxDistance) throws ParseException{
        ReadDoc reader = new ReadDoc();
        JSONArray inJSON = reader.jsomFileURL();
        for (Object object : inJSON) {
            JSONObject obj =(JSONObject)object;
            Vertex v = new Vertex((String)obj.get("city"),(String)obj.get("growth_from_2000_to_2013"),(Double)obj.get("latitude"),
                                                   (Double)obj.get("longitude"),Integer.valueOf((String)obj.get("population")),
                                                   Integer.valueOf((String)obj.get("rank")),
                                                   (String)obj.get("state"));
            
            this.addVertex(v);
        }
            DistanceCalculation d = new DistanceCalculation();
        for (Vertex v : this.verteces) {
            for (int i = 0; i < this.verteces.size(); i++) {
                Double cost = d.latAndLgnToDistance(v.getLatitude(), v.getLongitude(), 
                                                    this.verteces.get(i).getLatitude(), 
                                                    this.verteces.get(i).getLongitude());
                if(cost <= maxDistance && cost != 0.0){
                    this.addEdge(cost, v, this.verteces.get(i));
                }
            }
        }
    }
    /**
     * Use se for testar varios raios de distancia maxima com um mesmo JSON
     * @param maxDistance
     * @throws ParseException 
     */
    public void readVertexFromJSON1(Double maxDistance) throws ParseException{
        
        JSONArray inJSON = this.reader1.jsomFileURL1();
        System.out.println("Aguarde isso pode levar alguns segundos\n(Background sound: MUSICA DE ELEVADOR IRRITANTE)");
        for (Object object : inJSON) {
            JSONObject obj =(JSONObject)object;
            Vertex v = new Vertex((String)obj.get("city"),(String)obj.get("growth_from_2000_to_2013"),(Double)obj.get("latitude"),
                                                   (Double)obj.get("longitude"),Integer.valueOf((String)obj.get("population")),
                                                   Integer.valueOf((String)obj.get("rank")),
                                                   (String)obj.get("state"));
            
            this.addVertex(v);
        }
            DistanceCalculation d = new DistanceCalculation();
        for (Vertex v : this.verteces) {
            for (int i = 0; i < this.verteces.size(); i++) {
                Double cost = d.latAndLgnToDistance(v.getLatitude(), v.getLongitude(), 
                                                    this.verteces.get(i).getLatitude(), 
                                                    this.verteces.get(i).getLongitude());
                if(cost <= maxDistance && cost != 0.0){
                    this.addEdge(cost, v, this.verteces.get(i));
                }
            }
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
            System.out.print(vertece.getRank()+",");
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
            System.out.println(edge.getStart().getRank() +"-->" + edge.getEnd().getRank() + " cost:"+edge.getCost()+"\n");
        }
        return a;
    }
    /**
     * transformando o grafo em uma matriz de adjacencia para facilitar a implementação dos algoritimos de caminho minimo
     * @return 
     */
    public Double[][] transforIntoMatix(){
        this.matrixG = new Double [this.verteces.size()+1][this.verteces.size()+1];
        for (Vertex vertece : verteces) {
            for (int i = 0; i < this.verteces.size()+1; i++) {
                int aux = vertece.getRank();
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
        this.floyd(matrixGg);
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
        System.out.println(atual.getRank());
        fila.add(atual);
        while(!fila.isEmpty()){
            Vertex visitado = fila.get(0);
            for(int i=0; i < visitado.getExitEdges().size(); i++){
                Vertex proximo = visitado.getExitEdges().get(i).getEnd();
                if (!marcados.contains(proximo)){ //se o vértice ainda não foi marcado
                    marcados.add(proximo);
                    System.out.println(proximo.getRank());
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
     * @return 
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
                System.out.print(m[i][j]+" ");
            }
            System.out.println(" ");
            a= a+"\n";
        }
        return a;
    }
    /**
     * Utilizando para interface 
     * @return 
     */
    public String printedFroydD(){
       Double [][]m =  this.floydResult.getD();
        String a= "";
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                //System.out.print(m[i][j]+" ");
                if(m[i][j]==999999999){
                    a=a+"INF"+" ";
                }else{
                  a=a+m[i][j]+" ";  
                }
                System.out.print(m[i][j]+" ");
            }
            System.out.println(" ");
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
    public void havePath(int start, int end){
        int r[][] = this.floydResult.getR();
        Double d [][] = this.floydResult.getD();
        int next = end-1;
        int cur = start-1;
        System.out.println("Ponto de partida: "+this.getVertex(start).getCity());
        while(true){
            if(r[cur][next]!=0){
                System.out.print("distancia percorrida da ultima parada ate a atual: "+d[cur][r[cur][next]-1]);
                cur = r[cur][next]-1;
                if(cur==next){
                    System.out.println(" Parada atual(DESTINO FINAL): "+this.getVertex(cur+1).getCity());
                    System.out.println("tem! Distancia total percorrida: " + d[start-1][end-1]);
                    break;
                }
                System.out.print(" distancia restante: "+d[cur][next]);
                System.out.println(" Parada atual: "+this.getVertex(cur+1).getCity());
            }else{
                System.out.println("Não existe caminho");
                break;
            }
        }
    }
    /////////////////// FIM do FLOYD ///////////////////////////
}
