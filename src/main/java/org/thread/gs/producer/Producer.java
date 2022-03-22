package org.thread.gs.producer;

import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {

    private BoundedBlockingQueue blockingQueue;
    public Producer(BoundedBlockingQueue blockingQueue){
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            while(true){
                for (int i = 0; i < 100; i++) {
                    blockingQueue.enqueue(ThreadLocalRandom.current().nextInt(100));
                }
            }
        } catch (InterruptedException exp) {
            exp.printStackTrace();
        }
    }
}
