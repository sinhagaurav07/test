package com.leetcode.ds.programmingskill1;

/**
 * Given two non-negative integers low and high. Return the count of odd numbers between low and high (inclusive).
 *
 *
 *
 * Example 1:
 *
 * Input: low = 3, high = 7
 * Output: 3
 * Explanation: The odd numbers between 3 and 7 are [3,5,7].
 * Example 2:
 *
 * Input: low = 8, high = 10
 * Output: 1
 * Explanation: The odd numbers between 8 and 10 are [9].
 *
 *
 * Constraints:
 *
 * 0 <= low <= high <= 10^9
 *
 */
public class Day1CountOddNumbersInInterval {

    public static int countOdds(int low, int high) {
        int count=0;
        for(int i=low; i<= high;){
            if(i%2!=0){
                count++;
                i+=2;
            }else{
                i++;
            }
        }
        return count;
    }
    public static int countOdds1(int low, int high) {
        if(low%2==0){
            if(high%2==0){
                return (high-low)/2;
            }else{
                return (high-low)/2+1;
            }
        }else{
            return (high-low)/2+1;
        }
    }

    public static void main(String[] args) {
        //int low=3, high=7;
        int low=14, high=17;
        //int low=8, high=10;
        //int low=3, high=8;
        //int low=0, high=1000000000;
        System.out.println(countOdds1(low, high));
    }

}
