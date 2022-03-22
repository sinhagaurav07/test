package com.gs.thread.kafka.Consumer;

import com.gs.thread.kafka.Message.Record;
import com.gs.thread.kafka.client.Client;

public class SimpleConsumer implements IConsumer<Record>, Runnable {

    private Client client;
    private ConsumerRequest consumerRequest;
    private long delay;

    public SimpleConsumer(Client client, long delay) {
        this.client = client;
        this.delay = delay;
    }

    public SimpleConsumer(Client client, ConsumerRequest consumerRequest, long delay) {
        this.client = client;
        this.consumerRequest = consumerRequest;
        this.delay = delay;
    }

    @Override
    public Record consume(ConsumerRequest consumerRequest) {
        final Record record = client.receive(consumerRequest.getTopic());
        System.out.println("Receiving message: " + Thread.currentThread().getName() + " ::: " + new String(record.getMessage().getData()));
        return record;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(delay);
                consume(consumerRequest);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
