package com.leetcode.ds.misc;

import org.junit.Assert;
import org.junit.Test;

/**
 * Given an integer array nums and an integer k, return true if it is possible to divide this array into k non-empty subsets whose sums are all equal.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [4,3,2,3,5,2,1], k = 4
 * Output: true
 * Explanation: It is possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
 * Example 2:
 *
 * Input: nums = [1,2,3,4], k = 3
 * Output: false
 *
 *
 * Constraints:
 *
 * 1 <= k <= nums.length <= 16
 * 1 <= nums[i] <= 104
 * The frequency of each element is in the range [1, 4].
 */
public class KEqualSumSubset {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int totalSum = 0;

        for(int i=0; i<nums.length;i++){
            totalSum+=nums[i];
        }

        if(totalSum % 4 != 0){
            return false;
        }
        boolean[] visited = new boolean[nums.length];
        return canPartitionTargetSum(nums, 0,0,k,totalSum/4,visited);
    }

    private boolean canPartitionTargetSum(int set[], int count, int currentSum, int k, int targetSum, boolean[] visited){
        //k-1 partition is possible then last one is by default possible
        if(count == k-1){
            return true;
        }
        //this set is not possible as its sum exceeds targetSum
        if(currentSum > targetSum){
            return false;
        }
        //this set is accepted and start checking next possible set
        if(currentSum == targetSum){
            return canPartitionTargetSum(set, count+1, 0, k, targetSum, visited);
        }
        //pick items to form set
        for(int i=0; i< set.length; i++){
            if(!visited[i]){
                visited[i] = true;
                if(canPartitionTargetSum(set, count+1, 0, k, targetSum, visited)){
                    return true;
                }
                visited[i] = false;
            }
        }
        return false;
    }

    @Test
    public void test1(){
        int[] nums = new int[]{4,3,2,3,5,2,1};
        Assert.assertEquals("Success", true, canPartitionKSubsets(nums, 4));
    }

    @Test
    public void test2(){
        int[] nums = new int[]{1,2,3,4};
        Assert.assertEquals("Success", false, canPartitionKSubsets(nums, 4));
    }
}
