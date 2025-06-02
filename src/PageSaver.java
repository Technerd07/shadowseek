import java.util.*;
import java.io.*;
import java.nio.file.*;

public class PageSaver {
    public static final String OUTPUT_DIR = "./pages/";

    /**
     * Saves a list of links to a text file.
     *
     * @param url   The URL being processed (used for naming the file).
     * @param links The list of extracted links to save.
     */
    public static void saveLinks(String url, List<String> links) {
        try {
            // Ensure the output directory exists
            Files.createDirectories(Paths.get(OUTPUT_DIR));

            // Generate a sanitized filename based on the URL
            String fileName = sanitizeFileName(url) + "_links.txt";
            File file = new File(OUTPUT_DIR + fileName);

            // Write the links to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Links extracted from: " + url + "\n\n");
                for (String link : links) {
                    writer.write(link + "\n");
                }
            }

            System.out.println("✅ Links saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("❌ Failed to save links for URL (" + url + "): " + e.getMessage());
        }
    }

    /**
     * Saves the full content of a page to a text file.
     *
     * @param url     The URL of the page.
     * @param content The full HTML or cleaned content of the page.
     */
    public static void savePageContent(String url, String content) {
        try {
            // Ensure the output directory exists
            Files.createDirectories(Paths.get(OUTPUT_DIR));

            // Generate a sanitized filename based on the URL
            String fileName = sanitizeFileName(url) + "_content.txt";
            File file = new File(OUTPUT_DIR + fileName);

            // Write the content to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Content from: " + url + "\n\n");
                writer.write(content);
            }

            System.out.println("📄 Saved content of " + url + " to " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("❌ Error saving page content for URL (" + url + "): " + e.getMessage());
        }
    }

    /**
     * Sanitizes a URL to create a valid filename.
     *
     * @param url The URL to sanitize.
     * @return A sanitized string suitable for use as a filename.
     */
    private static String sanitizeFileName(String url) {
        return url.replaceAll("[^a-zA-Z0-9]", "_") // Replace non-alphanumeric characters with underscores
                  .replaceAll("_+", "_")          // Replace multiple underscores with a single one
                  .replaceAll("^_|_$", "");       // Remove leading and trailing underscores
    }
}