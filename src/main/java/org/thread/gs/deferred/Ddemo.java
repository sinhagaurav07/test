package org.thread.gs.deferred;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Ddemo {
    public static void main(String[] args) throws InterruptedException {
        Set<Thread> all = new HashSet<>();
        DeferredCallback deferredCallback = new DeferredCallback();

        Thread t = new Thread(() -> {
            deferredCallback.run();
        });

        t.start();

        for(int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                Callback cb = new Callback(1, "Hello this is " + Thread.currentThread().getName());
                deferredCallback.register(cb);
            });

            thread.setName("Thread_" + (i + 1));
            thread.start();
            all.add(thread);
            Thread.sleep((new Random().nextInt(3) + 1) * 1000);
        }

        all.stream().forEach(th -> {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
