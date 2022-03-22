package com.leetcode.ds.feb;

/**
 *Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
 *
 * A subsequence of a string is a new string that is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).
 *
 *
 *
 * Example 1:
 *
 * Input: s = "abc", t = "ahbgdc"
 * Output: true
 * Example 2:
 *
 * Input: s = "axc", t = "ahbgdc"
 * Output: false
 *
 *
 * Constraints:
 *
 * 0 <= s.length <= 100
 * 0 <= t.length <= 104
 * s and t consist only of lowercase English letters.
 *
 *
 * Follow up: Suppose there are lots of incoming s, say s1, s2, ..., sk where k >= 109, and you want to check one by one to see if t has its subsequence. In this scenario, how would you change your code?
 *
 */
public class Day2IsSubsequence {
    public static boolean isSubsequence(String s, String t) {
        int foundSize = 0;
        for(int i=0, j=0;i<t.length() && j<s.length();i++){
            if(s.charAt(j) == t.charAt(i)){
                j++;
                foundSize++;
            }
        }
        if(foundSize == s.length()){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        //String s = "abc", t = "ahbgdc";
        String s = "axc", t = "ahbgdc";
        System.out.println(isSubsequence(s,t));
    }
}
