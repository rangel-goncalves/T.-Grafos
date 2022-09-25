package grafosatv1;
/**
 *
 * @author Rangel
 */
public class Edge<TYPE> {
    private double cost;
    private Vertex<TYPE> start;
    private Vertex<TYPE> end;

    public Edge(double cost, Vertex<TYPE> start, Vertex<TYPE> end) {
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

    public Vertex<TYPE> getStart() {
        return start;
    }

    public void setStart(Vertex<TYPE> start) {
        this.start = start;
    }

    public Vertex<TYPE> getEnd() {
        return end;
    }

    public void setEnd(Vertex<TYPE> end) {
        this.end = end;
    }
    
}
