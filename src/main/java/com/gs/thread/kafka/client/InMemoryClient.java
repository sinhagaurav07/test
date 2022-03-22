package com.gs.thread.kafka.client;

import com.gs.thread.kafka.Message.Message;
import com.gs.thread.kafka.Message.Record;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class InMemoryClient implements Client {

    private Map<String, Queue> topicQueueMapping;

    private final Object lock = new Object();

    public InMemoryClient() {
        this.topicQueueMapping = new HashMap<>();
    }

    @Override
    public void send(Record record) {
        String topicName = record.getTopic();
        final Queue<byte[]> queue = topicQueueMapping.getOrDefault(topicName, new ArrayBlockingQueue(50));
        topicQueueMapping.put(topicName, queue);
        System.out.println("Producing Data:: " + new String(record.getMessage().getData()));
        queue.offer(record.getMessage().getData());
    }

    @Override
    public Record receive(String topicName) {
        Queue<byte[]> queue = topicQueueMapping.get(topicName);
        while(queue.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!queue.isEmpty()) {
            byte[] data = queue.poll();
            Message message = new Message(data);
            Record record = new Record(topicName, message);
            return record;
        }
        return null;
    }
}
