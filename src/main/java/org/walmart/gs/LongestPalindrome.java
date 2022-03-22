package org.walmart.gs;

public class LongestPalindrome {

    public String longestPalindrome(String s) {
        if(s == null || s.length() == 0) return "";
        int length = s.length();
        String longest = s.substring(0, 1);
        for(int i = 0; i < length; i++){
            String s1 = expandAroundCenter(s, i, i);
            String s2 = expandAroundCenter(s, i, i+1);
            longest = findLongest(longest, s1);
            longest = findLongest(longest, s2);
        }
        return longest;
    }

    private String expandAroundCenter(String s, int start, int end){
        while(start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)){
            start--;
            end++;
        }
        return s.substring(start+1, end);
    }

    private String findLongest(String s1, String s2){
        return s1.length() > s2.length() ? s1 : s2;
    }

    public static void main(String[] args) {
        LongestPalindrome longestPalindrome = new LongestPalindrome();
        System.out.println(longestPalindrome.longestPalindrome("abba"));
        //System.out.println(longestPalindrome.longestPalindrome("cabbad"));
    }

    class Employee {
        private String name;


    }
}
