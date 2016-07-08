package coljamkop.momoney.Content;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Represents our entire budget including all months/categories/expenses
 */
public class Budget {
    private Deque<Month> monthStack;

    /**
     * Creates an ArrayDeque for the month stack
     */
    public Budget() {
        this.monthStack = new ArrayDeque<>();
        monthStack.push(new Month(2016, 1, null));
    }

    /**
     * Returns the most recent Month
     * @return most recent month
     */
    public Month getCurrentMonth() {
        return monthStack.peek();
    }

    /**
     * Rolls the month over to the next month
     */
    public void monthRollover() {
        if (monthStack != null) {
            Month oldMonth = monthStack.peek();
            monthStack.push(new Month(oldMonth.year,
                    oldMonth.month + 1,
                    oldMonth.getCategories()));
        }
    }
}
