package org.thread.gs.deferred;

public class Callback {

    private long executedAt;
    private String message;

    public Callback(long executedAt, String message) {
        this.executedAt = System.currentTimeMillis() + executedAt * 1000;
        this.message = message;
    }

    public long getExecutedAt() {
        return executedAt;
    }

    public String getMessage() {
        return message;
    }
}
