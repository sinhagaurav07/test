package org.thread.gs.mergesort;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class MergeSort {

    public static void main(String[] args) {
        int arr[] = new int[100000];
        Random rand = new Random();
        for (int j = 0; j < 100000; j++) {
            arr[j] = rand.nextInt(1000000);
        }
        ForkJoinTask<?> task = new MergeSortTask(arr,0, arr.length - 1);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
        System.out.println("completed");
    }
}
