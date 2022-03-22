package com.gs.thread.producer;

import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class Producer<T> implements Runnable {

    private Queue<T> queue;
    private long delay;

    public Producer(Queue<T> queue, long delay){
        this.queue = queue;
        this.delay = delay;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try{
                System.out.println("Producing value : " + Integer.valueOf(i));
                queue.offer((T)Integer.valueOf(i));
                Thread.sleep(delay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
