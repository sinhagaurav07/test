package com.leetcode.ds.programmingskill1;

/**
 * Given an integer number n, return the difference between the product of its digits and the sum of its digits.
 *
 *
 * Example 1:
 *
 * Input: n = 234
 * Output: 15
 * Explanation:
 * Product of digits = 2 * 3 * 4 = 24
 * Sum of digits = 2 + 3 + 4 = 9
 * Result = 24 - 9 = 15
 * Example 2:
 *
 * Input: n = 4421
 * Output: 21
 * Explanation:
 * Product of digits = 4 * 4 * 2 * 1 = 32
 * Sum of digits = 4 + 4 + 2 + 1 = 11
 * Result = 32 - 11 = 21
 *
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 */
public class Day2SubstractProductAndSumOfdigits {
    public static int subtractProductAndSum(int n) {
        int digit, sum=0, product=1;
        while (n>0){
            digit = n%10;
            sum+=digit;
            product*=digit;
            n=n/10;
        }
        return (product - sum);
    }

    public static void main(String[] args) {
        System.out.println(subtractProductAndSum(234));
        System.out.println(subtractProductAndSum(4421));
    }
}
