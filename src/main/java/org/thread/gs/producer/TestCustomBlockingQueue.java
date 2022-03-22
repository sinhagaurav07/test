package org.thread.gs.producer;

public class TestCustomBlockingQueue {

    public static void main(String[] args) throws InterruptedException {
        CustomBlockingQueue customBlockingQueue = new CustomBlockingQueue(10);
        BoundedBlockingQueue blockingQueue = new BoundedBlockingQueue(10);
        Thread pThread = new Thread(new Producer(blockingQueue));
        Thread cThread = new Thread(new Consumer(blockingQueue));
        pThread.start();
        cThread.start();
    }
}
