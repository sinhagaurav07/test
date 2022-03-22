package com.gs.thread.splitwise.split;

import com.gs.thread.splitwise.User;

public class ExactSplit extends Split {

    public ExactSplit(User user, double amount) {
        super(user);
        this.amount = amount;
    }
}
