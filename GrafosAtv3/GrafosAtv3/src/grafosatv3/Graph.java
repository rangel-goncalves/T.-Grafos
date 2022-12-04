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

    public void createEdges(){
        for (Vertex vertece : this.verteces) {
            Double Y = 0.0;
            for (Vertex vertece1 : this.verteces) {
                Double m = 0.0;
                Boolean dentro = false;
                if(Objects.equals(vertece.getPoint().x, vertece1.getPoint().x)){
                    continue;
                }
                int i = 0;
                m = (vertece.getPoint().y - vertece1.getPoint().y)/(vertece.getPoint().x - vertece1.getPoint().x);
         
                if(vertece1.getPoint().x<=vertece.getPoint().x){
                    for (Double X = vertece.getPoint().x;
                        X > vertece1.getPoint().x 
                        ; X -= 0.01) {
                    Y = (m * (X - vertece.getPoint().x)) + vertece.getPoint().y;
                    Point p = new Point(X,Y);
                    for (int j = 0; j < this.polygons.size()-1; j++) {
                        if(Position_Point_WRT_Polygon.isInside(this.polygons.get(j).getPoints(),this.polygons.get(j).getPoints().length,p)){
                        dentro = true;
                        }
                    }
                    
                }
                }else{
                    for (Double X = vertece.getPoint().x;
                        X < vertece1.getPoint().x
                        ; X += 0.01) {
                        Y = (m * (X - vertece.getPoint().x)) + vertece.getPoint().y;
                        Point p = new Point(X,Y);
                        for (int j = 0; j < this.polygons.size()-1; j++) {
                            if(Position_Point_WRT_Polygon.isInside(this.polygons.get(j).getPoints(),this.polygons.get(j).getPoints().length,p)){
                                dentro = true;
                            }
                        }
                    }
                }

                i++;
                if(!dentro){
                    
                    Double cost = Math.sqrt(Math.pow((vertece.getPoint().x-vertece1.getPoint().x), 2)
                                 +Math.pow((vertece.getPoint().y-vertece1.getPoint().y), 2));
                    
                    this.addEdge(cost, vertece, vertece1);
                }
            }
        }
        this.transforIntoMatrix();
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
        Double [][] matrixGg = new Double [this.verteces.size()+1][this.verteces.size()+1];
        for (int i = 0; i < this.verteces.size(); i++) {
            for (int j = 0; j < this.verteces.size(); j++) {
                if(this.matrixG[i][j] == 0 /*&& this.matrixG[j][i] == 0*/){
                    if(i==j){
                        matrixGg [i][j] = 0.00;
                    }else{
                        matrixGg [i][j] = INF;
                    }
                }else{
                    matrixGg [i][j]=this.matrixG[i][j] /*== 0 ? this.matrixG[j][i]: this.matrixG[i][j]*/ ;
                }
            }
        }
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
 
    // A utility function to print the constructed MST
    // stored in parent[]
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
    }
 
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
    
    public int find(subSet subsets[], int i)
    {
        // find root and make root as parent of i
        // (path compression)
        if (subsets[i].parent != i)
            subsets[i].parent
                = find(subsets, subsets[i].parent);
 
        return subsets[i].parent;
    }
    
    public void Union(subSet subsets[], int x, int y)
    {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);
 
        // Attach smaller rank tree under root
        // of high rank tree (Union by Rank)
        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
 
        // If ranks are same, then make one as
        // root and increment its rank by one
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }
    
    public void KruskalMST()
    {
        // This will store the resultant MST
        kruskalEdge result[] = new kruskalEdge[this.verteces.size()];
 
        // An index variable, used for result[]
        int e = 0;
 
        // An index variable, used for sorted edges
        int i = 0;
        for (i = 0; i < this.verteces.size(); ++i)
            result[i] = new kruskalEdge();
 
        // Step 1:  Sort all the edges in non-decreasing
        // order of their weight.  If we are not allowed to
        // change the given graph, we can create a copy of
        // array of edges
        Arrays.sort(this.kruskalEdge);
 
        // Allocate memory for creating V subsets
        subSet subsets[] = new subSet[this.verteces.size()];
        for (i = 0; i < this.verteces.size(); ++i)
            subsets[i] = new subSet();
 
        // Create V subsets with single elements
        for (int v = 0; v < this.verteces.size(); ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
 
        i = 0; // Index used to pick next edge
 
        // Number of edges to be taken is equal to V-1
        while (e < this.verteces.size() - 1) {
            // Step 2: Pick the smallest edge. And increment
            // the index for next iteration
            kruskalEdge next_edge = this.kruskalEdge[i++];
 
            int x = find(subsets, next_edge.getSrc());
            int y = find(subsets, next_edge.getDest());
 
            // If including this edge doesn't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
            // Else discard the next_edge
        }
 
        // print the contents of result[] to display
        // the built MST
        System.out.println("Following are the edges in "
                           + "the constructed MST");
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
    
    /////////////////Fim kruskal/////////////
    
}
