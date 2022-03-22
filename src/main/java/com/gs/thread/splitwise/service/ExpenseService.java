package com.gs.thread.splitwise.service;

import com.gs.thread.splitwise.ExpenseType;
import com.gs.thread.splitwise.User;
import com.gs.thread.splitwise.expense.EqualExpense;
import com.gs.thread.splitwise.expense.ExactExpense;
import com.gs.thread.splitwise.expense.Expense;
import com.gs.thread.splitwise.expense.ExpenseMetaData;
import com.gs.thread.splitwise.expense.PercentageExpense;
import com.gs.thread.splitwise.split.PercentageSplit;
import com.gs.thread.splitwise.split.Split;

import java.util.List;

public class ExpenseService {

    public static Expense createExpense(ExpenseType expenseType, double amount, User paidBy, List<Split> splits, ExpenseMetaData expenseMetadata) {
        switch (expenseType) {
            case EXACT:
                return new ExactExpense(amount, paidBy, splits, expenseMetadata);
            case PERCENTAGE:
                for (Split split : splits) {
                    PercentageSplit percentSplit = (PercentageSplit) split;
                    split.setAmount((amount*percentSplit.getPercent())/100.0);
                }
                return new PercentageExpense(amount, paidBy, splits, expenseMetadata);
            case EQUAL:
                int totalSplits = splits.size();
                double splitAmount = ((double) Math.round(amount*100/totalSplits))/100.0;
                for (Split split : splits) {
                    split.setAmount(splitAmount);
                }
                splits.get(0).setAmount(splitAmount + (amount - splitAmount*totalSplits));
                return new EqualExpense(amount, paidBy, splits, expenseMetadata);
            default:
                return null;
        }
    }
}
