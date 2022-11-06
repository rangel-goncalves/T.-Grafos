package grafosatv2;
/**
 * @author Rangel
 */
public class Edge {
    private double cost;
    private Vertex start;
    private Vertex end;

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
    
}
