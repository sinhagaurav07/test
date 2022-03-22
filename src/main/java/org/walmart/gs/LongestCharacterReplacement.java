package org.walmart.gs;

public class LongestCharacterReplacement {

    public int characterReplacement(String s, int k) {
        if(s == null || s.length() == 0) return 0;
        int left = 0;
        int[] count = new int[26];
        int maxCount = 0;
        int res = 0;
        for(int right = 0; right < s.length(); right++) {
            count[s.charAt(right)-'A']++;
            maxCount = Math.max(maxCount, count[s.charAt(right) - 'A']);
            if(right - left + 1 - maxCount <= k) {
                res = Math.max(res, right - left + 1);
            } else {
                count[s.charAt(left) - 'A']--;
                left++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        LongestCharacterReplacement longestCharacterReplacement = new LongestCharacterReplacement();
        longestCharacterReplacement.characterReplacement("ABAB", 2);
    }
}
