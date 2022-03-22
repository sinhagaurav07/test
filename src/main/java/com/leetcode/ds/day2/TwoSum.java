package com.leetcode.ds.day2;

import java.util.HashSet;
import java.util.Set;

public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        Set<Integer> twoComplement = new HashSet<>();

        for (int i : nums){
            if(twoComplement.contains(target - i))
                return new int[]{i, (target -1)};
            else
                twoComplement.add(target - i);
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums = {2,7,11,15};
        //int[] nums = {-2,-3,-5};
        //int[] nums = {1};
        //int[] nums = {5,4,-1,7,8};
        int target=9;
        System.out.println(twoSum(nums, target));
    }
}
