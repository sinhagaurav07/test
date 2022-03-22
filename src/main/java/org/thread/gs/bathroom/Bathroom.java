package org.thread.gs.bathroom;

import java.util.LinkedHashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bathroom {

    private static final int CAPACITY  = 5;
    private LinkedHashSet<Person> users;
    private int capacity;
    private Sex currentSex;
    private Lock lock = new ReentrantLock();

    private static Bathroom instance = new Bathroom(CAPACITY);

    private Bathroom(int capacity){
        this.capacity = capacity;
        this.currentSex = Sex.NONE;
        this.users = new LinkedHashSet<>();
    }

    public static Bathroom getInstance(){
        return instance;
    }

    public void addUsers(Person person) {
        this.lock.lock();
        try{
            if(isBathroomEmpty()){
                this.currentSex = person.getSex();
            }
            if(!this.isFull() && !this.users.contains(person) && this.currentSex.equals(person.getSex())){
                if(this.users.add(person)){
                    System.out.println(person.getName() + " entered the bathroom");
                }
                if(this.isFull()){
                    System.out.println("Bathroom is full");
                }
            }
        } finally {
            this.lock.unlock();
        }
    }

    public void removeUsers(Person person) {
        this.lock.lock();
        try {
            if (!isBathroomEmpty()) {
                if (this.users.remove(person)) {
                    System.out.println(person.getName() + " left the bathroom");
                }
                if (this.isBathroomEmpty()) {
                    System.out.println("Bathroom is empty");
                    this.currentSex = Sex.NONE;
                }
            }
        }finally {
            this.lock.unlock();
        }
    }

    public boolean isFull(){
        return users.size() == capacity;
    }

    public boolean isBathroomEmpty() {
        return users.isEmpty();
    }

    public boolean isUserInBathroom(Person person){
        return users.contains(person);
    }

    public Sex getCurrentSex() {
        return currentSex;
    }

    @Override
    public String toString() {
        return "Bathroom{" + "currentSex = " + this.currentSex
                + ", capacity = " + this.capacity
                + ", numberOfUsers = " + this.users.size() + '}';
    }
}
