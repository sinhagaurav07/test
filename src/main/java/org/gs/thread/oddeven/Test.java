package org.gs.thread.oddeven;

public class Test {

    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread even = new Thread(new OddEven(printer, true, 10));
        Thread odd = new Thread(new OddEven(printer, false, 10));
        even.start();
        odd.start();
    }
}
