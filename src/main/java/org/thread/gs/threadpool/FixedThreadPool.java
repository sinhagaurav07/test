package org.thread.gs.threadpool;

import java.util.LinkedList;
import java.util.Queue;

public class FixedThreadPool {
    private int capacity;
    private Queue<Runnable> queue;
    private WorkerThread[] workerThreads;

     public FixedThreadPool(int capacity) {
        this.capacity = capacity;
        queue = new LinkedList<>();
        workerThreads = new WorkerThread[capacity];
         for (int i = 0; i < workerThreads.length; i++) {
             workerThreads[i] = new WorkerThread();
             workerThreads[i].start();
         }
    }

    public void submitTasks(Runnable runnable){
         synchronized (queue){
             queue.offer(runnable);
             queue.notify();
         }
    }

    private class WorkerThread extends Thread {

        @Override
        public void run() {
            Runnable task = null;
            while(true) {
                synchronized (queue){
                    while(queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    task = queue.poll();
                }
                task.run();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void shutDown(){
        for (int i = 0; i < capacity; i++) {
            workerThreads[i] = null;
        }
    }

}
