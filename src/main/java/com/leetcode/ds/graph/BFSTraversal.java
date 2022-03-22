package com.leetcode.ds.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSTraversal {

    //Directed Graph
    List<Integer>[] graph;

    public BFSTraversal(Integer V){
        graph = new LinkedList[V];
        for(int i=0; i< V; i++){
            graph[i] = new LinkedList<>();
        }
    }

    public void addEdge(int u, int v){
        graph[u].add(v);
    }

    public void BFS(int start){
        Queue<Integer> bfsQueue = new LinkedList<>();
        boolean [] visited = new boolean[graph.length];

        visited[start] = true;
        bfsQueue.offer(start);

        while(!bfsQueue.isEmpty()){
            start = bfsQueue.poll();
            System.out.print(" " + start);

            graph[start].stream().forEach(vertex -> {
                if(!visited[vertex]) {
                    bfsQueue.offer(vertex);
                    visited[vertex] = true;
                }
            });
        }
    }

    public static void main(String args[])
    {
        BFSTraversal g = new BFSTraversal(4);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);

        System.out.println("Following is Breadth First Traversal "+
                "(starting from vertex 2)");

        g.BFS(2);
    }
}
