import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UtilsTest.class,
        EntryTest.class,
        PageParserTest.class
})
public class AllTests {
}