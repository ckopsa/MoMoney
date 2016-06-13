package coljamkop.momoney.Content;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Created by Aghbac on 6/10/16.
 */
public class Budget {
    private Deque<Month> monthStack;

    public Budget() {
        this.monthStack = new ArrayDeque<>();
        monthStack.push(new Month(2016, 1, null));
    }

    public Month getCurrentMonth() {
        return monthStack.peek();
    }

    public void monthRollover() {
        if (monthStack != null) {
            Month oldMonth = monthStack.peek();
            monthStack.push(new Month(oldMonth.year,
                    oldMonth.month + 1,
                    oldMonth.getCategories()));
        }
    }
}
