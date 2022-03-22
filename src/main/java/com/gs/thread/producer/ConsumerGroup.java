package com.gs.thread.producer;

import java.util.Queue;
import java.util.concurrent.ExecutorService;

public class ConsumerGroup<T> {

    private int subscribers;
    private Queue<T> queue;
    private long delay;
    private ExecutorService executorService;

    public ConsumerGroup(int subscribers, Queue<T> queue, long delay, ExecutorService executorService) {
        this.subscribers = subscribers;
        this.queue = queue;
        this.delay = delay;
        this.executorService = executorService;
        init();
    }

    private void init(){
        for (int i = 0; i < subscribers; i++) {
            final Consumer<String> consumer = new Consumer(queue, 30);
            executorService.submit(consumer);
        }
    }
}
