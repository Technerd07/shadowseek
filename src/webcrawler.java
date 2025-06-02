import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class WebCrawler {

    // Regex pattern to extract hyperlinks from HTML
    private static final Pattern LINK_PATTERN = Pattern.compile(
        "<a\\s+(?:[^>]*?\\s+)?href=[\"'](http[^\"'>\\s]+)[\"']", Pattern.CASE_INSENSITIVE);

    // File path to the input URLs file
    private static final String INPUT_FILE_PATH = "./input_urls/input_urls.txt";

    public static void main(String[] args) {
        System.out.println("🌐 Starting Web Crawler...");

        try {
            // Load URLs from the input file
            List<String> urlsToVisit = loadUrlsFromFile(INPUT_FILE_PATH);
            Set<String> visitedUrls = new HashSet<>();

            // Process each URL
            for (String url : urlsToVisit) {
                if (visitedUrls.contains(url)) {
                    System.out.println("🔄 Skipping already visited URL: " + url);
                    continue;
                }

                visitedUrls.add(url);
                System.out.println("\n🔗 Crawling: " + url);

                try {
                    // Fetch HTML content and extract links
                    String html = fetchHtmlContent(url);
                    List<String> links = extractLinksFromHtml(html);

                    // Display extracted links
                    if (links.isEmpty()) {
                        System.out.println("📭 No links found on this page.");
                    } else {
                        System.out.println("📌 Found links:");
                        for (String link : links) {
                            System.out.println("  -> " + link);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("❌ Error fetching URL (" + url + "): " + e.getMessage());
                }
            }

            System.out.println("\n✅ Web crawling completed!");
        } catch (IOException e) {
            System.out.println("❌ Failed to load URLs from file: " + e.getMessage());
        }
    }

    /**
     * Loads URLs from a file and returns them as a list.
     */
    private static List<String> loadUrlsFromFile(String filePath) throws IOException {
        System.out.println("📂 Loading URLs from file: " + filePath);
        List<String> urls = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    urls.add(line.trim());
                }
            }
        }
        System.out.println("✅ Loaded " + urls.size() + " URLs.");
        return urls;
    }

    /**
     * Fetches the HTML content of a given URL.
     */
    private static String fetchHtmlContent(String urlStr) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine).append("\n");
            }
        }

        return result.toString();
    }

    /**
     * Extracts all hyperlinks from the given HTML content.
     */
    private static List<String> extractLinksFromHtml(String html) {
        List<String> links = new ArrayList<>();
        Matcher matcher = LINK_PATTERN.matcher(html);
        while (matcher.find()) {
            links.add(matcher.group(1));
        }
        return links;
    }
}