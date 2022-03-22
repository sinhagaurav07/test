package org;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {

    public static int minSetSize(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < arr.length; i++){
            int count = map.getOrDefault(arr[i], 0);
            map.put(arr[i], count + 1);
        }
        int length = arr.length / 2;
        Integer[] intArray = new Integer[map.size()];
        int index = 0;
        //put values in map
        for(Integer key : map.keySet()){
            intArray[index] = key;
            index++;
        }
        Arrays.sort(intArray, (a, b) -> map.get(b) - map.get(a));
        int totalCount = 0;
        int count = 0;
        for(int i = 0; i < intArray.length; i++){
            if(totalCount < length) {
                totalCount += map.get(intArray[i]);
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] values = {9,77,63,22,92,9,14,54,8,38,18,19,38,68,58,19};
        System.out.println(minSetSize(values));
    }
}
