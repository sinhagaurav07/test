package org.walmart.gs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ConsolidateSolutions {
    public static void main(String[] args) {
        List<Integer> used = new ArrayList<>();
        used.add(3);
        used.add(2);
        used.add(1);
        used.add(3);
        used.add(1);

        List<Integer> totalCapacity = new ArrayList<>();
        totalCapacity.add(3);
        totalCapacity.add(5);
        totalCapacity.add(3);
        totalCapacity.add(5);
        totalCapacity.add(5);

        System.out.println(minPartition(used, totalCapacity));
    }

    public static int minPartition(List<Integer> used, List<Integer> totalCapacity) {
        int minPartition = 0;

        List<Integer> sortedTotalCapacity = totalCapacity.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        int totalNumber = 0;
        for (int i : used) {
            totalNumber += i;
        }

        int counterTotal = 0;
        int currTotal;
        while (totalNumber > 0) {
            currTotal = sortedTotalCapacity.get(counterTotal);
            totalNumber = totalNumber - currTotal;

            if (totalNumber > 0) {
                minPartition++;
                counterTotal++;
                continue;
            }
            minPartition++;
        }

        return minPartition;
    }
}
