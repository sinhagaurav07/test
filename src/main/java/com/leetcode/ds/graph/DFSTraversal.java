package com.leetcode.ds.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class DFSTraversal {

    //Directed Graph
    List<Integer>[] graph;

    public DFSTraversal(Integer V){
        graph = new LinkedList[V];
        for(int i=0; i< V; i++){
            graph[i] = new LinkedList<>();
        }
    }

    public void addEdge(int u, int v){
        graph[u].add(v);
    }

    public void DFS(int start){
        Stack<Integer> dfsStack = new Stack<>();
        boolean [] visited = new boolean[graph.length];

        visited[start] = true;
        dfsStack.push(start);

        while(!dfsStack.isEmpty()){
            start = dfsStack.pop();
            System.out.print(" " + start);

            graph[start].stream().forEach(vertex -> {
                if(!visited[vertex]) {
                    dfsStack.push(vertex);
                    visited[vertex] = true;
                }
            });
        }
    }

    public static void main(String args[])
    {
        DFSTraversal g = new DFSTraversal(4);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);

        System.out.println("Following is Depth First Traversal "+
                "(starting from vertex 2)");

        g.DFS(2);
    }
}
