package grafosatv1;

import java.util.ArrayList;

/**
 *
 * @author Rangel
 * @param <TYPE>
 */
public class Graph<TYPE> {
    private ArrayList<Vertex<TYPE>> verteces;
    private ArrayList<Edge<TYPE>> edges;
    
    public Graph() {
        this.verteces = new ArrayList<Vertex<TYPE>>();
        this.edges =  new ArrayList<Edge<TYPE>>();
    }
  
    public void addVertex(TYPE data){
        if(this.getVertex(data)== null){
            Vertex<TYPE> newVertex = new Vertex(data);
            this.verteces.add(newVertex);
        }
    }
    
    public void addEdge(Double cost, TYPE startData, TYPE endData){
        Vertex<TYPE> start = this.getVertex(startData);
        Vertex<TYPE> end = this.getVertex(endData);
        if((start!= null)&&(end != null)){
            Edge<TYPE> edge = new Edge<TYPE>(cost,start,end);
            end.addInputEdges(edge);
            start.addExitEdges(edge);
        }
    }
    
    public Vertex<TYPE> getVertex(TYPE data){
        Vertex<TYPE> finder = null;
        for (int i = 0; i < this.verteces.size(); i++) {
            if(this.verteces.get(i).getData().equals(data)){
                finder = this.verteces.get(i);
                break;
            } 
        }
        return finder;
    }
    
    public void readFromTxt(){
        ArrayList<String[]> reader  = new ReadDoc().FileUrl1();
        for (String[] string : reader) {
            this.addVertex((TYPE)string[0]);
            this.addEdge(Double.parseDouble(string[2]), (TYPE)string[0], (TYPE)string[1]);
        }
    }

    public ArrayList<Vertex<TYPE>> getVerteces() {
        return verteces;
    }

    public ArrayList<Edge<TYPE>> getEdges() {
        return edges;
    }
    
    public void listVerteces(){
        for (Vertex<TYPE> vertece : verteces) {
            System.out.println(vertece.getData());
        }
    }
}
