package com.leetcode.ds.misc;

import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

public class StringReverse {

    public static void main(String[] args) {
        String str = "This is a good day";
        System.out.println("Reversed =" + reverse(str));
    }

    public static String reverse(String str){
        char[] chars = str.toCharArray();
        Stack<Character> s = new Stack<>();

        for (int i = 0; i<chars.length ; i++) {
            s.push(chars[i]);
        }
        StringBuffer sbf = new StringBuffer();
        while(!s.empty()){
            sbf.append(s.pop());
        }
        return sbf.toString();
    }

    @Test
    public void testReverse(){
        Assert.assertTrue("Success", "".equals(reverse("")));
        Assert.assertTrue("Success", "avaj".equals(reverse("java")));
        Assert.assertTrue("Success", "AvAj".equals(reverse("jAvA")));
        Assert.assertTrue("Success", "AvAj si siht".equals(reverse("this is jAvA")));
    }

}
