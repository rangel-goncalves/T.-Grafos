package grafosatv3;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Rangel
 */
public class MinTreePrim{
    
    private ArrayList<Vertex> verteces;
    private ArrayList<Edge> edges;

    public MinTreePrim() {
        this.verteces = new ArrayList<>();
        this.edges = new ArrayList<>();
    }
    
    public boolean addVertex(Vertex v){
        Double [] coordV = {v.getPoint().x, v.getPoint().y};
        if(this.getVertex(coordV)== null){
            this.verteces.add(v);
            return true;
        }
        return false;
    }
    /**
     * Cria as Arestas para a Arvore mínima gerada pelo Algoritimo de Prim e também
     * cria a AdjList de cada vertice que forma a arvore.
     * @param cost
     * @param start
     * @param end 
     */
    public void addEdge(Double cost, int start, int end){
        Vertex endData = this.getVertex(end);
        Vertex startData = this.getVertex(start);
        if((startData != null) && (endData != null)){
            Edge edge = new Edge(cost,startData,endData);
            endData.addInputEdges(edge);
            startData.addExitEdges(edge);
            startData.getAdjList().add(endData);
            edges.add(edge);
        }
    }
    
    /**
     * Busca e retorna Vertex espeficado através de suas coordenadas
     * @param data
     * @return 
     */
    public Vertex getVertex(Double[] data){
        Vertex finder = null;
        for (int i = 0; i < this.verteces.size(); i++) {
            if(Objects.equals(this.verteces.get(i).getPoint().x, data[0]) && Objects.equals(this.verteces.get(i).getPoint().y, data[1])){
                finder = this.verteces.get(i);
                break;
            } 
        }
        return finder;
    }
    
    /**
     * Busca e retorna um Vertex através de seu identificador(Ordem())
     * @param data
     * @return 
     */
    public Vertex getVertex(int data){
        Vertex finder = null;
        for (int i = 0; i < this.verteces.size(); i++) {
            if(Objects.equals(this.verteces.get(i).getOrdem() , data)){
                finder = this.verteces.get(i);
                break;
            } 
        }
        return finder;
    }
    
     public String listEdges(){
        String a = "";
        for (Edge edge : edges) {
            System.out.println(edge.getStart().getOrdem()+"-->"
                                + edge.getEnd().getOrdem()+"\n");
        }
        return a;
    }

     /**
      * Função de Busca em Largura
      */
    public void buscaEmLargura(){
        ArrayList<Vertex> marcados = new ArrayList<>();
        ArrayList<Vertex> fila = new ArrayList<>();
        Vertex atual = this.getVertex(0);
        marcados.add(atual);
        System.out.println(atual.getOrdem()+"=("+atual.getPoint().x + "," + atual.getPoint().y+")");
        fila.add(atual);
        while(!fila.isEmpty()){
            Vertex visitado = fila.get(0);
            for(int i=0; i < visitado.getExitEdges().size(); i++){
                Vertex proximo = visitado.getExitEdges().get(i).getEnd();
                if (!marcados.contains(proximo)){ //se o vértice ainda não foi marcado
                    marcados.add(proximo);
                    System.out.println(proximo.getOrdem()+"=("+proximo.getPoint().x + "," + proximo.getPoint().y+")");
                    fila.add(proximo);
                }
            }
            fila.remove(0);
        }
    }
    
    /**
     * Função de Busca em Profundidade
     * @param source 
     */
    public void dfs(int source){
        this.getVertex(source).setVisited(true);
        System.out.println(source);
        for (Vertex vertex : this.getVertex(source).getAdjList()) {
            if (!vertex.getVisited()) {
                dfs(vertex.getOrdem());
            }
        }
    }       
}
