package com.gs.thread.scheduler.thread;

import com.gs.thread.scheduler.task.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class InMemoryPriorityQueueThreadPool implements ThreadPool {

    private PriorityBlockingQueue<Task> submittedJobs;
    private Map<String, ScheduledFuture<Task>> activeJobFutures;
    private PoolWorker daemon;
    private ScheduledExecutorService scheduledExecutorService;

    public InMemoryPriorityQueueThreadPool(int threadCapacity) {
        this.submittedJobs = new PriorityBlockingQueue<>();
        this.activeJobFutures = new HashMap<>();
        this.daemon = new PoolWorker();
        this.daemon.start();
        this.scheduledExecutorService = Executors.newScheduledThreadPool(threadCapacity);
    }

    @Override
    public void executeTasks(Task task, String taskName, int priority) {
        synchronized (submittedJobs){
            submittedJobs.add(task);
            submittedJobs.notify();
        }
    }

    private class PoolWorker extends Thread {

        @Override
        public void run() {
            Task task;
            while(true){
                synchronized (submittedJobs) {
                    while(submittedJobs.isEmpty()){
                        try {
                            submittedJobs.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    task = submittedJobs.poll();
                }
                final ScheduledFuture<?> future = scheduledExecutorService.schedule(task, task.getSubmittedTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            }
        }
    }
}
