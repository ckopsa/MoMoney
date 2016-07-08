package coljamkop.momoney;

import org.junit.Test;

import java.math.BigDecimal;

import coljamkop.momoney.Content.Category;
import coljamkop.momoney.Content.Expense;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class CategoryUnitTest {
    @Test
    public void isInGoal_isCorrect() throws Exception {
        Category category = new Category(null, "New Category", new BigDecimal(20.0));
        // $0.00 <= $20.00
        assertEquals(true, category.isInGoal());
        category.addExpense(new BigDecimal(10.0));
        // $10.00 <= $20.00
        assertEquals(true, category.isInGoal());
        category.addExpense(new BigDecimal(10.0));
        // $20.00 <= $20.00
        assertEquals(true, category.isInGoal());
        category.addExpense(new BigDecimal(10.0));
        // $30.00 <= $20.00
        assertEquals(false, category.isInGoal());
    }

    @Test
    public void total_isCorrect() throws Exception {
        BigDecimal total = new BigDecimal(0.0);
        Category category = new Category(null, "New Category", new BigDecimal(0.0));
        for(int i = 0; i < 10; i++) {
            category.addExpense(new BigDecimal(10.0));
            total = total.add(new BigDecimal(10.0));
            assert(total.compareTo(category.getTotal()) == 0.0);
        }

    }
}