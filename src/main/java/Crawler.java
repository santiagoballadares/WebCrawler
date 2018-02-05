import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Crawler {
    private final int maxPagesToVisit;
    private final int minEntriesToGet;

    private Set<String> pagesBlacklisted = new HashSet<>();
    private Set<String> pagesVisited = new HashSet<>();
    private List<String> pagesToVisit = new LinkedList<>();
    private List<Entry> entries = new LinkedList<>();

    private PageFetcher pageFetcher = new PageFetcher();
    private PageParser pageParser = new PageParser();

    public Crawler(int maxPagesToVisit, int minEntriesToGet) {
        this.maxPagesToVisit = maxPagesToVisit;
        this.minEntriesToGet = minEntriesToGet;
    }

    /**
     * Process a web page.
     * @param url The page's url to process
     */
    public void processPage(final String url) {
        while (pagesVisited.size() < maxPagesToVisit) {
            String currentUrl;
            FetchResult fetchResult;

            if (pagesToVisit.isEmpty()) {
                if (pagesVisited.contains(url)) {
                    System.out.println("Process ended: No more links found.");
                    break;
                }
                currentUrl = url;
                pagesVisited.add(url);
            } else {
                currentUrl = getNextUrl();
            }

            fetchResult = pageFetcher.fetchPage(currentUrl);

            boolean success = haveAllEntries(fetchResult);
            if (success) {
                System.out.println("Extraction completed: " + minEntriesToGet + " entries extracted.");
                break;
            }

            pagesToVisit.addAll(pageParser.extractLinks(fetchResult, "a[href^=\"news?p=\"]"));
        }

        System.out.println("Done: Visited " + pagesVisited.size() + " page(s).");
    }

    /**
     * Gets the next url checking it against already visited pages and a black-list of pages.
     * @return The next url to fetch.
     */
    private String getNextUrl() {
        String nextUrl;

        do {
            nextUrl = pagesToVisit.remove(0);
        } while (pagesVisited.contains(nextUrl) || pagesBlacklisted.contains(nextUrl));

        pagesVisited.add(nextUrl);

        return nextUrl;
    }

    /**
     * Checks whether the number of entries required has been found.
     * @param fetchResult Result from fetching a page.
     * @return True if all required entries have been found, else False.
     */
    private boolean haveAllEntries(FetchResult fetchResult) {
        if (entries.size() < minEntriesToGet) {
            List<Entry> pageEntries = pageParser.extractEntries(fetchResult);

            while (!pageEntries.isEmpty()) {
                Entry entry = pageEntries.remove(0);
                entries.add(entry);

                if (entries.size() >= minEntriesToGet) {
                    break;
                }
            }
        }

        return entries.size() >= minEntriesToGet;
    }

    /**
     * Returns the list of entries found.
     * @return The entries.
     */
    public List<Entry> getEntries() {
        return entries;
    }

    /**
     * Returns the list of blacklisted pages.
     * @return The blacklisted pages.
     */
    public Set<String> getPagesBlacklisted() {
        return pagesBlacklisted;
    }

    /**
     * Sets the list of blacklisted pages.
     * @param pagesBlacklisted The blacklisted pages.
     */
    public void setPagesBlacklisted(Set<String> pagesBlacklisted) {
        this.pagesBlacklisted = pagesBlacklisted;
    }
}
