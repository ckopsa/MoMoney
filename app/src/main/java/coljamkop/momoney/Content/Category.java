package coljamkop.momoney.Content;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aghbac on 6/8/16.
 */
public class Category implements Budgetable {
    private List<Expense> expenseList;
    private String categoryName;
    private double total;
    private double goal;

    /**
     * @return name of category
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName sets the name of the category
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @param total sets the total of the category
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @param goal sets the goal for the category
     */
    public void setGoal(double goal) {
        this.goal = goal;
    }

    /**
     * Loads the passed parameters into the private variables and
     * sums the expenses to set the total
     * @param expenseList a list of expenses
     * @param categoryName name for new category
     * @param goal budgetable goal for new category
     */
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

    /**
     * Adds an expense to the current list of expenses
     * @param expense expense to be added
     */
    public void addExpense(Double expense) {
        if (expenseList == null) {
            expenseList = new ArrayList<>();
        }
        expenseList.add(new Expense(expense));
        total += expense;
    }

    @Override
    /**
     * @return true when total is less than or equal to goal, else returns false
     */
    public boolean isInGoal() {
        return total <= goal;
    }

    @Override
    /**
     * @return total of expenses in category
     */
    public double getTotal() {
        return total;
    }

    /**
     * @return goal of category
     */
    public double getGoal() {
        return goal;
    }
}
