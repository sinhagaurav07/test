package com.leetcode.ds.misc;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

/**
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 * Example 2:
 *
 * Input: nums = [2,7,9,3,1]
 * Output: 12
 * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
 * Total amount you can rob = 2 + 9 + 1 = 12.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 400
 *
 */

public class HouseRobber {

    public int rob(int[] nums) {
        Map<Integer, Integer> memo = new HashMap<>();
        return getMax(nums, memo, nums.length-1);
    }
    private int getMax(int[] nums, Map<Integer, Integer> memo, int i){

        if(memo.containsKey(i)){
            return memo.get(i);
        }

        if(i==0) return nums[0];
        if(i==1) return Math.max(nums[0], nums[1]);

        memo.put(i, Math.max(nums[i]+getMax(nums, memo,i-2), getMax(nums, memo,i-1)));
        return memo.get(i);
    }

    @Test
    public void test1(){
        int[] nums = {1,2,3,1};
        Assert.assertEquals("Sucess", 4, rob(nums));
    }

    @Test
    public void test2(){
        int[] nums = {2,7,9,3,1};
        Assert.assertEquals("Sucess", 12, rob(nums));
    }

    @Test
    public void test3(){
        int[] nums = {114,117,207,117,235,82,90,67,143,146,53,108,200,91,80,223,58,170,110,236,81,90,222,160,165,195,187,199,114,235,197,187,69,129,64,214,228,78,188,67,205,94,205,169,241,202,144,240};
        long startTime = System.currentTimeMillis();
        int res = rob(nums);
        System.out.println("Time taken : " + (System.currentTimeMillis() - startTime));
        Assert.assertEquals("Sucess", 4173, res);

    }


}
