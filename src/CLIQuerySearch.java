import java.util.*;
import java.io.*;
import java.nio.file.*;
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
        boolean found = false;
        for(File file : files){
            try{
                String content = new String(Files.readAllBytes(file.toPath()), "UTF-8").toLowerCase();
                if(content.contains(keyword_query)){
                    System.out.println("Found in file: " +file.getName());
                    found = true;
                }
            }catch(Exception e){
                System.out.println("Error reading file: " + file.getName());
            }
        }if(!found){
            System.out.println("No matches found for the query: "+keyword_query);
        }
    }
}