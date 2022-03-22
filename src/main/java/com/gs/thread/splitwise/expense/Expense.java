package com.gs.thread.splitwise.expense;

import com.gs.thread.splitwise.User;
import com.gs.thread.splitwise.split.Split;

import java.util.List;

public abstract class Expense {

    private String id;
    private double amount;
    private User paidBy;
    private List<Split> splits;
    private ExpenseMetaData metadata;

    public Expense(double amount, User paidBy, List<Split> splits, ExpenseMetaData metadata) {
        this.amount = amount;
        this.paidBy = paidBy;
        this.splits = splits;
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public void setSplits(List<Split> splits) {
        this.splits = splits;
    }

    public ExpenseMetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(ExpenseMetaData metadata) {
        this.metadata = metadata;
    }

    public abstract boolean validate();
}
