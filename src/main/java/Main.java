import java.util.*;

public class Main {
    private static Crawler crawler;

    private static void init() {
        crawler = new Crawler(10, 30);

        // Workaround to prevent the crawler from fetching pages we do not want it to visit,
        // E.g. same page with different urls
        Set<String> pagesBlacklisted = new HashSet<>();
        pagesBlacklisted.add("https://news.ycombinator.com");
        pagesBlacklisted.add("https://news.ycombinator.com/news");

        crawler.setPagesBlacklisted(pagesBlacklisted);
    }

    public static void main(String[] args) {
        System.out.println("Web Crawler");

        // Init crawler.
        init();

        // Process page.
        crawler.processPage("https://news.ycombinator.com/");

        // Print entries.
        for (Entry entry : crawler.getEntries()) {
            System.out.println(entry.toString());
        }

        // Filtering.
        String input;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nSelect option: (f) Filter entries (q) Quit");
            input = sc.nextLine().trim().toLowerCase();

            if ("f".equals(input)) {
                System.out.println(
                        "(1) Entries with more than five words in the title ordered by amount of comments first.\n" +
                        "(2) Entries with less than or equal to five words in the title ordered by points.");
                input = sc.nextLine().trim().toLowerCase();

                // Get entries from crawler.
                List<Entry> entries = crawler.getEntries();

                // For each case, first filter the entries, then sort them.
                if ("1".equals(input)) {
                    entries = Entry.filterByNumberOfWordsInTitle(entries, 5, Utils.Operator.GreaterThan);
                    Entry.sortByNumberOfComments(entries, true);
                }
                else if ("2".equals(input)) {
                    entries = Entry.filterByNumberOfWordsInTitle(entries, 5, Utils.Operator.LessThanOrEqualTo);
                    Entry.sortByPoints(entries, true);
                }
                else {
                    System.out.println("Invalid option.");
                    continue;
                }

                for (Entry entry : entries) {
                    System.out.println(entry.toString());
                }
            }
        } while (!"q".equals(input));
    }
}
