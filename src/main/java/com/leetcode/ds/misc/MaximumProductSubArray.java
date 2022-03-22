package com.leetcode.ds.misc;

import org.junit.Assert;
import org.junit.Test;

public class MaximumProductSubArray {

    private static int maxSoFar = 0;
    private static int localMin = 0;
    private static int localMax = 0;
    static int maxSubarrayProduct(int arr[])
    {
        if(arr == null|| arr.length==0){
            return -1;
        }
        if(arr.length == 1){
            return arr[0];
        }

        maxSoFar = arr[0];
        localMax = arr[0];
        localMin = arr[0];

        for(int i=1; i< arr.length ; i++){
            int temp =  Math.max(Math.max(arr[i],localMax*arr[i]),localMin*arr[i]);
            localMin = Math.min(arr[i], Math.min(localMin*arr[i], localMax*arr[i]));
            localMax = temp;
            maxSoFar = Math.max(maxSoFar, localMax);
        }
        return  maxSoFar;
    }

    @Test
    public void maxProdArrayTest1(){
        int[] arr = {6,-3,-10,0,2};
        Assert.assertEquals("Success", 180, maxSubarrayProduct(arr));
    }
    @Test
    public void maxProdArrayTest2(){
        int[] arr = {-1, -3, -10, 0, 60};
        Assert.assertEquals("Success", 60, maxSubarrayProduct(arr));
    }
    @Test
    public void maxProdArrayTest3(){
        int[] arr = {-2, -40, 0, -2, -3};
        Assert.assertEquals("Success", 80, maxSubarrayProduct(arr));
    }
}
