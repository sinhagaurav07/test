package org.gs.thread.producer;

public interface BlockingQueue<T> extends Queue<T> {

    void produce(T t) throws InterruptedException;

    T consume() throws InterruptedException;
}
