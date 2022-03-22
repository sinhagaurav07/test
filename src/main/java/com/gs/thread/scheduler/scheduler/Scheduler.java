package com.gs.thread.scheduler.scheduler;

import com.gs.thread.scheduler.task.Task;

public interface Scheduler<T extends Task> {

    void scheduleTasks(Task task);
}
