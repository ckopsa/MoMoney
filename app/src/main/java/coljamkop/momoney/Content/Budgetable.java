package coljamkop.momoney.Content;

import java.math.BigDecimal;

/**
 * Created by Aghbac on 6/10/16.
 */
public interface Budgetable {
    boolean isInGoal();
    BigDecimal getTotal();
}
