package coljamkop.momoney.dummy;

import java.util.Date;

/**
 * Created by Aghbac on 6/8/16.
 */
public class Expense {

    private double total;
    private String date;

    public Expense(double expense) {
        total = expense;
        setDate();
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double Expense) {
        total = Expense;
    }

    public void setDate() {
        Date d = new Date();
        date = d.toString();
    }

    public String getDate() {
        return date;
    }
}
