package com.gs.thread.kafka.Consumer;

public class ConsumerRequest {

    private String topic;

    public ConsumerRequest(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
