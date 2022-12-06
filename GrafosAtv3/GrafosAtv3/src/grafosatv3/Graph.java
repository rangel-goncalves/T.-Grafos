package grafosatv3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
/**
 * @author Rangel
 * @param 
 */

public class Graph {
    private kruskalEdge[] kruskalEdge;
    private MinTreePrim minTreePrim;
    private Point start;
    private Point end;
    private ArrayList<Vertex> verteces;
    private ArrayList<Edge> edges;
    private ArrayList<Polygon> polygons;
    private Double [][] matrixG;
    final static Double INF = 99999.99;
    
    public Graph() {
        this.verteces = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.polygons = new ArrayList<>();
        this.minTreePrim = new MinTreePrim();
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
            if(Objects.equals(this.verteces.get(i).getPoint().x, data[0]) && Objects.equals(this.verteces.get(i).getPoint().y, data[1])){
                finder = this.verteces.get(i);
                break;
            } 
        }
        return finder;
    }
    
    public Vertex getVertex(int data){
        Vertex finder = null;
        for (int i = 0; i < this.verteces.size(); i++) {
            if(Objects.equals(this.verteces.get(i).getOrdem() , data)){
                finder = this.verteces.get(i);
                break;
            } 
        }
        return finder;
    }
    
    public void readFromTxtUnoriented(){
        ArrayList<Point> reader  = new ReadDoc().FileUrl1();
        this.start = reader.remove(0);
        Vertex v = new Vertex(this.start, this.verteces.size());
        this.addVertex(v);
        this.end = reader.remove(0);
        Point init = reader.remove(0);
        int n = init.x.intValue();
        int k = reader.remove(0).x.intValue();
        
        for (int i = 0; i < n; i++) {
            Polygon lixo =  new Polygon();
            this.polygons.add(lixo);
        }
        
        while(!reader.isEmpty()){
            
            for (int i = 0; i < n; i++) {

                for (int j = 0; j < k; j++) {

                    Point p = new Point();
                    if(!reader.isEmpty()){
                        p = reader.remove(0);
                    }else{
                        break;
                    }

                    v = new Vertex(p, this.verteces.size());
                    this.addVertex(v);
                    this.polygons.get(i).poly.add(p);
                    
                }

                if(!reader.isEmpty()){
                    k = reader.remove(0).x.intValue();
                }else{
                        break;
                }
            }
        }
        v = new Vertex(this.end, this.verteces.size());
        this.addVertex(v);
    }
    
    /**
     * Função utilizada para a acriação das arestas.
     * Cria uma equação para cada dupla de pontos verificados e verifica se estes pontos podem ser ligados
     */
   public void createEdges(){
        for (Vertex vertece : this.verteces) {
            Double Y = 0.0;
            for (Vertex vertece1 : this.verteces) {
                Double m = 0.0;
                Boolean dentro = false;
                if(Objects.equals(vertece.getPoint().x, vertece1.getPoint().x)){
                    continue;
                }
                
                // m = coeficiente angular da reta
                m = (vertece.getPoint().y - vertece1.getPoint().y)/(vertece.getPoint().x - vertece1.getPoint().x);
                //verifica se o valor de x do ponto de destino é maior ou menor do que o do ponto de origem 
                // para limitar o segmento de reta
                if(vertece1.getPoint().x<=vertece.getPoint().x){
                    // se for menor o valor de X decresse ate atingir o valor de x
                    for (Double X = vertece.getPoint().x;
                        X > vertece1.getPoint().x 
                        ; X -= 0.01) {
                        // equação da reta
                        Y = (m * (X - vertece.getPoint().x)) + vertece.getPoint().y;
                        Point p = new Point(X,Y);
                        // testa se o algum dos pontos pertencentes a reta passa por dentro de algum dos poligonos
                        for (int j = 0; j < this.polygons.size(); j++) {
                            if(Position_Point_WRT_Polygon.isInside(this.polygons.get(j).getPoints(),
                                    this.polygons.get(j).getPoints().length,
                                    p)){
                                dentro = true;
                            }
                        }
                    }
                }else{
                    // se for maior o valor de X acresse ate atingir o valor de x
                    for (Double X = vertece.getPoint().x;
                        X < vertece1.getPoint().x
                        ; X += 0.01) {
                        // equação da reta
                        Y = (m * (X - vertece.getPoint().x)) + vertece.getPoint().y;
                        Point p = new Point(X,Y);
                        // testa se o algum dos pontos pertencentes a reta passa por dentro de algum dos poligonos
                        for (int j = 0; j < this.polygons.size(); j++) {
                            if(Position_Point_WRT_Polygon.isInside(this.polygons.get(j).getPoints(),
                                    this.polygons.get(j).getPoints().length,
                                    p)){
                                dentro = true;
                            }
                        }
                    }
                }
                // caso nenhum ponto esteja dentro de algum poligono a aresta é criada
                if(!dentro){
                    // calculo do custo da aresta (módulo do vetor entre os dois pontos)
                    Double cost = Math.sqrt(Math.pow((vertece.getPoint().x-vertece1.getPoint().x), 2)
                                 +Math.pow((vertece.getPoint().y-vertece1.getPoint().y), 2));
                    
                    this.addEdge(cost, vertece, vertece1);
                }
            }
        }
        /**
         * esse laço de repetição serve para adicionar as arestas dos poligonos no grafo
         */
        for (int i = 0; i < this.polygons.size(); i++) {
            int n = this.polygons.get(i).getPoints().length;
            for (int j = 0; j < n; j++) {
                if(j==this.polygons.get(i).getPoints().length-1){
                    
                    Double cost = Math.sqrt(Math.pow((this.polygons.get(i).getPoints()[j].x-this.polygons.get(i).getPoints()[0].x), 2)
                                 +Math.pow((this.polygons.get(i).getPoints()[j].y-this.polygons.get(i).getPoints()[0].y), 2));
                    
                    Double[] v = {this.polygons.get(i).getPoints()[j].x,this.polygons.get(i).getPoints()[j].y};
                    Double[] v1 = {this.polygons.get(i).getPoints()[0].x,this.polygons.get(i).getPoints()[0].y};
                    this.addEdge(cost, this.getVertex(v), this.getVertex(v1));
                    this.addEdge(cost, this.getVertex(v1), this.getVertex(v));
                    break;
                }
                Double cost = Math.sqrt(Math.pow((this.polygons.get(i).getPoints()[j].x-this.polygons.get(i).getPoints()[j+1].x), 2)
                                 +Math.pow((this.polygons.get(i).getPoints()[j].y-this.polygons.get(i).getPoints()[j+1].y), 2));
                Double[] v = {this.polygons.get(i).getPoints()[j].x,this.polygons.get(i).getPoints()[j].y};
                Double[] v1 = {this.polygons.get(i).getPoints()[j+1].x,this.polygons.get(i).getPoints()[j+1].y};
                this.addEdge(cost, this.getVertex(v), this.getVertex(v1));
                this.addEdge(cost, this.getVertex(v1), this.getVertex(v));
            }
        }
        ///////// this.transforIntoMatrix(); faz parte da iniciação para rodar o algoritimo de Prim
        this.transforIntoMatrix();
        // criação das arestas para o Algoritimo de Kruskal
        kruskalEdge = new kruskalEdge[this.edges.size()];
        for (int i = 0; i < this.edges.size(); ++i){
            this.kruskalEdge[i] = new kruskalEdge();
            this.kruskalEdge[i].setSrc(this.edges.get(i).getStart().getOrdem());
            this.kruskalEdge[i].setDest(this.edges.get(i).getEnd().getOrdem());
            this.kruskalEdge[i].setWeight(this.edges.get(i).getCost());
        }
        System.out.println("\t"+"Iniciando Algoritimo de Kruskal");
        this.KruskalMST();
        System.out.println("\t"+"Fim do Algoritimo de Kruskal");
    }

    public ArrayList<Vertex> getVerteces() {
        return verteces;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public MinTreePrim getMinTreePrim() {
        return minTreePrim;
    }
    
    public void listVerteces(){
        for (Vertex vertece : verteces) {
            System.out.print("("+vertece.getPoint().x+","+vertece.getPoint().y+")");
        }
        System.out.println("");
    }
    /**
     *@return  Lista todas as arestas no formato (inicio, destino, custo)
     */
    public String listEdges(){
        String a = "";
        for (Edge edge : edges) {
            System.out.println(edge.getStart().getPoint().x+","+edge.getStart().getPoint().y +"-->"
                                + edge.getEnd().getPoint().x+","+edge.getEnd().getPoint().y + " cost:"+edge.getCost()+"\n");
        }
        return a;
    }
    /**
     * transformando o grafo em uma matriz de adjacencia para facilitar a implementação do Algoritimo de Prim 
     */
    public void printGraph(){
        
        for (Vertex v : this.verteces) {
            System.out.println("Vertece "+v.getOrdem()+":"+v.toString());
        }
    }
    
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
        Double [][] matrixGg = new Double [this.verteces.size()+1][this.verteces.size()+1];
        for (int i = 0; i < this.verteces.size(); i++) {
            for (int j = 0; j < this.verteces.size(); j++) {
                if(this.matrixG[i][j] == 0 && this.matrixG[j][i] == 0){
                    if(i==j){
                        matrixGg [i][j] = 0.00;
                    }else{
                        matrixGg [i][j] = INF;
                    }
                }else{
                    matrixGg [i][j]=this.matrixG[i][j] == 0 ? this.matrixG[j][i]: this.matrixG[i][j] ;
                }
            }
        }
        this.printGraph();
        System.out.println("\t"+"Iniciando Algoritimo de Prim");
        this.primMST(matrixGg);
        System.out.println("\t"+"Fim do Algoritimo de Prim");
        
        return matrixGg;
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(ArrayList<Polygon> polygons) {
        this.polygons = polygons;
    }
    
    //////////////////////Prim////////////////
    
  public int minKey(Double key[], Boolean mstSet[])
    {
        // Initialize min value
        Double min = Math.abs(Double.MAX_VALUE);
        int min_index = -1;
 
        for (int v = 0; v < this.verteces.size(); v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }
 
        return min_index;
    }
 
    /**
     * Função Auxiliar(ALgoritimo de Prim). Printa a Arvore mínima ao mesmo tempo que a salva na classe MinTreePrim.
     * 
     * @param parent
     * @param graph 
     */
    public void printMST(int parent[], Double graph[][])
    {
        Double minimumCost=0.0;
        System.out.println("Edge \tWeight");
        for (int i = 1; i < this.verteces.size(); i++){
            System.out.println(parent[i] + " - " + i + "\t"+ graph[i][parent[i]]);
            minimumCost +=graph[i][parent[i]];
            Vertex v = new Vertex(this.getVertex(parent[i]).getPoint(), parent[i]);
            Vertex v1 = new Vertex(this.getVertex(i).getPoint(), i);
            this.minTreePrim.addVertex(v);
            this.minTreePrim.addVertex(v1);
            this.minTreePrim.addEdge(graph[i][parent[i]], v.getOrdem(), v1.getOrdem());
            this.minTreePrim.addEdge(graph[i][parent[i]], v1.getOrdem(), v.getOrdem());
        }
        System.out.println("Minimum Cost Spanning Tree "
                           + minimumCost);
        this.minTreePrim.setPolygons(this.polygons);
    }
    
    /**
     * Algoritimo de Prim para geração de arvore mínima
     * @param graph
     */
    public void primMST(Double graph[][]){
        
        int parent[] = new int[this.verteces.size()];

        Double key[] = new Double[this.verteces.size()];

        Boolean mstSet[] = new Boolean[this.verteces.size()];

        for (int i = 0; i < this.verteces.size(); i++) {
            key[i] = Double.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0.0;
        parent[0] = -1;
 
        for (int count = 0; count < this.verteces.size() - 1; count++) {
            int u = minKey(key, mstSet);
 
            mstSet[u] = true;

            for (int v = 0; v < this.verteces.size(); v++){

                if (graph[u][v] != 0 && mstSet[v] == false
                    && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }
        this.printMST(parent, graph);
    }
    
    ////////////////////Fim Prim//////////////
    
    ///////////////////kruskal////////////////
    
    /**
     * Função Auxiliar(ALgoritimo de Kruskal)
     * @param subsets
     * @param i
     * @return 
     */
    public int find(subSet subsets[], int i)  
    {
        if (subsets[i].parent != i)
            subsets[i].parent
                = find(subsets, subsets[i].parent);
 
        return subsets[i].parent;
    }
    
    /**
     * Função Auxiliar(ALgoritimo de Kruskal)
     * @param subsets
     * @param x
     * @param y 
     */
    public void Union(subSet subsets[], int x, int y)
    {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;

        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }
    
    /**
     * Função Auxiliar(ALgoritimo de Kruskal). Printa a Arvore mínima
     * @param result
     * @param i
     * @param e 
     */
    public void PrintKruskalMST(kruskalEdge result[],int i, int e){
        Double minimumCost = 0.0;
        System.out.println("Edge \tWeight");
        for (i = 0; i < e; ++i) {
            System.out.println(result[i].getSrc() + " - "
                               + result[i].getDest()
                               + "\t"+ result[i].getWeight());
            minimumCost += result[i].getWeight();
        }
        System.out.println("Minimum Cost Spanning Tree "
                           + minimumCost);
    }
 
    /**
     * Algoritimo de Kruskal para geração de arvore mínima
     */
    public void KruskalMST()
    {
        kruskalEdge result[] = new kruskalEdge[this.verteces.size()];
 
        int e = 0;
 
        int i = 0;
        for (i = 0; i < this.verteces.size(); ++i){
            result[i] = new kruskalEdge();
        }

        Arrays.sort(this.kruskalEdge);
 
        subSet subsets[] = new subSet[this.verteces.size()];
        for (i = 0; i < this.verteces.size(); ++i)
            subsets[i] = new subSet();
 
        for (int v = 0; v < this.verteces.size(); ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
 
        i = 0;

        while (e < this.verteces.size() - 1) {
            
            kruskalEdge next_edge = this.kruskalEdge[i++];
 
            int x = find(subsets, next_edge.getSrc());
            int y = find(subsets, next_edge.getDest());
 
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
        }
        this.PrintKruskalMST(result, i, e);
    }
    /////////////////Fim kruskal/////////////
    
}
