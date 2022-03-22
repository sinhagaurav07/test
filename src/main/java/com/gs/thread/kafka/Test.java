package com.gs.thread.kafka;

import com.gs.thread.kafka.Consumer.ConsumerGroup;
import com.gs.thread.kafka.Consumer.ConsumerRequest;
import com.gs.thread.kafka.Consumer.SimpleConsumer;
import com.gs.thread.kafka.client.Client;
import com.gs.thread.kafka.client.InMemoryClient;
import com.gs.thread.kafka.producer.IProducer;
import com.gs.thread.kafka.producer.ProducerData;
import com.gs.thread.kafka.producer.StringProducer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        Client client = new InMemoryClient();
        IProducer<String, String> producer = new StringProducer(client);

        for (int i = 0; i < 10; i++) {
            String topic = "topic1";
            if(i % 2 == 0) {
                topic = "topic2";
            }
            ProducerData<String, String> producerData = new ProducerData<>(topic, "", "Hello ::" + i);
            producer.send(producerData);
        }
        ExecutorService executorServiceEven = Executors.newCachedThreadPool();

        for(int i = 0; i < 3; i++) {
            ConsumerRequest consumerRequest = new ConsumerRequest("topic2");
            SimpleConsumer consumer = new SimpleConsumer(client, consumerRequest, 300);
            executorServiceEven.submit(consumer);
        }

        ExecutorService executorServiceOdd = Executors.newCachedThreadPool();
        ConsumerRequest consumerRequest = new ConsumerRequest("topic1");
        ConsumerGroup consumerGroup = new ConsumerGroup(2, executorServiceOdd, 300, client, consumerRequest);

        for(int i = 0; i < 10; i++) {
            ProducerData<String, String> producerData = new ProducerData<>("topic1", "", "Hello ::" + (i + 10));
            producer.send(producerData);
        }
    }
}
