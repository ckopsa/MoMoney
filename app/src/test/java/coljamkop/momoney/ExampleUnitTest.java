package coljamkop.momoney;

import org.junit.Test;

import coljamkop.momoney.dummy.Expenses;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

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