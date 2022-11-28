package grafosatv3;

import java.util.List;

/**
 *
 * @author Rangel
 */
public class Prim {
   /* 
    private List<Vertex> graph;
    
    public void run() {
    if (graph.size() > 0) {
        graph.get(0).setIsVisited(true);
    }
    while (isDisconnected()) {
        Edge nextMinimum = new Edge(9999999.99999);
        Vertex nextVertex = graph.get(0);
        for (Vertex vertex : graph) {
            if (vertex.isIsVisited()) {
                Pair<Vertex, Edge> candidate = vertex.nextMinimum();
                if (candidate.getValue().getWeight() < nextMinimum.getWeight()) {
                    nextMinimum = candidate.getValue();
                    nextVertex = candidate.getKey();
                }
            }
        }
        nextMinimum.setIsIncluded(true);
        nextVertex.setIsVisited(true);
    }
    }
    
    private boolean isDisconnected() {
    for (Vertex vertex : graph) {
        if (!vertex.isIsVisited()) {
            return true;
        }
    }
    return false;
    }*/
}
