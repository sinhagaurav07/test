package com.leetcode.ds.misc;

import org.junit.Assert;
import org.junit.Test;

/**
 * You are climbing a staircase. It takes n steps to reach the top.
 *
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 *
 *
 *
 * Example 1:
 *
 * Input: n = 2
 * Output: 2
 * Explanation: There are two ways to climb to the top.
 * 1. 1 step + 1 step
 * 2. 2 steps
 *
 * Example 2:
 *
 * Input: n = 3
 * Output: 3
 * Explanation: There are three ways to climb to the top.
 * 1. 1 step + 1 step + 1 step
 * 2. 1 step + 2 steps
 * 3. 2 steps + 1 step
 *
 *
 * Constraints:
 *
 * 1 <= n <= 45
 *
 *
 */
public class ClimbingStairsTopDown {

    public int climbStairs(int n){
        if(n<=2){
            return n;
        }
        return climbStairs(n-1) + climbStairs(n-2);
    }

    @Test
    public void test1(){
        Assert.assertEquals("Success", 1, climbStairs(1));
    }
    @Test
    public void test2(){
        Assert.assertEquals("Success",2, climbStairs(2));
    }
    @Test
    public void test3(){
        Assert.assertEquals("Success",3, climbStairs(3));
    }
    @Test
    public void test4(){
        Assert.assertEquals("Success",5, climbStairs(4));
    }
}
