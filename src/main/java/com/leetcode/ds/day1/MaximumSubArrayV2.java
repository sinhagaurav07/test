package com.leetcode.ds.day1;

public class MaximumSubArrayV2 {

    public static int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int sum=0;
        int startIndex=0;
        int endIndex = 0;

        for(int i=0; i<nums.length ; i++){
            sum += nums[i];
            max = Math.max(sum, max);

            if(sum < 0) {
                sum=0;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        //int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        int[] nums = {-2,-3,-5};
        //int[] nums = {1};
        //int[] nums = {5,4,-1,7,8};
        System.out.println(maxSubArray(nums));
    }
}
