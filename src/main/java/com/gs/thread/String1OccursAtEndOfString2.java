package com.gs.thread;

import java.util.Arrays;
import java.util.Comparator;

public class String1OccursAtEndOfString2 {

    public static int returnVal(String s){
        String[] strings = s.split(",");
        if(strings.length < 2){
            return 0;
        }
        if(strings[0].endsWith(strings[1])){
            return 1;
        }else {
            return 0;
        }
    }

    public static void main(String[] args) {
        //String line ="Hello world,world";

        //System.out.println(returnVal(line));

        //Runnable r = () -> {System.out.print("Hello");};
//String[] cities = {"Bangalore", "Pune","San Francisco" , "New York city"};

        //MySort ms = new MySort();
        //Arrays.sort(cities,ms);
        //System.out.println(Arrays.binarySearch(cities, "New York city"));
    }
    /*static class MySort implements Comparator<String>
    {
        @Override
        public int compareTo(String a, String b){
            return b.compareTo(a);
        }


    }
    */

}


