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
        if((start != null) && (end != null)){
            Edge<TYPE> edge = new Edge<TYPE>(cost,start,end);
            end.addInputEdges(edge);
            start.addExitEdges(edge);
            edges.add(edge);
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
    
    /**
     * use para ler grafos não orientados
     */
    public void readFromTxtUnoriented(){
        ArrayList<String[]> reader  = new ReadDoc().FileUrl1();
        for (String[] string : reader) {
            this.addVertex((TYPE)string[0]);
            this.addVertex((TYPE)string[1]);
            this.addEdge(Double.parseDouble(string[2]),(TYPE)string[0],(TYPE)string[1]);
            this.addEdge(Double.parseDouble(string[2]),(TYPE)string[1],(TYPE)string[0]);
        }
    }
    /**
     * use para ler grafos orientados
     */
    public void readFromTxtOriented(){
        ArrayList<String[]> reader  = new ReadDoc().FileUrl1();
        for (String[] string : reader) {
            this.addVertex((TYPE)string[0]);
            this.addVertex((TYPE)string[1]);
            this.addEdge(Double.parseDouble(string[2]),(TYPE)string[0],(TYPE)string[1]);
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
            System.out.print(vertece.getData()+",");
        }
        System.out.println("");
    }
    
    public void listEdges(){
        System.out.println(edges.size());
        for (Edge<TYPE> edge : edges) {
            System.out.println( edge.getStart().getData() +"-->" + edge.getEnd().getData() + " cost:"+edge.getCost() );
        }
    }
    
    public void buscaEmLargura(){
        ArrayList<Vertex<TYPE>> marcados = new ArrayList<Vertex<TYPE>>();
        ArrayList<Vertex<TYPE>> fila = new ArrayList<Vertex<TYPE>>();
        Vertex<TYPE> atual = this.verteces.get(0);
        marcados.add(atual);
        System.out.println(atual.getData());
        fila.add(atual);
        while(fila.size() > 0){
            Vertex<TYPE> visitado = fila.get(0);
            for(int i=0; i < visitado.getExitEdges().size(); i++){
                Vertex<TYPE> proximo = visitado.getExitEdges().get(i).getEnd();
                if (!marcados.contains(proximo)){ //se o vértice ainda não foi marcado
                    marcados.add(proximo);
                    System.out.println(proximo.getData());
                    fila.add(proximo);
                }
            }
            fila.remove(0);
        }
    }
}
