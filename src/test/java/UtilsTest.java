import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void isNullOrEmpty() {
        String nonEmptyString = "This is a not null nor empty string.";
        String emptyString = "";
        String nullString = null;

        Assert.assertFalse("False expected.", Utils.isNullOrEmpty(nonEmptyString));
        Assert.assertTrue("True expected.", Utils.isNullOrEmpty(emptyString));
        Assert.assertTrue("True expected.", Utils.isNullOrEmpty(nullString));
    }

    @Test
    public void getIntFromString() {
        String s = "40. ";
        int expected = 40;
        Assert.assertEquals("Int expected.", expected, Utils.getIntFromString(s));
    }

    @Test
    public void trimChar() {
        String s = "www.website.com/";
        char c = '/';
        String expected = "www.website.com";
        Assert.assertEquals("String mismatch.", expected, Utils.trimChar(s, c));
    }

    @Test
    public void countWordsInString() {
        String s = "This is a string.";
        int expected = 4;
        Assert.assertEquals("Int expected.", expected, Utils.countWordsInString(s));
    }
}