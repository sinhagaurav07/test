package org.walmart.gs.arrays;

import java.util.HashMap;
import java.util.Map;

public class AlienSorted {

    public boolean isAlienSorted(String[] words, String order) {
        Map<Character, Integer> map = new HashMap<>();
        char[] orders = order.toCharArray();
        for(int i = 0; i < orders.length; i++) {
            map.put(orders[i], i);
        }

        search: for(int i = 1; i < words.length; i++) {
            String word1 = words[i-1];
            String word2 = words[i];
            int index = Math.min(word1.length(), word2.length());
            for(int j = 0; j < index; j++){
                int first = map.get(word1.charAt(j));
                int second = map.get(word2.charAt(j));
                if(first != second) {
                    if(first > second) return false;
                    continue search;
                }
            }
            if(word1.length() > word2.length()) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        AlienSorted alienSorted = new AlienSorted();
        System.out.println(alienSorted.isAlienSorted(new String[]{"hello", "hellod"}, "abcdefghijklmnopqrstuvwxyz"));
    }
}
