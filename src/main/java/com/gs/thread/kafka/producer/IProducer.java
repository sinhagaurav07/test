package com.gs.thread.kafka.producer;

public interface IProducer<K, V> {

    void send(ProducerData<K, V> producerData);

}
