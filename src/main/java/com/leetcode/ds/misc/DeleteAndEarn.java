package com.leetcode.ds.misc;

import java.util.Arrays;

/**
 * You are given an integer array nums. You want to maximize the number of points you get by performing the following operation any number of times:
 *
 * Pick any nums[i] and delete it to earn nums[i] points. Afterwards, you must delete every element equal to nums[i] - 1 and every element equal to nums[i] + 1.
 * Return the maximum number of points you can earn by applying the above operation some number of times.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [3,4,2]
 * Output: 6
 * Explanation: You can perform the following operations:
 * - Delete 4 to earn 4 points. Consequently, 3 is also deleted. nums = [2].
 * - Delete 2 to earn 2 points. nums = [].
 * You earn a total of 6 points.
 * Example 2:
 *
 * Input: nums = [2,2,3,3,3,4]
 * Output: 9
 * Explanation: You can perform the following operations:
 * - Delete a 3 to earn 3 points. All 2's and 4's are also deleted. nums = [3,3].
 * - Delete a 3 again to earn 3 points. nums = [3].
 * - Delete a 3 once more to earn 3 points. nums = [].
 * You earn a total of 9 points.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 2 * 104
 * 1 <= nums[i] <= 104
 *
 */
public class DeleteAndEarn {

    public static int deleteAndEarn(int[] nums) {
        int[] frequency = new int[10001];

        for(int i=0; i< nums.length;i++){
            frequency[nums[i]]++;
        }
        Arrays.stream(frequency).forEach(System.out::print);
        System.out.println();

        int previousIncludeEarn =0;
        int previousExcludeEarn=0;

        int currentIncludeEarn=0;
        int currentExcludeEarn=0;

        for(int i=0; i<frequency.length;i++){
            System.out.println("Checking -" + i + "\t If Previous Included, Earning till now -" + currentIncludeEarn + "\t If Previous Excluded, Earning till now - " + currentExcludeEarn);

            currentIncludeEarn = previousExcludeEarn + i*frequency[i];
            currentExcludeEarn = Math.max(previousIncludeEarn, previousExcludeEarn);
            System.out.println("Checking -" + i + "\t If Included, Earning So Far -" + currentIncludeEarn + "\t If Excluded, Earning so far - " + currentExcludeEarn);

            previousIncludeEarn = currentIncludeEarn;
            previousExcludeEarn = currentExcludeEarn;


        }

        return Math.max(previousIncludeEarn, previousExcludeEarn);
    }

    public static int deleteAndEarn1(int[] nums) {
        int[] frequency = new int[105];
        int[] dp = new int[105];
        for(int i=0; i< nums.length;i++){
            frequency[nums[i]]++;
        }
        Arrays.stream(frequency).forEach(System.out::print);
        System.out.println();

        dp[0] = 0;
        dp[1] = Math.max(0,1*frequency[1]);

        for(int i=2; i< dp.length; i++){
            dp[i] = Math.max(dp[i-2] + i*frequency[i], Math.max(dp[i-1], dp[i-2]));
        }
        System.out.println();
        Arrays.stream(dp).forEach(x -> System.out.print(" "+x));
        System.out.println();
        return dp[dp.length-1];
    }

    public static void main(String[] args) {
        //int[] nums = new int[]{3,4,2};
        //int[] nums = new int[]{2,2,3,3,3,4};
        //int[] nums = new int[]{12,32,93,17,100,72,40,71,37,92,58,34,29,78,11,84,77,90,92,35,12,5,27,92,91,23,65,91,85,14,42,28,80,85,38,71,62,82,66,3,33,33,55,60,48,78,63,11,20,51,78,42,37,21,100,13,60,57,91,53,49,15,45,19,51,2,96,22,32,2,46,62,58,11,29,6,74,38,70,97,4,22,76,19,1,90,63,55,64,44,90,51,36,16,65,95,64,59,53,93};
        //int[] nums = new int[]{8,6,1,7,5,8,9,5,1,1,7,3,5,8,5,2,9,6,9,10,10,10,4,4,8,8,4,3,6,7,4,5,1,7,1,5,1,6,7,9,6,4,8,7,9,7,8,2,9,5};
        //int[] nums = new int[]{1,6,3,3,8,4,8,10,1,3};
        int[] nums = new int[]{8,3,4,7,6,6,9,2,5,8,2,4,9,5,9,1,5,7,1,4};
        System.out.println(deleteAndEarn1(nums));
    }
}
