package utils;

import java.math.BigDecimal;
import java.util.Comparator;

public class SortUtils {

    private static final String NUMBER_PATTERN = "-?\\d+(\\.\\d+)?";

    public static Comparator<String> getStringComparator(boolean ascending) {
        return (a, b) -> {
            int result = compareStrings(a, b);
            return ascending ? result : -result;
        };
    }

    private static int compareStrings(String a, String b) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;

        String normA = a.trim();
        String normB = b.trim();

        if (normA.isEmpty() && normB.isEmpty()) return 0;
        if (normA.isEmpty()) return -1;
        if (normB.isEmpty()) return 1;

        if (isNumeric(normA) && isNumeric(normB)) {
            return compareNumeric(normA, normB);
        }
        return normA.compareTo(normB);
    }

    private static boolean isNumeric(String value) {
        return value.matches(NUMBER_PATTERN);
    }

    private static int compareNumeric(String a, String b) {
        try {
            return new BigDecimal(a).compareTo(new BigDecimal(b));
        } catch (NumberFormatException e) {
            return a.compareTo(b);
        }
    }
}
