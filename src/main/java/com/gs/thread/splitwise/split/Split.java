package com.gs.thread.splitwise.split;

import com.gs.thread.splitwise.User;

public abstract class Split {

    protected double amount;
    private User user;

    public Split(User user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
