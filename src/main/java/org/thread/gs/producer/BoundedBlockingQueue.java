package org.thread.gs.producer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBlockingQueue {

    private Integer[] arr;
    private Lock lock;
    private Condition enCondition;
    private Condition deCondition;
    private int capacity = 0;
    private int enIndex = 0;
    private int deIndex = 0;
    private int count = 0;

    public BoundedBlockingQueue(int capacity) {
        this.arr = new Integer[capacity];
        this.capacity = capacity;
        this.lock = new ReentrantLock();
        this.enCondition = lock.newCondition();
        this.deCondition = lock.newCondition();
    }

    public void enqueue(int element) throws InterruptedException {
        lock.lock();
        try {
            if(count == capacity) {
                enCondition.await();
            }
            arr[enIndex] = element;
            System.out.println("producing: " + element);
            enIndex++;
            if(enIndex == capacity) {
                enIndex = 0;
            }
            count++;
            deCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public int dequeue() throws InterruptedException {
        lock.lock();
        Integer val = 0;
        try {
            if(count == 0) {
                deCondition.await();
            }
            val = arr[enIndex];
            System.out.println("consuming: " + val);
            deIndex++;
            if(deIndex == capacity) {
                deIndex = 0;
            }
            count--;
            enCondition.signal();
        } finally {
            lock.unlock();
        }
        return val;
    }

    public int size() {
        return count;
    }
}
