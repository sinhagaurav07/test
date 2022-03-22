package com.gs.thread.producer;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        Queue<String> queue = new ArrayBlockingQueue<String>(5);
        final Producer<String> producer = new Producer(queue, 5);
        final Consumer<String> consumer = new Consumer<>(queue, 30);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(producer);

        ConsumerGroup consumerGroup = new ConsumerGroup(5, queue, 30, executorService);

    }
}
