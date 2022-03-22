package org.thread.gs.bathroom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestBathRoom {
    public static void main(String[] args) {
        Bathroom bathroom = Bathroom.getInstance();
        List<Person> users = new ArrayList<>();
        for (int i = 0; i < ((new Random()).nextInt(10) + 15); i++) {
            if (new Random().nextInt(2) == 0) {
                users.add(new Person(("João" + i), Sex.MALE, bathroom));
            } else {
                users.add(new Person(("Maria" + i), Sex.FEMALE, bathroom));
            }
        }
        users.stream().map((Person) -> new Thread(Person)).forEach((t) -> {
            t.start();
        });
        //
        users.stream().map((Person) -> new Thread(Person)).forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(TestBathRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
