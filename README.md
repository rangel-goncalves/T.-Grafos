Índice
=================
<!--ts-->
   * [Atividade 1](#Atividade-1---Algoritimos-de-caminho-mínimo)
   * [Atividade 2](#Atividade-2---Algoritimos-de-caminho-mínimo)
   * [Atividade 3](#Atividade-3---Algoritimos-de-Árvore-Mínima)
<!--te-->
# Atividade 1 - Algoritimos de caminho mínimo
# Cenário 1
  
 ## Leitura do grafo a partir de um arquivo .txt Neste passo teremos um Grafo formado por 2 objetos: Vertex e Edges
  ```java
  public void readFromTxtUnoriented(){
        ArrayList<String[]> reader  = new ReadDoc().FileUrl1();
        for (String[] string : reader) {
            this.addVertex((TYPE)string[0]);
            this.addVertex((TYPE)string[1]);
            this.addEdge(Double.parseDouble(string[2]),(TYPE)string[0],(TYPE)string[1]);
            this.addEdge(Double.parseDouble(string[2]),(TYPE)string[1],(TYPE)string[0]);
        }
    }
   ```
## Utilizando a função Graph.transforIntoMatix() podemos criar uma matriz de adjacencias para facilitar o uso do algoritimo de caminho mínimo escolhido, que neste cado foi o algoritimo de Floyd.
```java
    public Integer[][] transforIntoMatix(){
        this.matrixG = new Integer [this.verteces.size()+1][this.verteces.size()+1];
        for (Vertex<TYPE> vertece : verteces) {
            for (int i = 0; i < this.verteces.size()+1; i++) {
                int aux = Integer.valueOf((String)vertece.getData());
                this.matrixG[aux][i] = vertece.getCustExpecificEdge(i);             
            }
        }
        /*
            Eliminando a linha e a colula [0,0] so pra não printar tudo INF nela
        */
        Integer [][] matrixGg = new Integer [this.verteces.size()][this.verteces.size()];
        for (int i = 0; i < this.verteces.size(); i++) {
            for (int j = 0; j < this.verteces.size(); j++) {
                if(this.matrixG[i+1][j+1] == 0){
                    if(i==j){
                        matrixGg [i][j] = 0;
                    }else{
                        matrixGg [i][j] = INF;
                    }
                }else{
                    matrixGg [i][j]=this.matrixG[i+1][j+1];
                }
                
            }
        }
        return matrixGg;
    }
 ```
 ## Computação de caminho mínimo
 ```java
    public void floyd(Integer graph[][])
    {
        int V = graph.length;
        Integer dist[][] = new Integer[V][V]; // Matriz de solução
        int r [][] = new int[V][V];
        int i, j, k;
        for (i = 0; i < V; i++){
            for (j = 0; j < V; j++){
                dist[i][j] = graph[i][j];
                    if(dist[i][j]==INF){
                        r[i][j]= 0;
                    }else{
                        r[i][j] = j+1;                   
                    }
            }
        }
        for (k = 0; k < V; k++) {
            for (i = 0; i < V; i++) {
                for (j = 0; j < V; j++) {
                    if(i==j){
                        continue;
                    }
                    if (dist[i][k] + dist[k][j]< dist[i][j]){
                        dist[i][j] = dist[i][k] + dist[k][j];
                        r[i][j] = r[i][k];
                    }
                }
            }
        }
        this.floydResult = new MatrixFloyd(r,dist); // Objeto que vai guardar as informações gerada pelo algoritimo
    }
 ```
## Cálculo dos vetores de distância
 ```java
 public String getSumD() {
        String a = "";
        
        for (int i = 0; i < d.length; i++) {
            int result=0;
            for (int j = 0; j < d.length; j++) {
                if(d[i][j]==999999999 && i!=j){
                    result = 999999999;
                    
                    break;
                }
                result +=d[i][j];
                
            }
            this.sumD.add(result); // sera utilizado na escolha do melhor vertice central
            if(result>=999999999){
                a = a+"INF"+"\n";
            }else{
                a = a+result+"\n";
            }
        }
        return a;
    }
 ```
## Determinando a estação central
 ```java
 public int getBestCentralVertex(){
        /*
            encontra o(os) vertices com o menor somatorio dos pesos do caminho
        */
        int min = Collections.min(this.sumD);
        ArrayList<Integer> minimals =  new ArrayList();
        for (int i = 0; i < this.sumD.size()-1; i++) {
            if(this.sumD.get(i).equals(min)){
                minimals.add(i);
            }
        }
        /*
            em caso de empate essa parte desempata usando o requisito de menor peso para o vertece mais distante
        */
        int result = 999999999;
        for (Integer minimal : minimals) {
            Integer []a  = d[minimal];
            System.out.println(minimal+1); // o + 1 é pq o contador começa do 0
            ArrayList<Integer> b =  new ArrayList(Arrays.asList(a));
            if(result>Collections.max(b)){  // se o resultado anterior for maior que o atualmente verificado atualiso o valor
                result = minimal+1;
            }
        }
        System.out.println("melhor: "+ result);
        return result;
    }
 ```
 # Cenário 2
  Neste cenario podemos utilizar tudo que foi utilizado no Cenário 1, a unica modificação que devemos levar em conta será a forma que o grafo sera lido pelo programa
  pois neste cenário teremos um grafo orientado desta forma a função utillizada será a função Graph.readFromTxtOriented()
```java
  public void readFromTxtOriented(){
        ArrayList<String[]> reader  = new ReadDoc().FileUrl1();
        for (String[] string : reader) {
            this.addVertex((TYPE)string[0]);
            this.addVertex((TYPE)string[1]);
            this.addEdge(Double.parseDouble(string[2]),(TYPE)string[0],(TYPE)string[1]);
        }
    }
```
  O restante sera feito de forma semelhante ao Cenário 1
  
  ## Telas
  
  Tela de Inicio           |  Tela de busca            |  Tela de resultado grafo01.txt       |  Tela de resultado grafo02.txt
:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/rangel-goncalves/T.-Grafos/blob/main/GrafosAtv1/src/Images/Tela%20inicial.png)  |  ![](https://github.com/rangel-goncalves/T.-Grafos/blob/main/GrafosAtv1/src/Images/Tela%20de%20busca.png) |  ![Resultado usando o grafo01](https://github.com/rangel-goncalves/T.-Grafos/blob/main/GrafosAtv1/src/Images/Tela%20de%20resultado.png) |  ![Resultado usando o grafo02](https://github.com/rangel-goncalves/T.-Grafos/blob/main/GrafosAtv1/src/Images/Tela%20de%20resultado2.png) 


# Atividade 2 - Algoritimos de caminho mínimo
## Função main
```java
public class GrafosAtv2 {
    /**
     * @param args the command line arguments
     * @throws org.json.simple.parser.ParseException
     */
    public static void main(String[] args) throws ParseException {
        Double maxDistance = 500.00;
        Graph g = new Graph();
        g.readGraphFromJSON(maxDistance);
        System.out.println(g.havePath(1, 2));
        maxDistance = 600.00;
        g.readGraphFromJSON(maxDistance);
        System.out.println(g.havePath(100, 1));
        /*
            //Gerar todas as possibilidades de caminhos  para a distância escolhido.
            maxDistance = (escolha a distância);
            g.rodarTodosOsCaminhos();
        */
    }
}
```
  Você pode alterar o valor da variavel (maxDistance) para testar outros limites de distância, caso queira testar varios percursos ou distâncias basta reperir o trecho de código:
```java
        maxDistance = [DISTÂNCIA DEESEJADA];
        g.readGraphFromJSON(maxDistance);
        System.out.println(g.havePath([PONTO DE PARTIDA], [PONTO DE DESTINO]));
```
###   **Os parametros [PONTO DE PARTIDA], [PONTO DE DESTINO] são os ranks das cidades**
### SE OPTAR POR USAR AS FUNÇÕES DO ESTÃO NO BLOCO COMENTADO O PROGRAMA IRA MOSTRAR O RESULTADO PARA TODOS OS CAMINHOS POSSIVEIS ENTRE AS COMBINAÇÕES ENTRE OS VERTICES E ISSO PODE LEVAR ALGUNS MINUTOS JA QUE SE TRATA DE UM ARRANJO DE 2^1000, POIS TEMOS 1000 VERTECES COMBINADOS 2 A 2.

## Função Usada Para Calcular a Distância Entres As Cooedenadas
```java
    public Double latAndLgnToDistance(double firstLatitude, double firstLongitude,double secondLatitude,double secondLongitude){
        double EARTH_RADIUS_KM = 6378.1;
        double firstLatToRad = Math.toRadians(firstLatitude);
        double secondLatToRad = Math.toRadians(secondLatitude);
        // Diferença das longitudes
        double deltaLongitudeInRad = Math.toRadians(secondLongitude
                                    - firstLongitude);
        Double a = Math.acos(Math.cos(firstLatToRad) * Math.cos(secondLatToRad)
                   * Math.cos(deltaLongitudeInRad) + Math.sin(firstLatToRad)
                   * Math.sin(secondLatToRad))* EARTH_RADIUS_KM;
        return a;
    }
```
## Função Usada Para ler o Arquivo
```java
    private File selectedFile1;
    private JSONArray JSONArray1;

    public ReadDoc() {
        this.selectedFile1 = null;
        this.JSONArray1 =  null;
    }
    
    public JSONArray jsomFileURL1() throws ParseException{
        if(this.JSONArray1 ==  null){
            JSONParser parser = new JSONParser();
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.addChoosableFileFilter(new FileNameExtensionFilter("JSON FILE", "json"));
            jfc.setDialogTitle("Selecione o Arquivo .JSON");
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                try {
                    this.JSONArray1  = (JSONArray) parser.parse(new FileReader(selectedFile));
                    return this.JSONArray1; 
                } catch (IOException ex) {
                    System.out.println("ERRO: " + ex.getMessage());
                }
            } 
        }else if(this.JSONArray1 !=  null){
            return this.JSONArray1; 
        }
        
        return null;
    }
```
## Algoritimo de Caminho Mínimo Floyd

```java
public void floyd(Double graph[][])
    {
        int V = graph.length;
        Double dist[][] = new Double[V][V]; // Matriz de solução
        int r [][] = new int[V][V];
        int i, j, k;
        for (i = 0; i < V; i++){
            for (j = 0; j < V; j++){
                dist[i][j] = graph[i][j];
                    if(dist[i][j]==INF){
                        r[i][j]= 0;
                    }else{
                        r[i][j] = j+1;                   
                    }
            }
        }
        for (k = 0; k < V; k++) {
            for (i = 0; i < V; i++) {
                for (j = 0; j < V; j++) {
                    if(i==j){
                        continue;
                    }
                    if (dist[i][k] + dist[k][j]< dist[i][j]){
                        dist[i][j] = dist[i][k] + dist[k][j];
                        r[i][j] = r[i][k];
                    }
                }
            }
        }
        this.floydResult = new MatrixFloyd(r,dist); // Objeto que vai guardar as informações gerada pelo algoritimo
    }
```
## Função Que Verifica a Existência de Caminho

```java
    /** 
     * @param start
     * @param end
     * @return ans (String com o resultado do percurso)
     */
    public String havePath(int start, int end){
        String ans="";
        int r[][] = this.floydResult.getR();
        Double d [][] = this.floydResult.getD();
        int next = end-1;
        int cur = start-1;
        ans += "Ponto de partida: " + this.getVertex(start).getCity()+"\n";
        while(true){
            if(r[cur][next]!=0){
                ans += "distancia percorrida da ultima parada ate a atual: "+d[cur][r[cur][next]-1];
                cur = r[cur][next]-1;
                if(cur==next){
                    ans +=" Parada atual(DESTINO FINAL): "+this.getVertex(cur+1).getCity()+"\n";
                    ans += "Distancia total percorrida: " + d[start-1][end-1]+"\n";
                    return ans; 
                }
                ans += " distancia restante: "+d[cur][next];
                ans += " Parada atual: "+this.getVertex(cur+1).getCity()+"\n";
            }else{
                ans += "Não existe caminho para "+this.getVertex(end).getCity()+"\n";
                return ans;
            }
        }
    }
```

## Telas
  
  Tela de Busca            |  Tela de Resultado        | 
:-------------------------:|:-------------------------:|
![](https://github.com/rangel-goncalves/T.-Grafos/blob/main/GrafosAtv2/src/images/TelaBusca.jpg)  |  ![](https://github.com/rangel-goncalves/T.-Grafos/blob/main/GrafosAtv2/src/images/TelaOutput.jpg) 

# Atividade 3 - Algoritimos de Árvore Mínima
##Função de leitura de Arquivo txt:

 ```java
public void readFromTxtUnoriented(){
        ArrayList<Point> reader  = new ReadDoc().FileUrl1();
        this.start = reader.remove(0);
        Vertex v = new Vertex(this.start, this.verteces.size());
        this.addVertex(v);
        this.end = reader.remove(0);
        Point init = reader.remove(0);
        int n = init.x.intValue();
        int k = reader.remove(0).x.intValue();
        
        for (int i = 0; i < n; i++) {
            Polygon lixo =  new Polygon();
            this.polygons.add(lixo);
        }
```
## Funções para montar as arvores mínimas:
### Prim
```java
public void primMST(Double graph[][]){
        
        int parent[] = new int[this.verteces.size()];

        Double key[] = new Double[this.verteces.size()];

        Boolean mstSet[] = new Boolean[this.verteces.size()];

        for (int i = 0; i < this.verteces.size(); i++) {
            key[i] = Double.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0.0;
        parent[0] = -1;
 
        for (int count = 0; count < this.verteces.size() - 1; count++) {
            int u = minKey(key, mstSet);
 
            mstSet[u] = true;

            for (int v = 0; v < this.verteces.size(); v++){

                if (graph[u][v] != 0 && mstSet[v] == false
                    && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }
        this.printMST(parent, graph);
    }
```
### Kruskal
```java
public void KruskalMST()
    {
        kruskalEdge result[] = new kruskalEdge[this.verteces.size()];
 
        int e = 0;
 
        int i = 0;
        for (i = 0; i < this.verteces.size(); ++i){
            result[i] = new kruskalEdge();
        }

        Arrays.sort(this.kruskalEdge);
 
        subSet subsets[] = new subSet[this.verteces.size()];
        for (i = 0; i < this.verteces.size(); ++i)
            subsets[i] = new subSet();
 
        for (int v = 0; v < this.verteces.size(); ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
 
        i = 0;

        while (e < this.verteces.size() - 1) {
            
            kruskalEdge next_edge = this.kruskalEdge[i++];
 
            int x = find(subsets, next_edge.getSrc());
            int y = find(subsets, next_edge.getDest());
 
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
        }
        this.PrintKruskalMST(result, i, e);
    }
```
## Adicionar Determinados pontos em outras posições e determinar o ponto mais próximo a ele:
### O novo ponto pode ser definido na Classe principal [GrafosAtv3](https://github.com/rangel-goncalves/T.-Grafos/blob/main/GrafosAtv3/GrafosAtv3/src/grafosatv3/GrafosAtv3.java)
```java
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
```
## Telas
  
  Tela de Prim             |  Tela de Kruskal e resultado da computação co caminho qSatrt->qgoal e resultado do caminho de um novo ponto qualquer ate o qgoal         | Arvore Mínima             
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/rangel-goncalves/T.-Grafos/blob/main/GrafosAtv3/GrafosAtv3/src/Images/atv1t1.jpg)  |  ![](https://github.com/rangel-goncalves/T.-Grafos/blob/main/GrafosAtv3/GrafosAtv3/src/Images/atv1t2.jpg) |  ![](https://github.com/rangel-goncalves/T.-Grafos/blob/main/GrafosAtv3/GrafosAtv3/src/Images/atv3Tree.jpg) 


