package com.leetcode.ds.misc;

import org.junit.Assert;
import org.junit.Test;

/**
 * There is a row of n houses, where each house can be painted one of three colors: red, blue, or green. The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.
 *
 * The cost of painting each house with a certain color is represented by an n x 3 cost matrix costs.
 *
 * For example, costs[0][0] is the cost of painting house 0 with the color red; costs[1][2] is the cost of painting house 1 with color green, and so on...
 * Return the minimum cost to paint all houses.
 *
 *
 *
 * Example 1:
 *
 * Input: costs = [[17,2,17],[16,16,5],[14,3,19]]
 * Output: 10
 * Explanation: Paint house 0 into blue, paint house 1 into green, paint house 2 into blue.
 * Minimum cost: 2 + 5 + 3 = 10.
 * Example 2:
 *
 * Input: costs = [[7,6,2]]
 * Output: 2
 *
 *
 * Constraints:
 *
 * costs.length == n
 * costs[i].length == 3
 * 1 <= n <= 100
 * 1 <= costs[i][j] <= 20
 *
 */
public class PaintHouse {
    public int minCost(int[][] costs) {
        int[][] dp = new int[costs.length+1][costs[0].length+1];

        if(costs.length == 1){
            return Math.min(costs[0][0], Math.min(costs[0][1], costs[0][2]));
        }

        for(int i=0; i< costs.length; i++){
            dp[i][0] = 0;
        }

        for(int i=0; i< costs.length; i++){
            dp[0][i] = 0;
        }
        dp[1][1] = costs[0][0];dp[1][2] = costs[0][1];dp[1][3] = costs[0][2];
        for(int i=2; i<= costs.length;i++){
            for(int j=1; j<=3;j++){
                if(j==1) {
                    dp[i][j] = costs[i-1][j-1] + Math.min(dp[i-1][2],dp[i-1][3]);
                }else if(j==2) {
                    dp[i][j] = costs[i-1][j-1] + Math.min(dp[i-1][1],dp[i-1][3]);
                }else {
                    dp[i][j] = costs[i-1][j-1] + Math.min(dp[i-1][1],dp[i-1][2]);
                }
            }
        }
        System.out.println();
        for(int i=0;i<dp.length;i++){
            for (int j=0; j< dp[0].length; j++){
                System.out.print(dp[i][j] + "\t");
            }
            System.out.println();
        }

        return Math.min(dp[costs.length][1],Math.min(dp[costs.length][2],dp[costs.length][3]));
    }

    @Test
    public void test1(){
        int[][] costs = {{17,2,17},{16,16,5},{14,3,19}};
        Assert.assertEquals("Success", 10, minCost(costs));
    }

    @Test
    public void test2(){
        int[][] costs = {{3,5,3},{6,17,6},{7,13,18},{9,10,18}};
        Assert.assertEquals("Success", 26, minCost(costs));
    }
}
