package coljamkop.momoney;

/**
 * Created by emi_lion91 on 6/8/2016.
 */
import org.junit.Test;

import java.util.Date;

import coljamkop.momoney.Content.Expense;

import static org.junit.Assert.*;

public class ExpensesUnitTest {

    @Test
    public void There_isExpense() throws Exception{
        Expense exp = new Expense(123.00);
        assertEquals(exp.getTotal(), 123.00, 0);

        Expense exp1 = new Expense(55.33);
        assertEquals(exp1.getTotal(),55.33, 0);

        Expense exp2 = new Expense(23.21);
        assertEquals(exp2.getTotal(), 23.21, 0);
    }

    @Test
    public void There_isDate() throws Exception{
        Expense exp = new Expense(100.00);
        Date d = new Date();
        String date = d.toString();

        assert(exp.getDate().equals(date));

    }
}
