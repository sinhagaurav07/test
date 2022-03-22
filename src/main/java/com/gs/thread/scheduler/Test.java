package com.gs.thread.scheduler;

import com.gs.thread.scheduler.scheduler.Scheduler;
import com.gs.thread.scheduler.scheduler.StandAloneTaskScheduler;
import com.gs.thread.scheduler.task.ScheduledTask;
import com.gs.thread.scheduler.task.Task;
import com.gs.thread.scheduler.task.TaskStatus;
import com.gs.thread.scheduler.thread.InMemoryPriorityQueueThreadPool;
import com.gs.thread.scheduler.thread.ThreadPool;

public class Test {

    public static void main(String[] args) {
        ThreadPool pool = new InMemoryPriorityQueueThreadPool(3);
        Scheduler scheduler = new StandAloneTaskScheduler(pool);
        Task task = new ScheduledTask("Task 1", System.currentTimeMillis(), TaskStatus.SUBMITTED, 10);
        scheduler.scheduleTasks(task);
        Task taskTwo = new ScheduledTask("Task 2", System.currentTimeMillis(), TaskStatus.SUBMITTED, 5);
        scheduler.scheduleTasks(taskTwo);
        Task taskThree = new ScheduledTask("Task 3", System.currentTimeMillis(), TaskStatus.SUBMITTED, 1);
        scheduler.scheduleTasks(taskThree);
        Task taskFour = new ScheduledTask("Task 4", System.currentTimeMillis(), TaskStatus.SUBMITTED, 4);
        scheduler.scheduleTasks(taskFour);
    }
}
