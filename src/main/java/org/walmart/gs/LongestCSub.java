package org.walmart.gs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LongestCSub {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[128]; // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        LongestCSub longestCSub = new LongestCSub();
        //longestCSub.lengthOfLongestSubstring("abcabcbb");

        Map<String, String> hello = new HashMap<>();
        hello.put("Gaurav", "Sinha");
        hello.put("Tan", "Rajkhowa");
        List<Map.Entry<String, String>> helloList = hello.entrySet().stream().filter(entry -> !entry.getValue().equalsIgnoreCase("Sinha")).collect(Collectors.toList());


    }
}
