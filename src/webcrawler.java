import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class webcrawler {

    private static final Pattern LINK_PATTERN = Pattern.compile(
        "<a\\s+(?:[^>]*?\\s+)?href=[\"'](http[^\"'>\\s]+)[\"']", Pattern.CASE_INSENSITIVE);

    //main method
    public static void main(String[] args) throws IOException {
        List<String> urlsToVisit = readUrlsFromFile("./input_urls/input_urls.txt");
        Set<String> visitedUrls = new HashSet<>();

        for (String url : urlsToVisit) {
            if (visitedUrls.contains(url)) continue;
            visitedUrls.add(url);

            System.out.println("🔗 Crawling: " + url);
            //this try method for fetching html pages of particular websites of the urls specified.
            try {
                String html = fetchHtml(url);
                List<String> links = extractLinks(html);

                System.out.println("📌 Found links:");
                for (String link : links) {
                    System.out.println("  -> " + link);
                }

                System.out.println("-----");
            } catch (Exception e) {
                System.out.println("❌ Failed to fetch " + url + ": " + e.getMessage());
            }
        }
    }
/*this method read urls from the input_urls.txt file and extracts them for furthur processing */
    private static List<String> readUrlsFromFile(String filePath) throws IOException {
        List<String> urls = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                urls.add(line.trim());
            }
        }
        reader.close();
        return urls;
        //finally extract urls object which contains actual urls.
    }
/*This method takes in urls and builds the results sending requests to mozilla agent (web browser) */
    private static String fetchHtml(String urlStr) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(
            new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            result.append(inputLine).append("\n");
        }
        in.close();
        return result.toString();
        //result is returned here.
    }


    /* */
    private static List<String> extractLinks(String html) {
        List<String> links = new ArrayList<>();
        Matcher matcher = LINK_PATTERN.matcher(html);
        while (matcher.find()) {
            links.add(matcher.group(1));
        }
        return links;
    }
}