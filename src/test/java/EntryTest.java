import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class EntryTest {
    private Entry entry_1;
    private Entry entry_2;
    private Entry entry_3;

    @Before
    public void setUp() {
        entry_1 = new Entry(1,"Title 1: Lorem ipsum dolor sit amet", 5, 100);
        entry_2 = new Entry(2,"Title 2: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec.", 25, 50);
        entry_3 = new Entry(3,"Title 3: Lorem ipsum dolor", 15, 80);
    }

    @Test
    public void sortByPoints() {
        List<Entry> list = new LinkedList<>();
        list.add(entry_1);
        list.add(entry_2);
        list.add(entry_3);

        List<Entry> expectedAsc = new LinkedList<>();
        expectedAsc.add(entry_1);
        expectedAsc.add(entry_3);
        expectedAsc.add(entry_2);

        Entry.sortByPoints(list);
        Assert.assertEquals("Ascending order expected", expectedAsc, list);

        List<Entry> expectedDesc = new LinkedList<>();
        expectedDesc.add(entry_2);
        expectedDesc.add(entry_3);
        expectedDesc.add(entry_1);

        Entry.sortByPoints(list, true);
        Assert.assertEquals("Descending order expected", expectedDesc, list);
    }

    @Test
    public void sortByNumberOfComments() {
        List<Entry> list = new LinkedList<>();
        list.add(entry_1);
        list.add(entry_2);
        list.add(entry_3);

        List<Entry> expectedAsc = new LinkedList<>();
        expectedAsc.add(entry_2);
        expectedAsc.add(entry_3);
        expectedAsc.add(entry_1);

        Entry.sortByNumberOfComments(list);
        Assert.assertEquals("Ascending order expected", expectedAsc, list);

        List<Entry> expectedDesc = new LinkedList<>();
        expectedDesc.add(entry_1);
        expectedDesc.add(entry_3);
        expectedDesc.add(entry_2);

        Entry.sortByNumberOfComments(list, true);
        Assert.assertEquals("Descending order expected", expectedDesc, list);
    }

    @Test
    public void filterByNumberOfWordsInTitle() {
        List<Entry> list = new LinkedList<>();
        list.add(entry_1);
        list.add(entry_2);
        list.add(entry_3);

        List<Entry> expected_1 = new LinkedList<>();
        expected_1.add(entry_1);

        Assert.assertEquals("Lists mismatch", expected_1, Entry.filterByNumberOfWordsInTitle(list, 7, Utils.Operator.EqualTo));

        List<Entry> expected_2 = new LinkedList<>();
        expected_2.add(entry_1);
        expected_2.add(entry_2);

        Assert.assertEquals("Lists mismatch", expected_2, Entry.filterByNumberOfWordsInTitle(list, 5, Utils.Operator.GreaterThan));
    }
}