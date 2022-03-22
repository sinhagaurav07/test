package com.gs.thread.kafka.Message;

import java.util.Arrays;

public class Message {

    private byte[] data;

    public Message(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "data=" + data.toString() +
                '}';
    }
}
