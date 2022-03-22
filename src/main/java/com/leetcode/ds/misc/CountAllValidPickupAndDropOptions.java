package com.leetcode.ds.misc;

/**
 * Given n orders, each order consist in pickup and delivery services.
 *
 * Count all valid pickup/delivery possible sequences such that delivery(i) is always after of pickup(i).
 *
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 1
 * Output: 1
 * Explanation: Unique order (P1, D1), Delivery 1 always is after of Pickup 1.
 * Example 2:
 *
 * Input: n = 2
 * Output: 6
 * Explanation: All possible orders:
 * (P1,P2,D1,D2), (P1,P2,D2,D1), (P1,D1,P2,D2), (P2,P1,D1,D2), (P2,P1,D2,D1) and (P2,D2,P1,D1).
 * This is an invalid order (P1,D2,P2,D1) because Pickup 2 is after of Delivery 2.
 * Example 3:
 *
 * Input: n = 3
 * Output: 90
 *
 *
 * Constraints:
 *
 * 1 <= n <= 500
 *
 */
public class CountAllValidPickupAndDropOptions {
    public static int countOrders(int n) {
        double res = 1;
        if(n<=1){
            return n;
        }
        for(int i=2*n; i>0 ;i=i-2){
            res=(res*(i*(i-1)/2))%1000000007;
        }
        return new Double(res).intValue();
    }

    public static void main(String[] args) {
        System.out.println(countOrders(1));
        System.out.println(countOrders(2));
        System.out.println(countOrders(3));
        System.out.println(countOrders(8));
    }
}
