package org.thread.gs.threadpool;

public class FixedThreadPoolExample {
   public static void main(String[] args) {
       FixedThreadPool customThreadPool = new FixedThreadPool(2);

       for (int i = 1; i <= 50; i++) {
           Task task = new Task("Task " + i);
           System.out.println("Created : " + task.getName());

           customThreadPool.submitTasks(task);
       }
   }
}
