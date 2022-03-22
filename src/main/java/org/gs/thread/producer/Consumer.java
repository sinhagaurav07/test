package org.gs.thread.producer;

public class Consumer implements Runnable {

    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                final String number = queue.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
