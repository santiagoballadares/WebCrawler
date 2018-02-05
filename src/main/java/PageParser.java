import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

public class PageParser {
    /**
     * Parses a html document and extracts all the links.
     * @param fetchResult The fetch result.
     * @param linkPattern A css pattern to look for in the html document.
     * @return A list of links.
     */
    public List<String> extractLinks(final FetchResult fetchResult, final String linkPattern) {
        if (!fetchResult.isSuccess()) {
            System.out.println("ERROR: Cannot process links. Fetch page operation unsuccessful.");
            return null;
        }

        Elements linksOnPage = fetchResult.getHtmlDocument().select(linkPattern);

        System.out.println("Found " + linksOnPage.size() + " link(s).");

        List<String> links = new LinkedList<>();
        for (Element link : linksOnPage) {
            links.add(Utils.trimChar(link.absUrl("href"), '/'));
        }

        return links;
    }

    /**
     * Parses a html document and extracts all the entries.
     * @param fetchResult The fetch result.
     * @return A list of entries.
     */
    public List<Entry> extractEntries(final FetchResult fetchResult) {
        if (!fetchResult.isSuccess()) {
            System.out.println("ERROR: Cannot process entries. Fetch page operation unsuccessful.");
            return null;
        }

        List<Entry> entries = new LinkedList<>();

        Element table = fetchResult.getHtmlDocument().selectFirst("table[class=\"itemlist\"]");

        if (table != null) {
            Elements rows = table.select("tr");

            for (int i = 0; i < rows.size() - 2; i += 3) {
                int rank = Utils.getIntFromString(rows.get(i).select("td span[class^=\"rank\"]").text());
                String title = rows.get(i).select("td a[class^=\"storylink\"]").text().trim();
                int points = Utils.getIntFromString(rows.get(i + 1).select("td span[class^=\"score\"]").text());
                int numberOfComments = Utils.getIntFromString(rows.get(i + 1).select("td a:matches(^.*?(comment).*$)").text());

                if (rank == 0 && Utils.isNullOrEmpty(title) && points == 0 && numberOfComments == 0) {
                    continue;
                }

                Entry entry = new Entry(rank, title, points, numberOfComments);
                entries.add(entry);
            }
        }

        return entries;
    }
}
