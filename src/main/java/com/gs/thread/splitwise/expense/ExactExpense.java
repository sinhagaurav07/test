package com.gs.thread.splitwise.expense;

import com.gs.thread.splitwise.User;
import com.gs.thread.splitwise.split.ExactSplit;
import com.gs.thread.splitwise.split.Split;

import java.util.List;

public class ExactExpense extends Expense {


    public ExactExpense(double amount, User paidBy, List<Split> splits, ExpenseMetaData metadata) {
        super(amount, paidBy, splits, metadata);
    }

    @Override
    public boolean validate() {
        for (Split split : getSplits()) {
            if (!(split instanceof ExactSplit)) {
                return false;
            }
        }

        double totalAmount = getAmount();
        double sumSplitAmount = 0;
        for (Split split : getSplits()) {
            ExactSplit exactSplit = (ExactSplit) split;
            sumSplitAmount += exactSplit.getAmount();
        }

        if (totalAmount != sumSplitAmount) {
            return false;
        }

        return true;
    }
}
