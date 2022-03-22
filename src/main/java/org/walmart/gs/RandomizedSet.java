package org.walmart.gs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class RandomizedSet {

    Map<Integer, Integer> indexMap;
    List<Integer> dataList;
    Random random;
    /** Initialize your data structure here. */
    public RandomizedSet() {
        indexMap = new HashMap<>();
        dataList = new ArrayList<>();
        random = new Random();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        indexMap.put(val, dataList.size());
        dataList.add(dataList.size(), val);
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if(indexMap.containsKey(val)){
            int index = indexMap.get(val);
            int lastElement = dataList.get(dataList.size()-1);
            if(index != dataList.size() - 1){

                dataList.add(index, lastElement);
            }
            dataList.remove(dataList.size()-1);
            indexMap.put(dataList.get(index), index);
            indexMap.remove(val);
            return true;
        }
        return false;
    }


    /** Get a random element from the set. */
    public int getRandom() {
        return dataList.get(random.nextInt(dataList.size()));
    }

    public static void main(String[] args) {
        RandomizedSet randomizedSet = new RandomizedSet();
        randomizedSet.insert(1);
        randomizedSet.remove(2);
        randomizedSet.insert(2);
        randomizedSet.getRandom();
        randomizedSet.remove(1);
        randomizedSet.insert(2);
        randomizedSet.getRandom();
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
