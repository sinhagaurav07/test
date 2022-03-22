package com.gs.thread.splitwise.split;

import com.gs.thread.splitwise.User;

public class PercentageSplit extends Split {

    private double percent;

    public PercentageSplit(User user, double percent) {
        super(user);
        this.percent = percent;
    }

    public double getPercent() {
        return percent;
    }
}
