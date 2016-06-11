package coljamkop.momoney;

import org.junit.Test;

import coljamkop.momoney.Content.Category;
import coljamkop.momoney.Content.Expense;
import coljamkop.momoney.Content.Month;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MonthUnitTest {
    @Test
    public void isInGoal_isCorrect() throws Exception {
        Month month = new Month(2016, 6);
        // $0.00 <= $0.00
        assertEquals(true, month.isInGoal());
        month.addCategory(new Category(null, "Groceries", 20.0));
    }

    @Test
    public void total_isCorrect() throws Exception {
        double total = 0;
        Category category = new Category(null, "New Category", 0.0);
        for(int i = 0; i < 10; i++) {
            category.addExpense(new Expense(10.0));
            total += 10.0;
            assertEquals(total, category.getTotal(), 0.0);
        }

    }
}