package coljamkop.momoney;

import org.junit.Test;

import coljamkop.momoney.dummy.Month;

/**
 * Created by Kate on 6/8/2016.
 */
public class MonthUnitTest {

    @Test
    public void There_isTotal() throws Exception{
        Month exp = new Month();
        assert(0 == exp.calcTotal());
        assert(false == exp.isInGoal());


    }
}
