package com.gs.thread.kafka.client;

import com.gs.thread.kafka.Message.Record;

public interface Client {

    void send(Record producerRecord);

    Record receive(String topicName);

}
