package com.leetcode.ds.misc;

import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

/*
Enqueue : O(n)
Dequeue : O(1)

 */
public class QueueUsingStackV2 {

    private static Stack<Integer> s1 = new Stack<>();
    private static Stack<Integer> s2 = new Stack<>();

    public static void main(String[] args) {
        enqueue(1);enqueue(2);enqueue(3);enqueue(4);
        System.out.println("Dequeue:" + dequeue());
        System.out.println("Dequeue:" + dequeue());
        System.out.println("Dequeue:" + dequeue());
        System.out.println("Dequeue:" + dequeue());
        System.out.println("Dequeue:" + dequeue());
    }

    public static void enqueue(int n){

        while(!s1.isEmpty()){
            s2.push(s1.pop());
        }
        s1.push(n);
        while(!s2.isEmpty()){
            s1.push(s2.pop());
        }
        System.out.println("Enqueued:"+n);
    }
    public static int dequeue(){
        if (s1.isEmpty())
                return -1;
        int n = s1.pop();
        System.out.println("Dequeued:"+n);
        return n;
    }

    @Test
    public void queueTest(){
        enqueue(1);
        Assert.assertEquals("Success", 1, dequeue());

        enqueue(6);enqueue(2);enqueue(3);
        Assert.assertEquals("Success", 6, dequeue());
    }
}
