package coljamkop.momoney;

import org.junit.Test;

import java.math.BigDecimal;

import coljamkop.momoney.Content.Category;
import coljamkop.momoney.Content.Month;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MonthUnitTest {
    @Test
    public void isInGoal_isCorrect() throws Exception {
        Month month = new Month(2016, 6, null);
        // $0.00 <= $0.00
        assertEquals(true, month.isInGoal());
        month.addCategory(new Category(null, "Groceries", new BigDecimal(20)));
    }

    @Test
    public void total_isCorrect() throws Exception {
        BigDecimal total = new BigDecimal(0);
        Category category = new Category(null, "New Category", new BigDecimal(0.0));
        for(int i = 0; i < 10; i++) {
            category.addExpense(new BigDecimal(10.0));
            total = total.add(new BigDecimal(10.0));
            assert(total.compareTo(category.getTotal()) == 0.0);
        }
    }

    @Test
    public void month_roll_over() {
        Month oldMonth = new Month(2016, 1, null);

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
                assert(category.getTotal().compareTo(new BigDecimal(30.0)) == 0.0);
                assert(category.getGoal().compareTo(new BigDecimal(100.0)) == 0.0);
            }
            if(category.getCategoryName().equals("Dates")) {
                assert(category.getTotal().compareTo(new BigDecimal(8.0)) == 0.0);
                assert(category.getGoal().compareTo(new BigDecimal(10.0)) == 0.0);
            }
            if(category.getCategoryName().equals("Fast Food")) {
                assert(category.getTotal().compareTo(new BigDecimal(5.0)) == 0.0);
                assert(category.getGoal().compareTo(new BigDecimal(15.0)) == 0.0);
            }
        }

        // month rolls over
        Month newMonth = new Month(2016, 2, oldMonth.getCategories());
        // check categories for 0.00 total
        for (Category category :
                newMonth.getCategories()) {
            assert(category.getTotal().compareTo(new BigDecimal(0.00)) == 0.0);
        }
    }
}