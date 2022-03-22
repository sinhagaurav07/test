package com.leetcode.ds.misc;

import org.junit.Assert;
import org.junit.Test;

public class ContainerWithMostWater {
    public static void main(String[] args) {
        int[] height={1,8,6,2,5,4,8,3,7};

    }

    public static int maxArea(int[] height){
        int maxArea = Integer.MIN_VALUE;
        for(int i=0, j=height.length-1; j>i ; ){
            int area = Math.min(height[i], height[j]) * (j-i);
            if (area > maxArea)
                    maxArea = area;
            if(height[i] < height[j]){
                i++;
            }else {
                j--;
            }
        }
        return maxArea;
    }

    @Test
    public void containerWithMostWaterTest(){
        int[] height={1,8,6,2,5,4,8,3,7};
        Assert.assertEquals("Success" , 49, maxArea(height));

        int[] height1={1,1};
        Assert.assertEquals("Success" , 1, maxArea(height1));
    }
}
