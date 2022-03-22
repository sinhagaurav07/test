package org.delphix.gs;

public class Solution {
    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 6, 4, 3};
        int maxIncreasingSub = findLIS(array);
        int diff = array.length - maxIncreasingSub;
        if( diff == 0){
            System.out.println(0);
        } else {
            System.out.println(array.length - maxIncreasingSub - 1);
        }
    }

    public static int findLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int maxAns = 1;
        for (int i = 1; i < dp.length; i++) {
            int maxVal = 0;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    maxVal = Math.max(maxVal, dp[j]);
                }
            }
            dp[i] = maxVal + 1;
            maxAns = Math.max(maxAns, dp[i]);
        }
        return maxAns;
    }
}
