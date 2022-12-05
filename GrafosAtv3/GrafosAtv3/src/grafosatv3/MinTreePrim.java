package grafosatv3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author Rangel
 */
public class MinTreePrim {

    private ArrayList<Integer> path;
    private ArrayList<Vertex> verteces;
    private ArrayList<Edge> edges;
    private ArrayList<Polygon> polygons;

    public MinTreePrim() {
        this.polygons = new ArrayList<>();
        this.verteces = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.path = new ArrayList<>();
    }

    /**
     * Adiciona um novo vertice ao grafo ao mesmo tempo que calcula o caminho ate um novo destino
     * @param pNew
     * @param newGoal 
     */
    public void addNewStart(Point pNew, int newGoal) {
        ArrayList<Edge> newEdges = new ArrayList<>();
        Vertex v = new Vertex(pNew, this.verteces.size());
        this.verteces.add(v);
        Double cost =0.0;
        for (Vertex vertex : verteces) {
            
            Double m = 0.0;
            Boolean dentro = false;
            
            if ((pNew.x - vertex.getPoint().x)==0) {
                continue;
            }

            m = ( vertex.getPoint().y- pNew.y ) / (vertex.getPoint().x-pNew.x);
            //verifica se o valor de x do ponto de destino é maior ou menor do que o do ponto de origem 
            // para limitar o segmento de reta
            if ( pNew.x >= vertex.getPoint().x ) {
 
                // se for menor o valor de X decresse ate atingir o valor de x
                for (Double X = pNew.x+0.2;
                        X > vertex.getPoint().x; X -= 0.1) {
                    // equação da reta
                    Double Y = (m * (X - pNew.x)) + pNew.y;
                    Point p = new Point(X, Y);
                    // testa se o algum dos pontos pertencentes a reta passa por dentro de algum dos poligonos
                    for (int j = 0; j < this.polygons.size(); j++) {
                        if (Position_Point_WRT_Polygon.isInside(this.polygons.get(j).getPoints(),
                                this.polygons.get(j).getPoints().length,
                                p)) {
                            if(vertex.getOrdem() == 0){
                            System.out.println(X+","+Y);
                            }
                            dentro = true;
                        }
                    }
                }
            } else {

                // se for maior o valor de X acresse ate atingir o valor de x
                for (Double X = pNew.x+0.2;
                        X < vertex.getPoint().x; X += 0.1) {
                    // equação da reta
                    Double Y = (m * (X - pNew.x)) + pNew.y;
                    Point p = new Point(X, Y);
                    // testa se o algum dos pontos pertencentes a reta passa por dentro de algum dos poligonos
                    for (int j = 0; j < this.polygons.size(); j++) {
                        if (Position_Point_WRT_Polygon.isInside(this.polygons.get(j).getPoints(),
                                this.polygons.get(j).getPoints().length,
                                p)) {
                            dentro = true;
                        }
                    }
                }
            }
            
            if(!dentro){
                    // calculo do custo da aresta (módulo do vetor entre os dois pontos)
                    cost = Math.sqrt(Math.pow((pNew.x-vertex.getPoint().x), 2)
                                 +Math.pow((pNew.y-vertex.getPoint().y), 2));
                    Edge e =  new Edge(cost, v, vertex);
                    newEdges.add(e);
                }
        }
        if(newEdges.isEmpty()){
            return;
        }
        Collections.sort(newEdges, Comparator.comparing(Edge::getCost));
        this.addEdge(cost, v.getOrdem(), newEdges.get(0).getEnd().getOrdem());
        this.addEdge(cost, newEdges.get(0).getEnd().getOrdem(),v.getOrdem());
        System.out.println("Novo Ponto adicionado!");
        //newEdges.forEach(x -> System.out.println(x));
        this.computarCaminho(v.getOrdem(), newGoal);
    }

    public boolean addVertex(Vertex v) {
        Double[] coordV = {v.getPoint().x, v.getPoint().y};
        if (this.getVertex(coordV) == null) {
            this.verteces.add(v);
            return true;
        }
        return false;
    }

