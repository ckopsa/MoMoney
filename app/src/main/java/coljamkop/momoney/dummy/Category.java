package coljamkop.momoney.dummy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aghbac on 6/8/16.
 */
public class Category {
    List<Expense> expenseList;
    double total;
    double goal;

    public Category(List<Expense> expenseList, double total, double goal) {
        this.expenseList = expenseList;
        this.total = total;
        this.goal = goal;
    }

    public Category(double total, double goal) {
        this(null, total, goal);
    }

    public void addExpense(Expense expense) {
        if (expenseList == null) {
            expenseList = new ArrayList<>();
        }
        expenseList.add(expense);
        total += expense.getTotal();
    }

    public boolean isInGoal() {
        return total <= goal;
    }

    public double getTotal() {
        return this.total;
    }
}
