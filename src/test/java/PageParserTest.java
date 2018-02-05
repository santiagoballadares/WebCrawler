import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class PageParserTest {
    private String html = "<html><head><title>News</title></head><body><table class=\"itemlist\"><tr><td>" +
            "<span class=\"rank\">1.</span></td><td><a href=\"https://www.reuters.com\" class=\"storylink\">" +
            "Reuters News</a></td></tr><tr><td><span class=\"score\">90 points</span><a>17&nbsp;comments</a>" +
            "</td></tr><tr></tr><tr><td><span class=\"rank\">2.</span></td><td><a href=\"https://www.bbc.com\" " +
            "class=\"storylink\">BBC News</a></td></tr><tr><td><span class=\"score\">120 points</span><a>25&nbsp;" +
            "comments</a>/td></tr><tr></tr><tr></tr></table></body></html>";
    private Document htmlDocumet;
    private FetchResult fetchResult;
    private PageParser pageParser;

    @Before
    public void setUp() {
        htmlDocumet = Jsoup.parse(html);
        fetchResult = new FetchResult();
        fetchResult.setSuccess(true);
        fetchResult.setFetchedUrl("www.test.com");
        fetchResult.setHtmlDocument(htmlDocumet);

        pageParser = new PageParser();
    }

    @Test
    public void extractLinks() {
        List<String> expectedLinks = new LinkedList<>();
        expectedLinks.add("https://www.reuters.com");
        expectedLinks.add("https://www.bbc.com");

        Assert.assertEquals("Lists mismatch", expectedLinks, pageParser.extractLinks(fetchResult, "a[href]"));
    }

    @Test
    public void extractEntries() {
        List<Entry> expectedEntries =  new LinkedList<>();
        expectedEntries.add(new Entry(1, "Reuters News", 90, 17));
        expectedEntries.add(new Entry(2, "BBC News", 120, 25));

        Assert.assertEquals("Lists mismatch", expectedEntries.toString(), pageParser.extractEntries(fetchResult).toString());
    }
}