import java.util.*;
import java.io.*;
import java.nio.file.*;
import org.jsoup.*;
public class CLIQuerySearch{
    private static final String PAGES_DIR = "/Users/damu/Desktop/collegedocx/projects/shadowseek/pages";
    public static void main(String ... args){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter search Query: ");
        String keyword_query = s.nextLine().trim().toLowerCase();
        if(keyword_query.isEmpty()){
            System.out.println("No query provided. Exiting.");
            return;
        }
        File dir = new File(PAGES_DIR);
        if(!dir.exists()){
            System.out.println("Pages directory does not exist. Exiting.");
            return;
        }
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if(files == null || files.length == 0){
            System.out.println("No pages found in the directory. Exiting.");
            return;
        }
       int resultCounter = 0;
        for(File file: files){
            try{
                String rawHTMLContent = Files.readString(file.toPath());
                String content = Jsoup.parse(rawHTMLContent).text().toLowerCase();
                if(content.contains(keyword_query)){
                    resultCounter++;
                    System.out.println("\nMatch #" +resultCounter);
                    System.out.println("File: "+file.getName());
                    int index = content.indexOf(keyword_query) ;
                    int snippetStart = Math.max(index -50 ,0);
                    int snippetEnd = Math.min(index + 50,content.length());
                    String snippet = content.substring(snippetStart,snippetEnd).replaceAll("\\s+"," ");
                    System.out.println("Snippet:..."+snippet+"...");
                }
            }catch(Exception e){
                System.out.println("Error Reading File: "+file.getName());
            }
        }if(resultCounter == 0){
            System.out.println("\n No results found for: \""+keyword_query);
        }else{
            System.out.println("\n"+resultCounter+"results Found.");
        }
    }
}