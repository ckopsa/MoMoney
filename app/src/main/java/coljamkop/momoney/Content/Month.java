package coljamkop.momoney.Content;

import android.support.annotation.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aghbac on 6/8/16.
 */
public class Month implements Budgetable {
    private Map<String, Category> categoryMap;
    private List<Category> categoryList;
    private int year;
    private int month;
    private BigDecimal total;
    private BigDecimal goal;

    public Month(int year, int month, @Nullable List<Category> categoryList) {
        this.year = year;
        this.month = month;
        this.goal = new BigDecimal(0.0);
        this.total = new BigDecimal(0.0);
        this.categoryMap = new HashMap<>();
        this.categoryList = new ArrayList<>();
        if (categoryList != null) {
            for (Category category :
                    categoryList) {
                addCategory(new Category(null, category.getCategoryName(), category.getGoal()));
            }
        }
    }

    public void addCategory(Category category) {
        categoryList.add(category);
        categoryMap.put(category.getCategoryName(), category);

        this.goal = this.goal.add(category.getGoal());
        this.total = this.total.add(category.getTotal());
    }

    @Override
    public boolean isInGoal() {
        return total.compareTo(goal) <= 0;
    }

    @Override
    public BigDecimal getTotal() {
        return total;
    }

    public Category getCategory(String categoryName) {
        return categoryMap.get(categoryName);
    }

    public List<Category> getCategories() {
        return categoryList;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public void deleteCategory(Category category) {
        categoryList.remove(category);
        categoryMap.remove(category);
    }

    public BigDecimal getGoal() {
        return goal;
    }

    public void setGoal(BigDecimal goal) {
        this.goal = goal;
    }
}
