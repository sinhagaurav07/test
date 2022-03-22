package org.thread.gs.uberride;

import java.util.HashSet;
import java.util.Set;

public class UberRideDemo {

    public static void main(String[] args) throws InterruptedException {
        UberRide uberRide = new UberRide();
        Set<Thread> allThreads = new HashSet<>();
        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(() -> {
                try {
                    uberRide.seatDemocrats();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.setName("Democrats_" + i);
            allThreads.add(thread);
            Thread.sleep(70);
        }

        for (int i = 0; i < 65; i++) {
            Thread thread = new Thread(() -> {
                try {
                    uberRide.seatRepublicans();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.setName("Republicans_" + i);
            allThreads.add(thread);
            Thread.sleep(20);
        }

        for (Thread thread: allThreads) {
            thread.start();
        }

        for (Thread thread: allThreads) {
            thread.join();
        }
    }
}
