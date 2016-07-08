package coljamkop.momoney.Content;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
    public static final List<Month> MONTH_LIST = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, Month> MONTH_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        Calendar calendar = Calendar.getInstance();
        for (int i = 1; i <= COUNT; i++) {
            Month month = new Month(calendar.YEAR, calendar.MONTH + i, null);
            for (int j = 1; j <= COUNT; j++) {
                month.addCategory(new Category(null, "Category " + j, new BigDecimal(10)));
            }
            addItem(month);
        }
    }

    private static void addItem(Month item) {
        MONTH_LIST.add(item);
        MONTH_MAP.put(item.hashCode(), item);
    }

    public static Month getThisMonth() {
        return MONTH_LIST.get(0);
    }
}
