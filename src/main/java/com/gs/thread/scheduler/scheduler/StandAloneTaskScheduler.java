package com.gs.thread.scheduler.scheduler;

import com.gs.thread.scheduler.task.ScheduledTask;
import com.gs.thread.scheduler.task.Task;
import com.gs.thread.scheduler.task.TaskStatus;
import com.gs.thread.scheduler.thread.ThreadPool;

public class StandAloneTaskScheduler implements Scheduler {

    private ThreadPool pool;

    public StandAloneTaskScheduler(ThreadPool pool) {
        this.pool = pool;
    }

    @Override
    public void scheduleTasks(Task task) {
        Task scheduledTask = new ScheduledTask(task.getTaskName(), task.getSubmittedTime(), TaskStatus.SUBMITTED, 0);
        pool.executeTasks(scheduledTask, scheduledTask.getTaskName(), 10);
    }
}
