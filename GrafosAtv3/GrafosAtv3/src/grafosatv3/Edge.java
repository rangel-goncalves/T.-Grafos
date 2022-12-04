package grafosatv3;
/**
 * @author Rangel
 */
public class Edge implements Comparable<Edge> {
    
    //private int weight;
    private boolean isIncluded = false;
    private Double cost;
    private Vertex start;
    private Vertex end; 

    public Edge(double cost) {
        this.cost = cost;
    }

    
    public Edge(double cost, Vertex start, Vertex end) {
        this.cost = cost;
        this.start = start;
        this.end = end;
    }

    public boolean isIsIncluded() {
        return isIncluded;
    }

    public void setIsIncluded(boolean isIncluded) {
        this.isIncluded = isIncluded;
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

    @Override
    public int compareTo(Edge compareEdge) {
        return this.cost.compareTo(compareEdge.getCost());
    }
    
}
