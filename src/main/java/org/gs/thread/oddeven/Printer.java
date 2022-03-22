package org.gs.thread.oddeven;

public class Printer {

    private volatile boolean isOdd = false;

    public synchronized void printEven(int number) throws InterruptedException {
        while(!isOdd){
            wait();
        }
        System.out.println(number);
        isOdd = false;
        notify();
    }

    public synchronized void printOdd(int number) throws InterruptedException {
        while(isOdd){
            wait();
        }
        System.out.println(number);
        isOdd = true;
        notify();
    }
}
