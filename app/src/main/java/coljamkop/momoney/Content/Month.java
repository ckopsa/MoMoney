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
    private String year;
    private String month;
    private String month_year;
    private BigDecimal total;
    private BigDecimal goal;

    public Month(String year, String month, @Nullable List<Category> categoryList) {
        this.year = year;
        this.month = month;
        this.month_year = month + " " + year;
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

    public Month(String Month_Year, List<Category> categoryList){
        this.month_year = Month_Year;
        String[] monthYear = month_year.split(" ");
        this.month = monthYear[0];
        this.year = monthYear[1];
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

    public boolean addCategory(Category category) {
        for(Category iCategory : categoryList) {
            if (iCategory.getCategoryName().equals(category.getCategoryName()))
                return false;
        }
        categoryList.add(category);
        categoryMap.put(category.getCategoryName(), category);

        this.goal = this.goal.add(category.getGoal());
        this.total = this.total.add(category.getTotal());
        return true;
    }

    @Override
    public boolean isInGoal() {
        return total.compareTo(goal) <= 0;
    }

    @Override
    public BigDecimal getTotal() {
        BigDecimal temp = BigDecimal.ZERO;
        for (Category category : categoryList)
            temp = temp.add(category.getTotal());
        return temp;
    }

    public Category getCategory(String categoryName) {
        return categoryMap.get(categoryName);
    }

    public List<Category> getCategories() {
        return categoryList;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
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

    public boolean categoryExists(String categoryName) {
        for(Category iCategory : categoryList) {
            if (iCategory.getCategoryName().equals(categoryName))
                return false;
        }
        return true;
    }
}
