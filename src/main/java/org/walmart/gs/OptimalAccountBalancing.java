package org.walmart.gs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class OptimalAccountBalancing {

    public int minTransfers(int[][] transactions) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] transaction : transactions) {
            map.put(transaction[0], map.getOrDefault(transaction[0], 0) - transaction[2]);
            map.put(transaction[1], map.getOrDefault(transaction[1], 0) + transaction[2]);
        }
        return balance(0, new ArrayList<>(map.values()));
    }

    private int balance(int start, List<Integer> list) {
        if (start == list.size() - 2) return list.get(list.size() - 2) == 0 ? 0 : 1;
        if (list.get(start) == 0) return balance(start + 1, list);
        int res = Integer.MAX_VALUE;
        for (int i = start + 1; i < list.size(); i++) {
            int balance = list.get(i);
            if (balance * list.get(start) < 0) {
                list.set(i, balance + list.get(start));
                res = Math.min(res, 1 + balance(start + 1, list));
                list.set(i, balance);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] c = {{0,1,10}, {2,0,5}};
        OptimalAccountBalancing optimalAccountBalancing = new OptimalAccountBalancing();
        optimalAccountBalancing.minTransfers(c);
    }
}
