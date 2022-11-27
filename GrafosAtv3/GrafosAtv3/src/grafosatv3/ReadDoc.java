package grafosatv3;

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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rangel
 */
public class ReadDoc {
    
    private File selectedFile1;
   // private JSONArray JSONArray1;

    public ReadDoc() {
        this.selectedFile1 = null;
        //this.JSONArray1 =  null;
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
                FileReader reader = new FileReader(selectedFile);
                BufferedReader buffer = new BufferedReader(reader);
                //String line = buffer.readLine(); // Tiro a primeira linha que contem o numero de verteces e arestas
                                                //isso pode ser facilmente calculado na classe Graph
                String line ="";
                line = buffer.readLine(); 
                String aux = "";
                while(line!=null){
                    //System.out.println(line);
                    //StringTokenizer st = new StringTokenizer(line," ");
                    line = line.replaceAll("," , "");
                    System.out.println(line);
                    Pattern p = Pattern.compile("\\d+.\\d+");
                    Matcher m = p.matcher(line);
                    while(m.find()) {
                    //System.out.println(m.group());
                    aux += m.group()+" ";
                }
                    //Point ans = new Point();
                    //int i = 0;
                    /*
                    while (st.hasMoreTokens()) {
                        ans[i] = st.nextToken();
                        i++;
                    }
                    */
                    //if(st.hasMoreTokens()) {
                       //System.out.println(st.nextToken());
                       //System.out.println(st.nextToken());
                       //ans.x = Double.parseDouble(st.nextToken()) ;
                       //ans.y = Double.parseDouble(st.nextToken()) ;
                       //i++;
                    //}else{
                    //}
                    
                    //result.add(ans);
                    line = buffer.readLine();
                }
                StringTokenizer st = new StringTokenizer(aux," ");
                while (st.hasMoreTokens()) {
                        //ans[i] = st.nextToken();
                        //i++;
                        System.out.println(st.nextToken());
                    }
                System.out.println(aux);
                /*
                Pattern p = Pattern.compile("\\d+.\\d+");
                Matcher m = p.matcher(aux);
                while(m.find()) {
                    System.out.println(m.group());
                }
                */
                reader.close();
                return result;
            } catch (IOException ex) {
                System.out.println("ERRO: " + ex.getMessage());
            }
	}
        return result;
    }
    /*
    public JSONArray jsomFileURL() throws ParseException{
        JSONArray JSONArray ;
        JSONParser parser = new JSONParser();
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            try {
                JSONArray  = (JSONArray) parser.parse(new FileReader(selectedFile));
                return JSONArray; 
            } catch (IOException ex) {
                System.out.println("ERRO: " + ex.getMessage());
            }
	}
        return null;
    }
    /**
    * Use se for testar varios raios de distancia maxima com um mesmo JSON
     * @return 
     * @throws org.json.simple.parser.ParseException
    
    */
    /*
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
    */
}
