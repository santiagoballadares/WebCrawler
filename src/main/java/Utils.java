public class Utils {
    public enum Operator {
        EqualTo,
        GreaterThan,
        GreaterThanOrEqualTo,
        LessThan,
        LessThanOrEqualTo
    }

    public static boolean isNullOrEmpty(final String s) {
        return s == null || s.trim().isEmpty();
    }

    public static int getIntFromString(final String s) {
        if (isNullOrEmpty(s)) {
            return 0;
        }

        return Integer.parseInt(s.trim().replaceAll("[^0-9]", ""));
    }

    public static String trimChar(final String s, final char c) {
        return s.replaceAll(c + "$|^" + c, "");
    }

    public static int countWordsInString(String s) {
        return s.split("\\s+").length;
    }
}
