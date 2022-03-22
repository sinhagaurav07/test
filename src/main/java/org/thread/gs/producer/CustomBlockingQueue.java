package org.thread.gs.producer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomBlockingQueue {

    private int size;
    private Lock lock;
    private Condition producerCond;
    private Condition consumerCond;
    private Object[] array;
    private int count = 0;
    private int produceIndex = 0;
    private int consumeIndex = 0;

    public CustomBlockingQueue(int size) {
        this.size = size;
        this.lock = new ReentrantLock();
        this.producerCond = lock.newCondition();
        this.consumerCond = lock.newCondition();
        this.array = new Object[size];
    }

    public void produce(Object object) throws InterruptedException {
        lock.lock();
        try {
            while(count == size) {
                producerCond.await();
            }
            array[produceIndex] = object;
            System.out.println("producing: " + object);
            produceIndex++;
            if(produceIndex == size) produceIndex = 0;
            count++;
            consumerCond.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object consume() throws InterruptedException {
        lock.lock();
        Object object = null;
        try {
            while(count == 0) {
                consumerCond.await();
            }
            object = array[consumeIndex];
            System.out.println("consuming: " + object);
            consumeIndex++;
            if(consumeIndex == size) consumeIndex = 0;
            count--;
            producerCond.signal();
        } finally {
            lock.unlock();
        }
        return object;
    }
}
