package coljamkop.momoney.Content;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
public class BudgetContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final Deque<Month> MONTH_DEQUE = new ArrayDeque<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, Month> MONTH_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        SimpleDateFormat format = new SimpleDateFormat("MM yyyy");
        Calendar calendar = Calendar.getInstance();
        Month month = new Month(format.format(calendar.getTime()), null);
        addMonth(month);
    }

    public static void addMonth(Month item) {
        MONTH_DEQUE.push(item);
        MONTH_MAP.put(item.hashCode(), item);
    }

    public static Month getThisMonth() {
        return MONTH_DEQUE.peek();
    }

    public static void monthRollover() {
        if (MONTH_DEQUE != null) {
            Month oldMonth = MONTH_DEQUE.peek();
            MONTH_DEQUE.push(new Month(oldMonth.getYear(),
                    oldMonth.getMonth() + 1,
                    oldMonth.getCategories()));
        }
    }
}
