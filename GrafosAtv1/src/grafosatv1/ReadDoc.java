package grafosatv1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Rangel
 */
public class ReadDoc {

    public ReadDoc() {
    }
    
    /**
     * (ESTAR COM ALGUM BUG QUE EU NÃO LEMBRO) PARA LER O ARQUIVO USE A FUNÇÃO: FileUrl1()
     * Ao invocar este metodo sera aberta uma janela para seleção do aqruivo desejado; 
    **/
    public ArrayList<Character> FileUrl(){
        
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
         ArrayList<Character> result = null;
	int returnValue = jfc.showOpenDialog(null);

	if (returnValue == JFileChooser.APPROVE_OPTION) {
            result = new ArrayList<>();
            File selectedFile = jfc.getSelectedFile();
            Path caminho = Paths.get(selectedFile.getAbsolutePath());
            try {
                byte[] txt = Files.readAllBytes(caminho);
                String texto =  new String(txt);
                char [] charac = texto.toCharArray(); // transformando em char eu posso ler 1 por 1
                boolean flag = false;
                for (int i = 0; i < charac.length; i++) {
                    if(charac[i] == '\n'){
                        flag = true;
                        continue;
                    }
                    if(flag && (charac[i]!=' ')){
                        result.add(charac[i]);
                    }
                }
                return result;
            } catch (IOException ex) {
            Logger.getLogger(ReadDoc.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
        return result;
    }
    /**
    Ao invocar este metodo sera aberta uma janela para seleção do aqruivo desejado; 
    **/
    public ArrayList<String[]> FileUrl1(){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<String[]> result = new ArrayList<>();
	int returnValue = jfc.showOpenDialog(null);

	if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            try {
                FileReader reader = new FileReader(selectedFile);
                BufferedReader buffer = new BufferedReader(reader);
                String line = buffer.readLine(); // Tiro a primeira linha que contem o numero de verteces e arestas
                                                //isso pode ser facilmente calculado na classe Graph
                line = buffer.readLine(); 
                while(line!=null){
                    
                    StringTokenizer st = new StringTokenizer(line);
                    String[] ans = new String[3];
                    int i = 0;
                    while (st.hasMoreTokens()) {
                        ans[i] = st.nextToken();
                        i++;
                    }
                    result.add(ans);
                    line = buffer.readLine();
                }
                reader.close();
                return result;
            } catch (IOException ex) {
                System.out.println("ERRO: " + ex.getMessage());
            }
	}
        return result;
    }
}
