package org.gs.ratelimiter;

import java.util.LinkedList;
import java.util.Queue;


public class Counter {

    private static long interval = 1000L;
    private static int limit = 1;
    private Queue<Long> queue;
    private ClientDetails clientDetails;

    public Counter(String clientId) {
        this(limit, interval, clientId);
    }

    public Counter(int limit, long interval, String clientId) {
        this.queue = new LinkedList<>();
        this.clientDetails = new ClientDetails(limit, interval, clientId);
    }

    public boolean hit(long timestamp) {
        int requestLimit = clientDetails.getLimit();
        long timeInterval = clientDetails.getInterval();

        while (!queue.isEmpty() && Math.abs(queue.peek() - timestamp) >= timeInterval) {
            queue.poll();
        }
        if (queue.size() < requestLimit) {
            queue.add(timestamp);
            return true;
        }
        return false;
    }
}
