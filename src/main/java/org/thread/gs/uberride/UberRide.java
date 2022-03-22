package org.thread.gs.uberride;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UberRide {

    private int democrats;
    private int republicans;

    private Semaphore repSemaphore = new Semaphore(0);
    private Semaphore demoSemaphore = new Semaphore(0);

    private CyclicBarrier barrier = new CyclicBarrier(4);
    private Lock lock = new ReentrantLock();

    public void seatRepublicans() throws InterruptedException, BrokenBarrierException {
        boolean leader = false;
        lock.lock();
        republicans++;
        if(republicans == 4) {
            leader = true;
            repSemaphore.release(3);
            republicans -= 4;
        } else if(republicans == 2 && democrats >= 2) {
            repSemaphore.release(1);
            demoSemaphore.release(2);
            leader = true;
            republicans -= 2;
            democrats -= 2;
        } else {
            lock.unlock();
            repSemaphore.acquire();
        }
        seated();
        barrier.await();

        if(leader){
            drive();
            lock.unlock();
        }
    }

    public void seatDemocrats() throws InterruptedException, BrokenBarrierException {
        boolean leader = false;
        lock.lock();
        democrats++;
        if(democrats == 4) {
            demoSemaphore.release(3);
            leader = true;
            democrats -= 4;
        } else if(republicans >= 2 && democrats == 2) {
            repSemaphore.release(2);
            demoSemaphore.release(1);
            leader = true;
            republicans -= 2;
            democrats -= 2;
        } else {
            lock.unlock();
            demoSemaphore.acquire();
        }
        seated();
        barrier.await();

        if(leader){
            drive();
            lock.unlock();
        }
    }

    private void drive() {
        System.out.println("Driver has started: " + Thread.currentThread().getName());
    }

    private void seated() {
        System.out.println("The person is seated: " + Thread.currentThread().getName());
    }
}
