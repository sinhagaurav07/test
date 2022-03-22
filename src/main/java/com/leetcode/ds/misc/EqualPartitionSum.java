package com.leetcode.ds.misc;

import org.junit.Assert;
import org.junit.Test;

/**
 * Given a non-empty array nums containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,5,11,5]
 * Output: true
 * Explanation: The array can be partitioned as [1, 5, 5] and [11].
 * Example 2:
 *
 * Input: nums = [1,2,3,5]
 * Output: false
 * Explanation: The array cannot be partitioned into equal sum subsets.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 200
 * 1 <= nums[i] <= 100
 */
public class EqualPartitionSum {
    public boolean canPartition(int[] nums) {
        int totalSum = 0;
        for(int i=0; i< nums.length; i++)
            totalSum += nums[i];

        if(totalSum % 2 ==0){
            return (isSubsetSum(nums, totalSum/2));
        }
        return false;
    }

    private boolean isSubsetSum(int set[], int sum){
        boolean[][] dp = new boolean[set.length+1][sum+1];

        for(int i=0; i< dp[0].length ; i++){
            dp[0][i] = false;
        }
        for(int i=0; i< dp.length ; i++){
            dp[i][0] = true;
        }

        for(int i=1; i<dp.length;i++){
            for(int j=1; j< dp[0].length; j++){
                if(set[i-1] > j){
                    dp[i][j] = dp[i-1][j];
                }else{
                    dp[i][j] = dp[i-1][j-set[i-1]] || dp[i-1][j];
                }
            }
        }

        return dp[set.length][sum];
    }

    @Test
    public void test1(){
        int nums[] = new int[]{1,5,11,5};
        Assert.assertEquals("Success", true, canPartition(nums));
    }
    @Test
    public void test2(){
        int nums[] = new int[]{1,2,3,5};
        Assert.assertEquals("Success", false, canPartition(nums));
    }
}
