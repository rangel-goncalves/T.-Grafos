package grafosatv3;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Rangel
 * @param 
 */

public class Graph {
    
    private MinTree minTree;
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
        this.minTree = new MinTree();
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
                    this.getVertex(9).getCustExpecificEdge(9);
                }
            }
        }
        this.transforIntoMatrix();
    }

    public ArrayList<Vertex> getVerteces() {
        return verteces;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public MinTree getMinTree() {
        return minTree;
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
        System.out.println("Iniciando Algoritimo de Prim");
        this.primMST(matrixGg);
        System.out.println("Fim do Algoritimo de Prim");
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
        System.out.println("Edge \tWeight");
        for (int i = 1; i < this.verteces.size(); i++){
            System.out.println(parent[i] + " - " + i + "\t"+ graph[i][parent[i]]);
            Vertex v = new Vertex(this.getVertex(parent[i]).getPoint(), parent[i]);
            Vertex v1 = new Vertex(this.getVertex(i).getPoint(), i);
            this.minTree.addVertex(v);
            this.minTree.addVertex(v1);
            this.minTree.addEdge(graph[i][parent[i]], v.getOrdem(), v1.getOrdem());
            this.minTree.addEdge(graph[i][parent[i]], v1.getOrdem(), v.getOrdem());
        }
        //System.out.println(this.minTree.getVertex(0).getExitEdges().size());
    }
 
    // Function to construct and print MST for a graph
    // represented using adjacency matrix representation
    public void primMST(Double graph[][])
    {
        // Array to store constructed MST
        int parent[] = new int[this.verteces.size()];
 
        // Key values used to pick minimum weight edge in
        // cut
        Double key[] = new Double[this.verteces.size()];
 
        // To represent set of vertices included in MST
        Boolean mstSet[] = new Boolean[this.verteces.size()];
 
        // Initialize all keys as INFINITE
        for (int i = 0; i < this.verteces.size(); i++) {
            key[i] = Double.MAX_VALUE;
            mstSet[i] = false;
        }
 
        // Always include first 1st vertex in MST.
        key[0] = 0.0; // Make key 0 so that this vertex is
        // picked as first vertex
        parent[0] = -1; // First node is always root of MST
 
        // The MST will have V vertices
        for (int count = 0; count < this.verteces.size() - 1; count++) {
            // Pick thd minimum key vertex from the set of
            // vertices not yet included in MST
            int u = minKey(key, mstSet);
 
            // Add the picked vertex to the MST Set
            mstSet[u] = true;
 
            // Update key value and parent index of the
            // adjacent vertices of the picked vertex.
            // Consider only those vertices which are not
            // yet included in MST
            for (int v = 0; v < this.verteces.size(); v++)
 
                // graph[u][v] is non zero only for adjacent
                // vertices of m mstSet[v] is false for
                // vertices not yet included in MST Update
                // the key only if graph[u][v] is smaller
                // than key[v]
                if (graph[u][v] != 0 && mstSet[v] == false
                    && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }
 
        // print the constructed MST
        this.printMST(parent, graph);
    }
    
    ////////////////////Fim Prim//////////////
    
}
