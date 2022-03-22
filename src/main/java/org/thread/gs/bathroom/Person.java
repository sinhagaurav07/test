package org.thread.gs.bathroom;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Person implements Runnable {

    private final Sex sex;
    private final String name;
    private boolean canLeave;
    private boolean needBathroom;
    private final Bathroom bathroom;

    public Person(String name, Sex sex, Bathroom bathroom) {
        this.name = name;
        this.bathroom = bathroom;
        this.sex = sex;
        this.canLeave = false;
        this.needBathroom = true;
    }

    public void useBathroom() {
        this.bathroom.addUsers(this);
        if (this.bathroom.isUserInBathroom(this)) {
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(1) + 1);
                this.canLeave = true;
                System.out.println(getName() + " done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void leaveBathroom() {
        this.bathroom.removeUsers(this);
        this.canLeave = false;
        this.needBathroom = false;
    }

    public void run() {
        System.out.println(this.getName());
        while(this.needBathroom){
            try {
                Thread.sleep(500);
            } catch(Exception exp){
                exp.printStackTrace();
            }
            if((this.bathroom.getCurrentSex().equals(Sex.NONE) ||
                    this.bathroom.getCurrentSex().equals(this.getSex()))
                    && !this.bathroom.isFull()
                    && !this.bathroom.isUserInBathroom(this)){
                this.useBathroom();
            }

            if(this.canLeave){
                this.leaveBathroom();
            }
        }
    }

    public Sex getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" + "name = " + this.name + ", sex = " + this.sex + '}';
    }
}
