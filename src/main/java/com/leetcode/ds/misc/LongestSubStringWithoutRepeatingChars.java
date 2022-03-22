package com.leetcode.ds.misc;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestSubStringWithoutRepeatingChars {
    public int lengthOfLongestSubstring(String s){
        int maxLength = Integer.MIN_VALUE;
        char[] chars = s.toCharArray();
        Map<Character, Integer> charMap = new HashMap<>();
        int length= -1;
        int i=0; //sliding window start index
        int prevWindow = -1; //end of previous sliding window
        for(int j=0; j<(chars.length - 1) ;j++){
            if(charMap.containsKey(chars[j])){
                length = j-i;
                maxLength = Math.max(maxLength, length);
                prevWindow = charMap.get(chars[j]);
                for(int k=i; k <= prevWindow;k++){
                    charMap.remove(chars[k]);
                }
                i = prevWindow + 1;
            }

            charMap.put(chars[j], j);
        }

        return Math.max(maxLength, chars.length-i-1);
    }

    @Test
    public void lengthOfLongestSubstringTest1(){
        String s = "abcabcbb";
        Assert.assertEquals("Success", 3, lengthOfLongestSubstring(s));

    }
    @Test
    public void lengthOfLongestSubstringTest2(){
        String s = "bbbbb";
        Assert.assertEquals("Success", 1, lengthOfLongestSubstring(s));

    }
    @Test
    public void lengthOfLongestSubstringTest3(){
        String s = "pwwkew";
        Assert.assertEquals("Success", 3, lengthOfLongestSubstring(s));
    }
}
