package com.leetcode.ds.misc;


import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class StackUsingQueueV2 {
    private static Queue<Integer> q1 = new LinkedList<>();
    private static Queue<Integer> q2 = new LinkedList<>();

    public static void main(String[] args) {
        push(1);push(2);push(3);push(4);
        pop();pop();pop();pop();pop();
    }

    public static void push(int n){
        q1.add(n);
        System.out.println("Push :" + n);
    }
    public static int pop(){
        if(q1.isEmpty())
                return -1;

        while(q1.size() > 1)
        {
            q2.add(q1.remove());
        }
        int res = q1.remove();
        Queue<Integer> temp = q2;
        q2=q1;
        q1=temp;

        System.out.println("Pop : " + res);
        return res;
    }
    @Test
    public void stackTest(){
        push(1);push(3);push(2);
        Assert.assertEquals("Success",2, pop());
        Assert.assertEquals("Success",3, pop());
    }
}
