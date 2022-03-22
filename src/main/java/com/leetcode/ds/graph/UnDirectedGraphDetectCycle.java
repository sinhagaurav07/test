package com.leetcode.ds.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UnDirectedGraphDetectCycle {

    //Directed Graph
    List<Integer>[] graph;

    public UnDirectedGraphDetectCycle(Integer V){
        graph = new LinkedList[V];
        for(int i=0; i< V; i++){
            graph[i] = new LinkedList<>();
        }
    }

    public void addEdge(int u, int v){
        graph[u].add(v);
    }

    public boolean isCyclic(){
        boolean[] visited = new boolean[graph.length];

        Arrays.fill(visited, false);

        for(int i=0; i<graph.length;i++){
            if(!visited[i]) {
                if (detectCycle(i, visited, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean detectCycle(int node, boolean[] visited, int callerNode) {

        visited[node] = true;

        for(int vertex : graph[node]){
            if(!visited[vertex]) {
                if (detectCycle(vertex, visited, node)) {
                    return true;
                }
            }else if(node != callerNode){
                return true;
            }
        }
        return false;
    }

    public static void main(String args[])
    {
        UnDirectedGraphDetectCycle g1 = new UnDirectedGraphDetectCycle(5);
        g1.addEdge(1, 0);
        g1.addEdge(0, 2);
        g1.addEdge(2, 1);
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        if (g1.isCyclic())
            System.out.println("Graph contains cycle");
        else
            System.out.println("Graph doesn't contains cycle");

        UnDirectedGraphDetectCycle g2 = new UnDirectedGraphDetectCycle(3);

        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        if (g2.isCyclic())
            System.out.println("Graph contains cycle");
        else
            System.out.println("Graph doesn't contains cycle");
    }
}
