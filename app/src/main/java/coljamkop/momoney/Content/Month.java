package coljamkop.momoney.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aghbac on 6/8/16.
 */
public class Month implements Budgetable {
    List<Category> categoryList;
    int year;
    int month;
    double total;
    double goal;

    public Month(int year, int month) {
        this.year = year;
        this.month = month;
        this.goal = 0.0;
        this.total = 0.0;
        this.categoryList = null;
    }

    public void addCategory(Category category) {
        if (categoryList == null) {
            categoryList = new ArrayList<>();
        }
        categoryList.add(category);
        this.goal += category.getGoal();
        this.total += category.getTotal();
    }

    @Override
    public boolean isInGoal() {
        return total <= goal;
    }

    @Override
    public double getTotal() {
        return total;
    }
}
