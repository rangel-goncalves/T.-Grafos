package grafosatv3;

/**
 * Arestas para usar no algoritimo de Kruskal, essa classe existe apenas para deixar o programa um pouco mais organizado,
 * e poder rodar o algoritimo juntamente com o Algoritimo de Prim.
 * @author Rangel
 */
public class kruskalEdge implements Comparable<kruskalEdge>{
    private int src, dest;
    private Double weight;
    
    /**
     * Arestas para usar no algoritimo de Kruskal, essa classe existe apenas para deixar o programa um pouco mais organizado,
 *   *e poder rodar o algoritimo juntamente com o Algoritimo de Prim.
     */
    public kruskalEdge() {
    }
    
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
