package org.thread.gs.producer;

public class Consumer implements Runnable {

    private BoundedBlockingQueue blockingQueue;

    public Consumer(BoundedBlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            while(true) {
                blockingQueue.dequeue();
            }
        } catch (InterruptedException exp) {
            exp.printStackTrace();
        }
    }
}