    /**
     * Cria as Arestas para a Arvore mínima gerada pelo Algoritimo de Prim e
     * também cria a AdjList de cada vertice que forma a arvore.
     *
     * @param cost
     * @param start
     * @param end
     */
    public void addEdge(Double cost, int start, int end) {
        Vertex endData = this.getVertex(end);
        Vertex startData = this.getVertex(start);
        if ((startData != null) && (endData != null)) {
            Edge edge = new Edge(cost, startData, endData);
            endData.addInputEdges(edge);
            startData.addExitEdges(edge);
            startData.getAdjList().add(endData);
            //endData.getAdjList().add(startData);
            edges.add(edge);
        }
    }

    /**
     * Busca e retorna Vertex espeficado através de suas coordenadas
     *
     * @param data
     * @return
     */
    public Vertex getVertex(Double[] data) {
        Vertex finder = null;
        for (int i = 0; i < this.verteces.size(); i++) {
            if (Objects.equals(this.verteces.get(i).getPoint().x, data[0]) && Objects.equals(this.verteces.get(i).getPoint().y, data[1])) {
                finder = this.verteces.get(i);
                break;
            }
        }
        return finder;
    }

    /**
     * Busca e retorna um Vertex através de seu identificador(Ordem())
     *
     * @param data
     * @return
     */
    public Vertex getVertex(int data) {
        Vertex finder = null;
        for (int i = 0; i < this.verteces.size(); i++) {
            if (Objects.equals(this.verteces.get(i).getOrdem(), data)) {
                finder = this.verteces.get(i);
                break;
            }
        }
        return finder;
    }

    public String listEdges() {
        String a = "";
        for (Edge edge : edges) {
            System.out.println(edge.getStart().getOrdem() + "-->"
                    + edge.getEnd().getOrdem() + "\n");
        }
        return a;
    }
    public void listVerteces(){
        for (Vertex vertece : verteces) {
            System.out.print("("+vertece.getOrdem()+")");
        }
        System.out.println("");
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(ArrayList<Polygon> polygons) {
        this.polygons = polygons;
    }

    /**
     * Função de Busca em Largura
     */
    public void buscaEmLargura() {
        ArrayList<Vertex> marcados = new ArrayList<>();
        ArrayList<Vertex> fila = new ArrayList<>();
        Vertex atual = this.getVertex(0);
        marcados.add(atual);
        System.out.println(atual.getOrdem() + "=(" + atual.getPoint().x + "," + atual.getPoint().y + ")");
        fila.add(atual);
        while (!fila.isEmpty()) {
            Vertex visitado = fila.get(0);
            for (int i = 0; i < visitado.getExitEdges().size(); i++) {
                Vertex proximo = visitado.getExitEdges().get(i).getEnd();
                if (!marcados.contains(proximo)) { //se o vértice ainda não foi marcado
                    marcados.add(proximo);
                    System.out.println(proximo.getOrdem() + "=(" + proximo.getPoint().x + "," + proximo.getPoint().y + ")");
                    fila.add(proximo);
                }
            }
            fila.remove(0);
        }
    }

    /**
     * Função de Busca em Profundidade
     *
     * @param source
     */
    public void computarCaminho(int source, int dest) {
        this.path.clear();
        this.verteces.forEach(Vertex::resetVisited);
        System.out.println("Computado caminho de "+source+" ate "+dest);
        //this.getVertex(source).getAdjList().forEach(System.out::println);
        if (this.dfs(source, dest)) {
            this.path.add(source);
            for (int i = this.path.size() - 1; i > 0; i--) {
                System.out.print(this.path.get(i) + "->");
            }
            System.out.println(dest);
        } else {
            System.out.println("Não existe caminho");
        }

    }

    public Boolean dfs(int source, int dest) {
        this.getVertex(source).setVisited(true);
        //System.out.println(source);
        for (Vertex vertex : this.getVertex(source).getAdjList()) {
            if (!vertex.getVisited()) {
                //dfs(vertex.getOrdem(),dest);
                if (vertex.getOrdem() == dest) {
                    //System.out.println(vertex.getOrdem());
                    this.path.add(vertex.getOrdem());
                    return true;
                }
                if (dfs(vertex.getOrdem(), dest)) {
                    //System.out.println(vertex.getOrdem());
                    this.path.add(vertex.getOrdem());
                    return true;
                }
            }
        }

        return false;
    }
}
