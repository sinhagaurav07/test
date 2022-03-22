package com.gs.thread.splitwise;

import com.gs.thread.splitwise.manager.ExpenseManager;
import com.gs.thread.splitwise.split.EqualSplit;
import com.gs.thread.splitwise.split.Split;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager();
        final User user = new User("u1", "User1", "gaurav@workat.tech", "9876543210");
        final User user1 = new User("u2", "User2", "sagar@workat.tech", "9876543210");
        final User user2 = new User("u3", "User3", "hi@workat.tech", "9876543210");
        final User user3 = new User("u4", "User4", "mock-interviews@workat.tech", "9876543210");
        expenseManager.addUser(user);
        expenseManager.addUser(user1);
        expenseManager.addUser(user2);
        //expenseManager.addUser(user3);

        //EqualSplit
        List<Split> splits = new ArrayList<>();
        splits.add(new EqualSplit(user));
        splits.add(new EqualSplit(user1));
        splits.add(new EqualSplit(user2));
        //splits.add(new EqualSplit(user3));
        expenseManager.addExpense(ExpenseType.EQUAL, 100, "u1", splits, null);
        expenseManager.showBalance("u2");
    }
}
