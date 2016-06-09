package coljamkop.momoney.dummy;

/**
 * Created by Aghbac on 6/8/16.
 */
public class Expense {
    private double total;
    private int date;

    public double getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Expense(double total, int date) {
        this.total = total;
        this.date = date;
    }
}
