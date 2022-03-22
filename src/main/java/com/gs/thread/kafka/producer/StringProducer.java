package com.gs.thread.kafka.producer;

import com.gs.thread.kafka.Message.Message;
import com.gs.thread.kafka.Message.Record;
import com.gs.thread.kafka.client.Client;

public class StringProducer implements IStringProducer {

    private Client client;

    public StringProducer(Client client) {
        this.client = client;
    }

    @Override
    public void send(ProducerData<String, String> producerData) {
        String key = producerData.getKey();
        String value = producerData.getValue();
        Message message = new Message(value.getBytes());
        Record producerRecord = new Record(producerData.getTopic(), message);
        client.send(producerRecord);
    }
}
