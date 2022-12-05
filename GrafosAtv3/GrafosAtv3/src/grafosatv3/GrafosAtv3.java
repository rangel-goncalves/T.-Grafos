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
        ////////////////////Testes////////////////////////////
        
        //essa parte calcula o caminho pedido na atividade do ponto (1,10) ate o (10,1)
        g.getMinTreePrim().computarCaminho(0, 14);
        
        // pNew recebe a nova coordenada onde o robo ta
        Point pNew = new Point(0.0, 0.0);
        g.getMinTreePrim().addNewStart(pNew,14);
        // Para adicionar um novo ponto e recalcular o caminho ate um novo destino é so repetir so 2 ultimos comandos
        
        /////////////////////////////////////////////////////
        
    }
    
}
