package org.walmart.gs;

public class KEmptySlots {

    public int kEmptySlots(int[] flowers, int k) {

        int res = Integer.MAX_VALUE;
        int[] day = new int[flowers.length + 1];
        for (int i = 0; i < flowers.length; i ++) {
            // day[i] is the day when the flower at position i blooms
            // day[0] is useless here
            day[flowers[i]] = i+1;
        }

        // we now are supposed to find a subarray of day[left, right] where its length is k+2
        // and all i that left < i < right, we have day[i] > day[left] and day[i] > day[right]
        int left = 1, right = k + 2;
        for (int i = 2; right < day.length; i++) {
            if (i == right) {
                // found a sub array
                res = Math.min(res, Math.max(day[left], day[right]));
                left = i;
                right = left + k + 1;
            } else if (day[i] < day[left] || day[i] < day[right]) {
                left = i;
                right = left + k + 1;
            }
        }

        return (res == Integer.MAX_VALUE)?-1:res;
    }

    public static void main(String[] args) {
        KEmptySlots kEmptySlots = new KEmptySlots();
        kEmptySlots.kEmptySlots(new int[]{1, 3, 2}, 1);
    }
}
