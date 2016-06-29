package coljamkop.momoney.Content;

import java.util.Date;

/**
 * Created by Aghbac on 6/8/16.
 */

/**
 * Stores the expense data including: total, date of purchase
 */
public class Expense {

    private double total;
    private String date;

    public Expense(double expense) {
        total = expense;
        this.date = new Date().toString();
    }

    /**
     * @return total amount of expense
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param expenseTotal total amount of the expense
     */
    public void setTotal(double expenseTotal) {
        total = expenseTotal;
    }


    /**
     * @param date purchase date of the expense
     */
    public void setDate(Date date) {
        this.date = date.toString();
    }

    /**
     * @return returns purchase date of expense as string
     */
    public String getDate() {
        return date;
    }
}
