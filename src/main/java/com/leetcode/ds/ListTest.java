package com.leetcode.ds;

import java.util.ArrayList;
import java.util.Collection;

public class ListTest {

    public static void main(String[] args) {
        Collection<Address> addressList= new ArrayList<>();
        addressList.add(null);

        for(Address a : addressList){
            System.out.println(a.s);
        }
    }

}
class Address {
    String s;
}
