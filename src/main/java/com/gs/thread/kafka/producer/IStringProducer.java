package com.gs.thread.kafka.producer;

public interface IStringProducer extends IProducer<String, String> {

    @Override
    void send(ProducerData<String, String> producerData);
}
