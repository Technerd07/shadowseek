import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jsoup.*;
public class CLIQuerySearch{
    private static final String PAGES_DIR = "/Users/damu/Desktop/collegedocx/projects/shadowseek/pages";
    private static final String CLEANED_DIR = "/Users/damu/Desktop/collegedocx/projects/shadowseek/pages/cleaned";
    private static final String SEARCH_HISTORY_FILE = "/Users/damu/Desktop/collegedocx/projects/shadowseek/logs/search_history.txt";
    public static void main(String ... args){
       //clean all the content removing html tags.
       cleanAllContentFiles();
       //get user input for search query.
       Scanner s = new Scanner(System.in);
       System.out.println("Enter Search Query: ");
       String keyword_query = s.nextLine().trim().toLowerCase();
       if(keyword_query.isEmpty()){
           System.out.println("No Query Provided Exiting...");
           return;
        }
        /*search for the keyword in cleaned files. and check 
         * wether the cleaned dir exists or not.
        */
       File cleanedDir = new File(CLEANED_DIR);
       if(!cleanedDir.exists()){
           System.out.println("Cleaned Directory does'nt Exist. Exiting...");
           return;
       }
       File [] files = cleanedDir.listFiles((d,name)->name.endsWith("_cleaned.txt"));
       if(files == null || files.length == 0){
           System.out.println("No Cleaned Files Found In the Directory. Exiting...");
           return;
       }
       /*Search history logging 
        * 
        */
        try{
            File searchFile = new File(SEARCH_HISTORY_FILE);
            if(!searchFile.exists()){
                searchFile.getParentFile().mkdirs();
                searchFile.createNewFile();
            }
            try(FileWriter fw = new FileWriter(searchFile,true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)){
                    String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    out.println(timeStamp + " - Search Query: \"" + keyword_query + "\"");
                }
        }catch(IOException e){
            System.out.println("Error Writing to Search History File:");
        }
       /*
        * if the files are found then search for the keyword in each file.
        */
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
            System.out.println("\nNo results found for: \""+keyword_query +"\"");
        }else{
            System.out.println("\n"+resultCounter+"results Found.");
        }
    }
    private static void cleanAllContentFiles(){
        File dir = new File(PAGES_DIR);
        File cleanedDir = new File(CLEANED_DIR);
        if(!cleanedDir.exists()){
            cleanedDir.mkdirs();
        }
        File [] files = dir.listFiles((d,name )-> name.endsWith("_content.txt"));
        if(files == null || files.length == 0){
            System.out.println("No *_content.txt files found to clean");
            return;
        }
        for(File file : files){
            try{
                String rawHTMLContent = Files.readString(file.toPath());
                int htmlStart = rawHTMLContent.indexOf("<html");
                if(htmlStart != -1){
                   rawHTMLContent = rawHTMLContent.substring(htmlStart);
                }
                String cleanedText = Jsoup.parse(rawHTMLContent).text();
                String cleanedFileName = file.getName().replaceAll("_content.txt","_cleaned.txt");
                File cleanedFile = new File(cleanedDir,cleanedFileName);
                Files.writeString(cleanedFile.toPath(),cleanedText);
            }catch(Exception e){
                System.out.println("Error Cleaning File"+e.getMessage());
            }
        }
    }
}