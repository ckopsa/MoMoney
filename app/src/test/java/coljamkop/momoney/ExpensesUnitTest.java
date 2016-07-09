package coljamkop.momoney;

/**
 * Created by emi_lion91 on 6/8/2016.
 */
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import coljamkop.momoney.Content.Expense;

import static org.junit.Assert.*;

public class ExpensesUnitTest {

    @Test
    public void There_isExpense() throws Exception{
        Expense exp = new Expense(new BigDecimal(123.00), "Category");
        assertEquals(exp.getTotal().compareTo(new BigDecimal(123.00)), 0);

        Expense exp1 = new Expense(new BigDecimal(55.33), "Category");
        assertEquals(exp1.getTotal().compareTo(new BigDecimal(55.33)), 0);

        Expense exp2 = new Expense(new BigDecimal(23.21), "Category");
        assertEquals(exp2.getTotal().compareTo(new BigDecimal(23.21)), 0);
    }

    @Test
    public void There_isDate() throws Exception{
        Expense exp = new Expense(new BigDecimal(100.0), "Category");
        Date d = new Date();
        String date = d.toString();

        assert(exp.getDate().equals(date));

    }
}
