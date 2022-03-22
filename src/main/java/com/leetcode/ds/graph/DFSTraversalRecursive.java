package com.leetcode.ds.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class DFSTraversalRecursive {

    //Directed Graph
    List<Integer>[] graph;

    public DFSTraversalRecursive(Integer V){
        graph = new LinkedList[V];
        for(int i=0; i< V; i++){
            graph[i] = new LinkedList<>();
        }
    }

    public void addEdge(int u, int v){
        graph[u].add(v);
    }

    public void DFS(int start, boolean [] visited){

        if(visited[start]){
           return;
        }

        visited[start] = true;
        System.out.print(" " + start);

        graph[start].stream().forEach(vertex -> {
                DFS(vertex, visited);
        });

    }

    public static void main(String args[])
    {
        DFSTraversalRecursive g = new DFSTraversalRecursive(4);
        boolean[] visited  = new boolean[4];
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);

        System.out.println("Following is Depth First Traversal "+
                "(starting from vertex 2)");

        g.DFS(2, visited);
    }
}
