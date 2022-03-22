package com.leetcode.ds.misc;

import org.junit.Assert;
import org.junit.Test;

/**
 * Given a set of non-negative integers, and a value sum, determine if there is a subset of the given set with sum equal to given sum.
 *
 * Example:
 *
 * Input: set[] = {3, 34, 4, 12, 5, 2}, sum = 9
 * Output: True
 * There is a subset (4, 5) with sum 9.
 *
 * Input: set[] = {3, 34, 4, 12, 5, 2}, sum = 30
 * Output: False
 * There is no subset that add up to 30.
 */
public class SubsetSum {

    static boolean isSubsetSum(int set[], int sum){
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
        System.out.println();
        for(int i=0; i<dp.length;i++){
            for (int j=0;j<dp[0].length;j++){
                System.out.print(dp[i][j] + "\t");
            }
            System.out.println();
        }
        return dp[set.length][sum];
    }

    @Test
    public void test1(){
        int nums[] = new int[]{3,34,4,12,5,2};
        Assert.assertEquals("Success", true, isSubsetSum(nums, 9));
    }
    @Test
    public void test2(){
        int nums[] = new int[]{3,34,4,12,5,2};
        Assert.assertEquals("Success", false, isSubsetSum(nums, 30));
    }
}
