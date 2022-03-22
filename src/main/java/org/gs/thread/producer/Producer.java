package org.gs.thread.producer;

import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {

    private BlockingQueue<String> queue;
    private long delay;

    public Producer(BlockingQueue<String> queue, long delay) {
        this.queue = queue;
        this.delay = delay;
    }

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
        this.delay = 50;
    }

    @Override
    public void run() {
        for(int i = 0; i < 100; i++) {
            try {
                String random = String.valueOf(ThreadLocalRandom.current().nextInt(100));
                queue.produce(random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
