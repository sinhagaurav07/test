package org.gs.thread.producer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueue<T> implements BlockingQueue<T> {

    private int capacity;
    private Lock lock;
    private Condition produceSignal;
    private Condition consumeSignal;
    private Object[] array;
    private int produceIndex = 0;
    private int consumeIndex = 0;
    private int count = 0;

    public ArrayBlockingQueue(int capacity) {
        this.capacity = capacity;
        this.lock = new ReentrantLock();
        this.produceSignal = lock.newCondition();
        this.consumeSignal = lock.newCondition();
        this.array = new Object[capacity];
    }

    @Override
    public void produce(T t) {
        lock.lock();
        try {
            while(count == capacity){
                try {
                    produceSignal.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            array[produceIndex] = t;
            System.out.println(Thread.currentThread().getName() + " producing : " + t.toString());
            produceIndex++;
            if(produceIndex == capacity){
                produceIndex = 0;
            }
            count++;
            consumeSignal.signal();
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public T consume() {
        lock.lock();
        T t = null;
        try {
            while(count == 0){
                try {
                    consumeSignal.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            t = (T)array[consumeIndex];
            System.out.println(Thread.currentThread().getName() + " Consuming:  " + t.toString());
            consumeIndex++;
            if(consumeIndex == capacity){
                consumeIndex = 0;
            }
            count--;
            produceSignal.signal();
        }
        finally {
            lock.unlock();
        }
        return t;
    }
}
