package com.gs.thread.scheduler.task;

public class ScheduledTask extends Task {

    private String taskName;
    private long submittedTime;
    private TaskStatus taskStatus;
    private long taskDuration;

    public ScheduledTask(String taskName, long submittedTime, TaskStatus taskStatus, long taskDuration) {
        this.taskName = taskName;
        this.submittedTime = submittedTime;
        this.taskStatus = taskStatus;
        this.taskDuration = taskDuration;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public long getSubmittedTime() {
        return submittedTime;
    }

    @Override
    public TaskStatus getStatus() {
        return taskStatus;
    }

    @Override
    public long getTaskDuration() {
        return taskDuration;
    }

    @Override
    public int compareTo(Task o) {
        return this.submittedTime == o.getSubmittedTime() ? 0 : this.submittedTime > o.getSubmittedTime() ? -1 : 1;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(taskName);
        System.out.println("Running thread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finishing job : " + taskName);
    }
}
