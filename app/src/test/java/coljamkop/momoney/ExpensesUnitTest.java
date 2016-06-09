package coljamkop.momoney;

/**
 * Created by emi_lion91 on 6/8/2016.
 */
import org.junit.Test;
import coljamkop.momoney.dummy.Expenses;
import static org.junit.Assert.*;

public class ExpensesUnitTest {

    @Test
    public void There_isExpense() throws Exception{
        Expenses exp = new Expenses(123.00);
        assertEquals(exp.getTotal(), 123.00, 0);

        Expenses exp1 = new Expenses(55.33);
        assertEquals(exp1.getTotal(),55.33, 0);

        Expenses exp2 = new Expenses(23.21);
        assertEquals(exp2.getTotal(), 23.21, 0);


    }
}
