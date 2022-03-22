package com.gs.thread.kafka.Consumer;

public interface IConsumer<T> {

    T consume(ConsumerRequest consumerRequest);
}
