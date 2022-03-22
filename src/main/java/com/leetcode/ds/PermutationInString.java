package com.leetcode.ds;

import java.util.Arrays;

/**
 * Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
 *
 * In other words, return true if one of s1's permutations is the substring of s2.
 *
 *
 *
 * Example 1:
 *
 * Input: s1 = "ab", s2 = "eidbaooo"
 * Output: true
 * Explanation: s2 contains one permutation of s1 ("ba").
 * Example 2:
 *
 * Input: s1 = "ab", s2 = "eidboaoo"
 * Output: false
 *
 *
 * Constraints:
 *
 * 1 <= s1.length, s2.length <= 104
 * s1 and s2 consist of lowercase English letters.
 */
public class PermutationInString {
    public static boolean checkInclusion(String s1, String s2) {
        char[] charArray = s1.toCharArray();
        Arrays.sort(charArray);
        s1 = new String(charArray);
        String temp;
        for(int i=0; i< s2.length()-s1.length()+1;i++){
            charArray = s2.substring(i, i+s1.length()).toCharArray();
            Arrays.sort(charArray);
            temp = new String(charArray);

            if(s1.equals(temp)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(checkInclusion("ab","eidbaooo"));
        System.out.println(checkInclusion("ab","eidboaoo"));
        System.out.println(checkInclusion("adc","dcda"));
    }
}
