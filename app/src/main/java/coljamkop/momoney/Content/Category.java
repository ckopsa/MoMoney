package coljamkop.momoney.Content;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aghbac on 6/8/16.
 */
public class Category implements Budgetable {
    List<Expense> expenseList;
    String categoryName;
    double total;
    double goal;

    public Category(@Nullable List<Expense> expenseList, String categoryName, double goal) {
        this.expenseList = expenseList;
        this.categoryName = categoryName;
        if(expenseList != null) {
            for (Expense expense:
                 expenseList) {
             total += expense.getTotal();
            }
        } else {
            this.total = 0;
        }
        this.goal = goal;
    }

    public void addExpense(Double expense) {
        if (expenseList == null) {
            expenseList = new ArrayList<>();
        }
        expenseList.add(new Expense(expense));
        total += expense;
    }

    @Override
    public boolean isInGoal() {
        return total <= goal;
    }

    @Override
    public double getTotal() {
        return total;
    }

    public double getGoal() {
        return goal;
    }
}
