# T.-Grafos
# Atividade 1 - Algoritimos de caminho mínimo
  Cenário 1
  Leitura do grafo a partir de um arquivo .txt Neste passo teremos um Grafo formado por 2 objetos: Vertex e Edges
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
   Utilizando a função Graph.transforIntoMatix() podemos criar uma matriz de adjacencias para facilitar o uso do algoritimo de caminho mínimo escolhido, que neste cado foi o algoritimo de Floyd.
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
 Computação de caminho mínimo
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
 Cálculo dos vetores de distância
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
 Determinando a estação central
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
  Cenário 2
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
  O restate sera feito de forma semelhante ao Cenário 1
  #Telas
  Tela de Inicio            |  Tela de busca             |  Tela de resultado
:-------------------------:|:-------------------------::-------------------------:
![](https://github.com/rangel-goncalves/T.-Grafos/blob/main/GrafosAtv1/src/Images/Tela%20de%20busca.png)  |  ![]()![]()  |  ![]()
