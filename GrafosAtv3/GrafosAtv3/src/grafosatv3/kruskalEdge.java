package grafosatv3;

/**
 *
 * @author Rangel
 */
public class kruskalEdge implements Comparable<kruskalEdge>{
    private int src, dest;
    private Double weight;

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public int getDest() {
        return dest;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    @Override
    public int compareTo(kruskalEdge compareEdge) {
        return this.weight.compareTo(compareEdge.getWeight());
    }
    
}
