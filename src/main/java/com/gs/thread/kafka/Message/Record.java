package com.gs.thread.kafka.Message;

public class Record {

    private String topic;
    private Message message;

    public Record(String topic, Message message) {
        this.topic = topic;
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Record{" +
                "topic='" + topic + '\'' +
                ", message=" + message.toString() +
                '}';
    }
}
