import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * The Entry class.
 */
public class Entry {
    private int rank;
    private String title;
    private int points;
    private int numberOfComments;

    public Entry(int rank, String title, int points, int numberOfComments) {
        this.rank = rank;
        this.title = title;
        this.points = points;
        this.numberOfComments = numberOfComments;
    }

    /**
     * Sorts a list of entries by the number of points in ascending order.
     * @param list The list of entries.
     */
    public static void sortByPoints(final List<Entry> list) {
        sortByPoints(list, false);
    }

    /**
     * Sorts a list of entries by the number of points.
     * @param list The list of entries.
     * @param descendingOrder Whether the sorting order is descending or ascending.
     */
    public static void sortByPoints(final List<Entry> list, boolean descendingOrder) {
        if (descendingOrder)
            Collections.sort(list, Comparator.comparingInt(Entry ::getPoints).reversed());
        else
            Collections.sort(list, Comparator.comparingInt(Entry ::getPoints));
    }

    /**
     * Sorts a list of entries by the number of comments in ascending order.
     * @param list The list of entries.
     */
    public static void sortByNumberOfComments(final List<Entry> list) {
        sortByNumberOfComments(list, false);
    }

    /**
     * Sorts a list of entries by the number of comments.
     * @param list The list of entries.
     * @param descendingOrder Whether the sorting order is descending or ascending.
     */
    public static void sortByNumberOfComments(final List<Entry> list, boolean descendingOrder) {
        if (descendingOrder)
            Collections.sort(list, Comparator.comparingInt(Entry ::getNumberOfComments).reversed());
        else
            Collections.sort(list, Comparator.comparingInt(Entry ::getNumberOfComments));
    }

    /**
     * Filters a list of entries by the number of words in each entry title.
     * @param list The list of entries.
     * @param numberOfWords The number of words required.
     * @param operator Which equality operator should be used:
     *                 >  Greater Than
     *                 >= Greater Than Or Equal To
     *                 <  Less Than
     *                 <= Less Than Or Equal To
     * @return A new filtered list of entries.
     */
    public static List<Entry> filterByNumberOfWordsInTitle(List<Entry> list, int numberOfWords, Utils.Operator operator) {
        List<Entry> filteredList = new LinkedList<>();

        for (Entry entry : list) {
            boolean condition = false;
            switch (operator) {
                case GreaterThan:
                    condition = Utils.countWordsInString(entry.getTitle()) > numberOfWords;
                    break;
                case GreaterThanOrEqualTo:
                    condition = Utils.countWordsInString(entry.getTitle()) >= numberOfWords;
                    break;
                case LessThan:
                    condition = Utils.countWordsInString(entry.getTitle()) < numberOfWords;
                    break;
                case LessThanOrEqualTo:
                    condition = Utils.countWordsInString(entry.getTitle()) <= numberOfWords;
                    break;
            }
            if (condition) {
                filteredList.add(entry);
            }
        }

        return filteredList;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "rank=" + rank +
                ", title='" + title + '\'' +
                ", points=" + points +
                ", numberOfComments=" + numberOfComments +
                '}';
    }
}
