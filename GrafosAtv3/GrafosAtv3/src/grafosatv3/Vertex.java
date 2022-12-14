package grafosatv3;

import java.util.ArrayList;
/**
 *
 * @author Rangel
 *
 */
public class Vertex {
    
    private int ordem;
    private Point point;
    private ArrayList<Edge> inputEdges;
    private ArrayList<Edge> exitEdges;
    private ArrayList<Vertex> adjList;
    private Boolean visited;

    public Vertex(Point point, int ordem) {
        this.point = point;
        this.ordem = ordem;
        this.inputEdges = new ArrayList<>();
        this.exitEdges =  new ArrayList<>();
        this.adjList = new ArrayList<>();
        this.visited = false;
    }
    
    
    public Vertex() {
        this.point = new Point();
        this.inputEdges = new ArrayList<>();
        this.exitEdges = new ArrayList<>();
    }

    public ArrayList<Edge> getInputEdges() {
        return inputEdges;
    }

    public void addInputEdges(Edge inputEdge) {
        this.inputEdges.add(inputEdge);
    }

    public ArrayList<Edge> getExitEdges() {
        return exitEdges;
    }

    public void addExitEdges(Edge exitEdges) {
        this.exitEdges.add(exitEdges);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public Boolean getVisited() {
        return visited;
    }
    
    public void resetVisited() {
        this.visited = false;
    }
    
    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public ArrayList<Vertex> getAdjList() {
        return adjList;
    }

    /**
     * Função utilizada para auxiliar em MinTreePrim.dfs()
     * @param adjList 
     */
    public void setAdjList(ArrayList<Vertex> adjList) {
        this.adjList = adjList;
    }
    
    
    
    /**
     * 
     * @param data
     * @return o custo de uma determinada aresta se ela não existir retorna 0 
     */
    public Double getCustExpecificEdge(int data){
        
        Double cost = 0.00;
        
        for (Edge exitEdge : exitEdges) {
            if(exitEdge.getEnd().getOrdem() == data){
                cost = (Double)exitEdge.getCost();
                return cost;
            }
        }
        return cost;
    }

    @Override
    public String toString() {
        String a= "[";
        for (Edge adj : this.exitEdges) {
            a+= adj.getEnd().getOrdem()+",";
        }
        a = a.substring(0, a.length()-1)+ "]";
        return a;
    }
    
}
