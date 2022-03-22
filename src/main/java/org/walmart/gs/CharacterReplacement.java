package org.walmart.gs;

public class CharacterReplacement {

    public int characterReplacement(String s, int k) {
        char[] str = s.toCharArray();
        int[] count = new int[26];
        int maxCount = 0;
        int left = 0;
        int res = 0;
        for (int right = 0; right < s.length(); right++) {
            count[str[right] -'A']++;
            maxCount = Math.max(maxCount, count[str[right]  - 'A']);
            if (right - left + 1 - maxCount <= k) {
                res = Math.max(res, right - left + 1);
            } else {
                count[str[left]  - 'A']--;
                left++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        CharacterReplacement characterReplacement = new CharacterReplacement();
        characterReplacement.characterReplacement("AABABBA", 1);
    }
}
