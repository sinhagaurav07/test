package org.gs.thread.oddeven;

public class OddEven implements Runnable {

    private boolean isEven;
    private int max;
    private Printer printer;

    public OddEven(Printer printer, boolean isEven, int max) {
        this.isEven = isEven;
        this.max = max;
        this.printer = printer;
    }

    @Override
    public void run(){
        int number = isEven ? 2 : 1;
        while(number <= max) {
            try {
                if(isEven)
                    printer.printEven(number);
                else
                    printer.printOdd(number);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            number = number + 2;
        }
    }
}
