package grafosatv3;
/**
 * @author Rangel
 */
public class Edge implements Comparable<Edge> {
    
    //private int weight;
    private Double cost;
    private Vertex start;
    private Vertex end; 

    /**
     * Classe com as aresats montadas pelo Grafo inicial que possui todas as aresatas possiveis
     * @param cost 
     */
    public Edge(double cost) {
        this.cost = cost;
    }

    
    public Edge(double cost, Vertex start, Vertex end) {
        this.cost = cost;
        this.start = start;
        this.end = end;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Vertex getStart() {
        return start;
    }

    public void setStart(Vertex start) {
        this.start = start;
    }

    public Vertex getEnd() {
        return end;
    }

    public void setEnd(Vertex end) {
        this.end = end;
    }

    /**
     * Utilizada para Classificar as arestas de menor custo para realizar o Algoritimo de Kruskal
     * @param compareEdge
     * @return 
     */
    @Override
    public int compareTo(Edge compareEdge) {
        return this.cost.compareTo(compareEdge.getCost());
    }
    
}
