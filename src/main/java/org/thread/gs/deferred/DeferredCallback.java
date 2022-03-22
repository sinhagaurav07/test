package org.thread.gs.deferred;

import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeferredCallback {

    private PriorityQueue<Callback> pq = new PriorityQueue<>((a, b)-> Math.toIntExact(a.getExecutedAt()) - Math.toIntExact(b.getExecutedAt()));
    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();
    private int lastSeen = 0;
    private long sleepTime = 0;

    public void run() {
        while(true) {
            lock.lock();
            try {
                while (pq.size() == 0) {
                    cond.await();
                }
                if(lastSeen == pq.size()) {
                    cond.await(sleepTime, TimeUnit.MILLISECONDS);
                }
                long time = System.currentTimeMillis();
                while(pq.size() != 0 && time >= pq.peek().getExecutedAt()) {
                    Callback callback = pq.poll();
                    System.out.println("Executed at: " + System.currentTimeMillis() / 1000
                            + " required at: " + callback.getExecutedAt() / 1000
                            + " message: " + callback.getMessage());
                }
                sleepTime = pq.size() == 0 ? 0 : pq.peek().getExecutedAt() - time;
                lastSeen = pq.size();
            } catch(Exception ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void register(Callback callback) {
        lock.lock();
        try {
            pq.add(callback);
            cond.signal();
        } finally {
            lock.unlock();
        }
    }
}
