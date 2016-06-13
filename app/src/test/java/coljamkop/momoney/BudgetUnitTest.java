package coljamkop.momoney;

import org.junit.Test;

import coljamkop.momoney.Content.Budget;
import coljamkop.momoney.Content.Category;
import coljamkop.momoney.Content.Month;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class BudgetUnitTest {
    @Test
    public void monthRollOver_IsCorrect() throws Exception {
        Budget budget = new Budget();
        Month oldMonth = budget.getCurrentMonth();

        // give month data
        oldMonth.addCategory(new Category(null, "Groceries", 100.0));
        oldMonth.addCategory(new Category(null, "Dates", 10.0));
        oldMonth.addCategory(new Category(null, "Fast Food", 15.0));
        oldMonth.getCategory("Groceries").addExpense(30.0);
        oldMonth.getCategory("Dates").addExpense(8.0);
        oldMonth.getCategory("Fast Food").addExpense(5.0);

        // check data
        for (Category category :
                oldMonth.getCategories()) {
            if(category.getCategoryName().equals("Groceries")) {
                assertEquals(30.00, category.getTotal(), 0.0);
                assertEquals(100.00, category.getGoal(), 0.0);
            }
            if(category.getCategoryName().equals("Dates")) {
                assertEquals(8.00, category.getTotal(), 0.0);
                assertEquals(10.00, category.getGoal(), 0.0);
            }
            if(category.getCategoryName().equals("Fast Food")) {
                assertEquals(5.0, category.getTotal(), 0.0);
                assertEquals(15.00, category.getGoal(), 0.0);
            }
        }

        // month rolls over
        budget.monthRollover();
        
        // check categories for 0.00 total
        for (Category category :
                budget.getCurrentMonth().getCategories()) {
            assertEquals(0.00, category.getTotal(), 0.0);
        }
    }

    @Test
    public void yearRollOver_IsCorrect() throws Exception {
        Budget budget = new Budget();
    }
}