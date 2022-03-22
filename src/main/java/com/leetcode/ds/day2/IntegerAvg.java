package com.leetcode.ds.day2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class IntegerAvg {



    public static void main(String[] args) {
        int[] nums = {2,7,11,15};



        //System.out.println(Arrays.stream(nums).sum());
       // System.out.println(Arrays.stream(nums).average());

        List<Integer> intList = new ArrayList<>();
        intList.add(2);intList.add(7);intList.add(11);intList.add(15);
        System.out.println(intList.stream().mapToDouble(i -> i.intValue()).summaryStatistics());
        //System.out.println(intList.stream().mapToDouble(i -> i.intValue()).sum());
        //System.out.println(intList.stream().mapToDouble(i -> i.intValue()).average());

        //System.out.println(intList.stream().mapToDouble(i -> i.intValue()).max());
        //System.out.println(intList.stream().mapToDouble(i -> i.intValue()).min());

        List<String> strList = new ArrayList<>();
        strList.add("abc");strList.add("ram");strList.add("shyam");strList.add("hello world");


        //Function<String, String> upperCase = name -> name.toUpperCase();

        Function<String, String> upperCase = (name) -> {
            return name.toUpperCase();
        };

        Predicate<String> lengthCheck = s -> s.length() > 3;

        /*strList = strList.stream().map(upperCase).collect(Collectors.toList());
       strList.stream().forEach(s -> System.out.println(s));*/

       //strList.stream().map(upperCase).filter(lengthCheck).forEach(s -> System.out.println(s));
        Consumer<String> consume = s -> System.out.println(s.toLowerCase());

        Supplier<String> supplier = () -> {
            List<String> newList = new ArrayList<>();
            newList.add("xyz");newList.add("bad world");
            return newList.toString();
        };



    }
}
