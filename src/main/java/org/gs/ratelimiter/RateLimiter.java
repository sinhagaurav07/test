package org.gs.ratelimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {

    private Map<String, Counter> counterMap = new ConcurrentHashMap<>();
    private Map<String, ClientDetails> client = new ConcurrentHashMap<>();

    public boolean isAllowed(String clientId) {
        Counter counter = counterMap.get(clientId);
        final long timestamp = System.currentTimeMillis();
        if (counter == null) {
            ClientDetails clientDetails = client.get(clientId);
            if(clientDetails == null)
                counter = new Counter(clientId);
            else
                counter = new Counter(clientDetails.getLimit(), clientDetails.getInterval(), clientId);
            counterMap.put(clientId, counter);
        }
        return counter.hit(timestamp);
    }

    public void setClient(Map<String, ClientDetails> client) {
        this.client = client;
    }
}
