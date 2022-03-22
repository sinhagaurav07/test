package org.walmart.gs;

import java.util.PriorityQueue;

public class Median {

    private PriorityQueue<Integer> minHeap;
    private PriorityQueue<Integer> maxHeap;

    public Median() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>((a,b)-> b.compareTo(a));
    }

    public void addNum(int num) {
        maxHeap.add(num);
        minHeap.add(maxHeap.poll());
        if(minHeap.size() > maxHeap.size()){
            maxHeap.add(minHeap.poll());
        }
    }

    public double findMedian() {
        if(maxHeap.size() > minHeap.size()){
            return (double)maxHeap.peek();
        }
        return (minHeap.peek() + maxHeap.peek())/2.0;
    }

    public static void main(String[] args) {
        Median median = new Median();
        median.addNum(2);
        System.out.println(median.findMedian());
        median.addNum(3);
        System.out.println(median.findMedian());
        median.addNum(1);
        System.out.println(median.findMedian());
        median.addNum(4);
        System.out.println(median.findMedian());
        median.addNum(2);
        System.out.println(median.findMedian());
        median.addNum(3);
        System.out.println(median.findMedian());

    }
}
