package grafosatv1;

import java.util.ArrayList;

/**
 *
 * @author Rangel
 * @param <TYPE>
 */
public class Vertex <TYPE> {
    private TYPE Data;
    private ArrayList<Edge<TYPE>> inputEdges;
    private ArrayList<Edge<TYPE>> exitEdges;

    public Vertex(TYPE Data) {
        this.Data = Data;
        this.inputEdges = new ArrayList<Edge<TYPE>>();
        this.exitEdges = new ArrayList<Edge<TYPE>>();
    }

    public TYPE getData() {
        return Data;
    }

    public void setData(TYPE Data) {
        this.Data = Data;
    }

    public ArrayList<Edge<TYPE>> getInputEdges() {
        return inputEdges;
    }

    public void addInputEdges(Edge<TYPE> inputEdge) {
        this.inputEdges.add(inputEdge);
    }

    public ArrayList<Edge<TYPE>> getExitEdges() {
        return exitEdges;
    }

    public void addExitEdges(Edge<TYPE> exitEdges) {
        this.exitEdges.add(exitEdges);
    }
    /**
     * 
     * @param data
     * @return o custo de uma determinada aresta se ela não existir retorna 0 
     */
    public Integer getCustExpecificEdge(Integer data){
        int cust = 0;
        
        for (Edge<TYPE> exitEdge : exitEdges) {
            if(exitEdge.getEnd().getData().equals(data.toString())){
                cust = (int)exitEdge.getCost();
            }
        }
        return cust;
    }
    
}
