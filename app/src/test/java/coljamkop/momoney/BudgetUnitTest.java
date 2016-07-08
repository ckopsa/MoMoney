package coljamkop.momoney;

import org.junit.Test;

import java.math.BigDecimal;

import coljamkop.momoney.Content.Budget;
import coljamkop.momoney.Content.Category;
import coljamkop.momoney.Content.Month;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class BudgetUnitTest {
    @Test
    public void monthRollOver_IsCorrect() throws Exception {
        Budget budget = new Budget();
        Month oldMonth = budget.getCurrentMonth();

        // give month data
        oldMonth.addCategory(new Category(null, "Groceries", new BigDecimal(100.0)));
        oldMonth.addCategory(new Category(null, "Dates", new BigDecimal(10.0)));
        oldMonth.addCategory(new Category(null, "Fast Food", new BigDecimal(15.0)));
        oldMonth.getCategory("Groceries").addExpense(new BigDecimal(30.0));
        oldMonth.getCategory("Dates").addExpense(new BigDecimal(8.0));
        oldMonth.getCategory("Fast Food").addExpense(new BigDecimal(5.0));

        // check data
        for (Category category :
                oldMonth.getCategories()) {
            if(category.getCategoryName().equals("Groceries")) {
                assert(category.getTotal().compareTo(new BigDecimal(30.0)) == 0);
                assert(category.getGoal().compareTo(new BigDecimal(100.0)) == 0);
            }
            if(category.getCategoryName().equals("Dates")) {
                assert(category.getTotal().compareTo(new BigDecimal(8.0)) == 0);
                assert(category.getGoal().compareTo(new BigDecimal(10.0)) == 0);
            }
            if(category.getCategoryName().equals("Fast Food")) {
                assert(category.getTotal().compareTo(new BigDecimal(5.0)) == 0);
                assert(category.getGoal().compareTo(new BigDecimal(15.0)) == 0);
            }
        }

        // month rolls over
        budget.monthRollover();

        // check categories for 0.00 total
        for (Category category :
                budget.getCurrentMonth().getCategories()) {
            assert(category.getTotal().compareTo(new BigDecimal(0.00)) == 0);
        }
    }

    @Test
    public void yearRollOver_IsCorrect() throws Exception {
        Budget budget = new Budget();
    }
}