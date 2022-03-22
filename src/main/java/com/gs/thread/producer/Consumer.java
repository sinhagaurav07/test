package com.gs.thread.producer;

import java.util.Queue;

public class Consumer<T> implements Runnable {

    private Queue<T> queue;
    private long delay;
    Object lock = new Object();

    public Consumer(Queue<T> queue, long delay) {
        this.queue = queue;
        this.delay = delay;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                try {
                    Thread.sleep(delay);
                    if (!queue.isEmpty()) {
                        T t = queue.poll();
                        System.out.println("Consuming value : " + t);

                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
