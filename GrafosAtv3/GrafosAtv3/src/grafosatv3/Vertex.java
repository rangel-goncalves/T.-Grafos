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

    public Vertex(Point point, int ordem) {
        this.point = point;
        this.ordem = ordem;
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
    
    /**
     * 
     * @param data
     * @return o custo de uma determinada aresta se ela não existir retorna 0 
     */
    public Double getCustExpecificEdge(int data){
        Double cost = 0.00;
        
        for (Edge exitEdge : exitEdges) {
            /*if(exitEdge.getEnd()== data){
                cost = (Double)exitEdge.getCost();
                return cost;
            }*/
        }
        return cost;
    }
    
}
