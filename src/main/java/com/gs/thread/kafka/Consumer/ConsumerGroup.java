package com.gs.thread.kafka.Consumer;

import com.gs.thread.kafka.client.Client;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class ConsumerGroup {

    private int subscribers;
    private ExecutorService executorService;
    private long delay;
    private Client client;
    private ConsumerRequest consumerRequest;
    private Map<String, Boolean> consumerMap;

    public ConsumerGroup(int subscribers, ExecutorService executorService, long delay, Client client, ConsumerRequest consumerRequest) {
        this.subscribers = subscribers;
        this.executorService = executorService;
        this.delay = delay;
        this.client = client;
        this.consumerRequest = consumerRequest;
        this.consumerMap = new HashMap<>();
        init();
    }

    private void init() {
        for (int i = 0; i < subscribers; i++) {
            SimpleConsumer simpleConsumer = new SimpleConsumer(client, consumerRequest, delay);
            if(!consumerMap.containsKey(consumerRequest.getTopic())) {
                executorService.submit(simpleConsumer);
                consumerMap.put(consumerRequest.getTopic(), Boolean.TRUE);
            }

        }
    }


}
