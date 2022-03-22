package com.leetcode.ds.misc;

import org.junit.Assert;
import org.junit.Test;

public class ClimbingStairsBottomUp {

    public int climbStairs(int n){
        int dp[] = new int[n+1];
        if(n<=2){
            return n;
        }
        dp[0]=0;dp[1]=1;dp[2]=2;
        for(int i=3; i<=n ;i++){
            dp[i] = dp[i-1] + dp[i-2];
        }

        return dp[n];
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
