package org.gs.ratelimiter;

public class ClientDetails {

    private int limit;
    private long interval;
    private String clientId;

    public ClientDetails(int limit, long interval, String clientId) {
        this.limit = limit;
        this.interval = interval;
        this.clientId = clientId;
    }

    public int getLimit() {
        return limit;
    }

    public long getInterval() {
        return interval;
    }

    public String getClientId() {
        return clientId;
    }
}
