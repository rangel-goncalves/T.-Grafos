/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package grafosatv3;



/**
 * falta muito, ainda falta muito mas falta menos
 * @author Rangel
 */
public class GrafosAtv3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Graph g = new Graph();
        
        g.readFromTxtUnoriented();

        g.createEdges();
        System.out.println("Busca\n\n");
        g.getMinTreePrim().dfs(0);

    }
    
}
