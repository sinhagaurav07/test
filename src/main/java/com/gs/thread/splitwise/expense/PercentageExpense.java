package com.gs.thread.splitwise.expense;

import com.gs.thread.splitwise.User;
import com.gs.thread.splitwise.split.PercentageSplit;
import com.gs.thread.splitwise.split.Split;

import java.util.List;

public class PercentageExpense extends Expense {

    public PercentageExpense(double amount, User paidBy, List<Split> splits, ExpenseMetaData metadata) {
        super(amount, paidBy, splits, metadata);
    }

    @Override
    public boolean validate() {
        for (Split split : getSplits()) {
            if (!(split instanceof PercentageSplit)) {
                return false;
            }
        }

        double totalPercent = 100;
        double sumSplitPercent = 0;
        for (Split split : getSplits()) {
            PercentageSplit percentageSplit = (PercentageSplit) split;
            sumSplitPercent += percentageSplit.getPercent();
        }

        if (totalPercent != sumSplitPercent) {
            return false;
        }

        return true;
    }
}
