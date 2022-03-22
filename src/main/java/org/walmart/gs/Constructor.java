package org.walmart.gs;

import java.util.Arrays;
import java.util.Comparator;

public class Constructor {
     public static void main(String[] args) {
       String cities[] = {"Bangalore","Pune", "SanFrasisco", "New York City" };
       Mysort ms = new Mysort();
       Arrays.sort(cities, ms);
       System.out.println(Arrays.binarySearch(cities,"SanFrasisco" ));
    }

    static class Mysort implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            return o2.compareTo(o1);
        }
    }
}
