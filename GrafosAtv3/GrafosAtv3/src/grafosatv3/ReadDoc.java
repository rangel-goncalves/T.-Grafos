package grafosatv3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rangel
 */
public class ReadDoc {
    
    public ReadDoc() {
    }
    
    /**
    Ao invocar este metodo sera aberta uma janela para seleção do aqruivo desejado; 
     * @return 
    **/
   public ArrayList<Point> FileUrl1(){
        
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.addChoosableFileFilter(new FileNameExtensionFilter("TXT FILE", "txt"));
        jfc.setDialogTitle("Selecione o Arquivo .TXT");
        int returnValue = jfc.showOpenDialog(null);
        ArrayList<Point> result = new ArrayList<>();

	if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            try {
                try (FileReader reader = new FileReader(selectedFile)) {
                    BufferedReader buffer = new BufferedReader(reader);
                    String line ="";
                    line = buffer.readLine();
                    String aux = "";
                    while(line!=null){
                        line = line.replaceAll("," , "");
                        if(line.length()<4){
                            line += " 99999.99";
                            
                        }
                        //System.out.println(line);
                        Pattern p = Pattern.compile("\\d+.\\d+");
                        Matcher m = p.matcher(line);
                        while(m.find()) {
                            aux += m.group()+" ";
                        }
                        line = buffer.readLine();
                    }
                    StringTokenizer st = new StringTokenizer(aux, " ");
                    
                    while (st.hasMoreTokens()) {
                        String x = st.nextToken();
                        String y = st.nextToken();
                        Point ans = new Point(Double.parseDouble(x),Double.parseDouble(y));
                        result.add(ans);
                    }
                }
                return result;
                
            } catch (IOException ex) {
                System.out.println("ERRO: " + ex.getMessage());
            }
	}
        
        return result;
    }
    
}
