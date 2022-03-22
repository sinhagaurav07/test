package com.gs.thread.splitwise.expense;

import com.gs.thread.splitwise.User;
import com.gs.thread.splitwise.split.EqualSplit;
import com.gs.thread.splitwise.split.Split;

import java.util.List;

public class EqualExpense extends Expense {

    public EqualExpense(double amount, User paidBy, List<Split> splits, ExpenseMetaData metadata) {
        super(amount, paidBy, splits, metadata);
    }


    @Override
    public boolean validate() {
        for (Split split : getSplits()) {
            if (!(split instanceof EqualSplit)) {
                return false;
            }
        }

        return true;
    }
}
