package com.gs.thread.scheduler.task;

public abstract class Task implements Comparable<Task>, Runnable {

    public abstract String getTaskName();

    public abstract long getSubmittedTime();

    public abstract TaskStatus getStatus();

    public abstract long getTaskDuration();

}
