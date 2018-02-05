import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PageFetcher {
    // Fake USER_AGENT to make the web server think the robot is a normal web browser.
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:58.0) Gecko/20100101 Firefox/58.0";
    // HTTP OK status code which indicates that everything is OK.
    private static final int HTTP_OK_CODE = 200;

    /**
     * Fetches a page.
     * @param url The page's url.
     * @return The fetch result.
     */
    public FetchResult fetchPage(final String url) {
        FetchResult fetchResult = new FetchResult();

        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();

            if (connection.response().statusCode() == HTTP_OK_CODE) {
                System.out.println("Page visited: Received web page data from " + url);
            }

            if (!connection.response().contentType().contains("text/html")) {
                System.out.println("Failure: Retrieved something other than HTML.");
                fetchResult.setSuccess(false);
            }
            else {
                fetchResult.setSuccess(true);
                fetchResult.setFetchedUrl(url);
                fetchResult.setHtmlDocument(htmlDocument);
            }
        } catch(IOException ioe) {
            // HTTP request unsuccessful.
            fetchResult.setSuccess(false);
        }

        return fetchResult;
    }
}
