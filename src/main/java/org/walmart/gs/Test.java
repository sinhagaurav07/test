package org.walmart.gs;

public class Test {

    public static void main(String[] args) {
        String s = "CA|NY";
        String[] split = s.split("\\|");
        System.out.println(split[0]);
    }
}
