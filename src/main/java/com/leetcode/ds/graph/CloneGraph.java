package com.leetcode.ds.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CloneGraph {

    public Vertex clone(Vertex node){
        Vertex clone = null, temp = null;
        Queue<Vertex> bfsQueue = new LinkedList<>();
        bfsQueue.offer(node);

        while(!bfsQueue.isEmpty()){
            temp = bfsQueue.poll();
            clone = new Vertex(temp.val, (ArrayList<Vertex>) temp.neighbors);

            temp.neighbors.stream().forEach( nd -> {
                    bfsQueue.offer(nd);
            });
        }
        return clone;
    }


}

class Vertex {
    public int val;
    public List<Vertex> neighbors;
    public Vertex() {
        val = 0;
        neighbors = new ArrayList<>();
    }
    public Vertex(int _val) {
        val = _val;
        neighbors = new ArrayList<Vertex>();
    }
    public Vertex(int _val, ArrayList<Vertex> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}