package com.leetcode.ds.programmingskill1;

import java.util.HashMap;
import java.util.Map;

/**
 * Write an algorithm to determine if a number n is happy.
 *
 * A happy number is a number defined by the following process:
 *
 * Starting with any positive integer, replace the number by the sum of the squares of its digits.
 * Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
 * Those numbers for which this process ends in 1 are happy.
 * Return true if n is a happy number, and false if not.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 19
 * Output: true
 * Explanation:
 * 1^2 + 9^2 = 82
 * 8^2 + 2^2 = 68
 * 6^2 + 8^2 = 100
 * 1^2 + 0^2 + 0^2 = 1
 * Example 2:
 *
 * Input: n = 2
 * Output: false
 *
 *
 * Constraints:
 *
 * 1 <= n <= 2^31 - 1
 */
public class Day4HappyNumber {

    public static boolean isHappy(int n) {
        boolean isHappy = false;
        int sum=0, digit=0;
        Map<Integer, Integer> nCounter = new HashMap<>();
        nCounter.put(n,1);
        while (n > 0){
            digit = n%10;
            sum+=digit*digit;
            n/=10;

            if(n==0 && sum==1){
                return true;
            }else if(n==0 && sum > 1){
                n=sum;
                sum=0;
                nCounter.put(n, nCounter.get(n)==null?1:nCounter.get(n)+1);
                if(nCounter.containsKey(n) && nCounter.get(n) > 2){
                    return false;
                }
            }
        }
        return isHappy;
    }

    public static void main(String[] args) {
       System.out.println(isHappy(19));
        //System.out.println(isHappy(2));
    }
}
