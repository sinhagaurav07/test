package org.walmart.gs;

import java.util.Arrays;

public class MaxProfitJobScheduling {
    private class Job implements Comparable<Job> {
        private int start;
        private int end;
        private int profit;

        public Job(int start, int end, int profit) {
            this.start = start;
            this.end = end;
            this.profit = profit;
        }

        @Override
        public int compareTo(Job job) {
            return this.end - job.end;
        }
    }

    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {

        int n = startTime.length;
        Job[] jobs = new Job[n];
        int[] dp = new int[n + 1];

        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }

        Arrays.sort(jobs);

        for (int i = 1; i <= n; i++) {
            int last = i - 1;
            dp[i] = dp[i - 1];
            while (last >= 0 && jobs[i - 1].start < jobs[last].end) {
                last--;
            }
            dp[i] = Math.max(dp[i - 1], jobs[i - 1].profit + dp[last + 1]);
        }
        return dp[n];
    }

    public static void main(String[] args) {
        MaxProfitJobScheduling max = new MaxProfitJobScheduling();
        max.jobScheduling(new int[]{1, 2, 3, 3}, new int[]{3, 4, 5, 6}, new int[]{50, 10, 40, 70});
    }
}
