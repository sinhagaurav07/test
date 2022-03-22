package com.leetcode.ds.misc;

import apple.laf.JRSUIConstants;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile, adjacent houses have a security system connected, and it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [2,3,2]
 * Output: 3
 * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2), because they are adjacent houses.
 * Example 2:
 *
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 * Example 3:
 *
 * Input: nums = [1,2,3]
 * Output: 3
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 1000
 *
 */

public class HouseRobber2BottomUp {

    public int rob(int[] nums) {
        //Keep first house and skip last
        //skip first house and keep last
        if(nums.length==0){
            return 0;
        }else if(nums.length==1){
            return nums[0];
        }else if(nums.length==2){
            return Math.max(nums[0], nums[1]); //it should be 0
        }else{
            return Math.max(rob_helper(nums, 0, nums.length -2), rob_helper(nums, 1, nums.length-1));
        }
    }

    public int rob_helper(int[] nums, int start, int end){
        int[] dp = new int[nums.length];


        dp[start] = nums[start];
        dp[start+1] = Math.max(nums[start], nums[start+1]);

        for(int i=start+2; i<= end;i++){
            dp[i] = Math.max(dp[i - 1], nums[i] + dp[i - 2]);
        }
        if(start==0) {
            return dp[nums.length-2];
        }
        else{
            return dp[nums.length-1];
        }
    }

    @Test
    public void test1(){
        int[] nums = {2,3,2};
        Assert.assertEquals("Sucess", 3, rob(nums));
    }

    @Test
    public void test2(){
        int[] nums = {1,2,3,1};
        Assert.assertEquals("Sucess", 4, rob(nums));
    }

    @Test
    public void test3(){
        int[] nums = {1,2,3};
        long startTime = System.currentTimeMillis();
        int res = rob(nums);
        System.out.println("Time taken : " + (System.currentTimeMillis() - startTime));
        Assert.assertEquals("Sucess", 3, res);

    }


}
