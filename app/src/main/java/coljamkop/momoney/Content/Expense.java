package coljamkop.momoney.Content;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Aghbac on 6/8/16.
 */

/**
 * Stores the expense data including: total, date of purchase
 */
public class Expense implements Serializable {

    private BigDecimal total;
    private Date date;
    private String category;

    public Expense(BigDecimal expense, String categoryName) {
        this.total = expense;
        this.category = categoryName;
        this.date = new Date();
    }

    /**
     * @return total amount of expense
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param expenseTotal total amount of the expense
     */
    public void setTotal(BigDecimal expenseTotal) {
        total = expenseTotal;
    }


    /**
     * @param date purchase date of the expense
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return returns purchase date of expense as string
     */
    public Date getDate() {
        return date;
    }

    public String getCategoryName() {
        return category;
    }
}
