package com.gs.thread.scheduler.thread;

import com.gs.thread.scheduler.task.Task;

public interface ThreadPool {

    void executeTasks(Task task, String taskName, int priority);
}
