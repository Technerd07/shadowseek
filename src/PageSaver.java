import java.util.*;
import java.io.*;
import java.nio.file.*;

public class PageSaver {
    public static final String OUTPUT_DIR = "./pages/";

    public static void saveLinks(String url,List<String> links){
        try{
       Files.createDirectories(Paths.get(OUTPUT_DIR));
        String file_name = sanitizedFileName(url) + "_links.txt";
        File outputFile = new File(OUTPUT_DIR + file_name);
        System.out.println("📂 Saving links to: " + OUTPUT_DIR + outputFile);
        //write the result links to the file
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))){
            for(String link : links){
                writer.write(link + "\n");
            }
        }
        }catch(Exception e){
            System.out.println("❌ Failed to save links for URL: " + e.getMessage());
        }
    }

    private static String sanitizedFileName(String url) {
        return url.replaceAll("[^a-zA-Z0-9]", "_")
                  .replaceAll("_+", "_") // Replace multiple underscores with a single one
                  .replaceAll("^_|_$", ""); // Remove leading and trailing underscores
    }
}
