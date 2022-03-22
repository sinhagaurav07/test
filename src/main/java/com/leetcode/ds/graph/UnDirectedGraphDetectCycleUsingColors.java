package com.leetcode.ds.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UnDirectedGraphDetectCycleUsingColors {

    //Directed Graph
    List<Integer>[] graph;
    private static int WHITE = 0, GREY=1, BLACK=2;
    public UnDirectedGraphDetectCycleUsingColors(Integer V){
        graph = new LinkedList[V];
        for(int i=0; i< V; i++){
            graph[i] = new LinkedList<>();
        }
    }

    public void addEdge(int u, int v){
        graph[u].add(v);
    }

    public boolean isCyclic(){
        int[] color = new int[graph.length];

        Arrays.fill(color, WHITE);

        for(int i=0; i<graph.length;i++){
            if(color[i] == WHITE) {
                if (detectCycle(i, color)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean detectCycle(int node, int[] color) {

        color[node] = GREY;

        for(int vertex : graph[node]){
            if(color[vertex] == WHITE) {
                if (detectCycle(vertex, color)) {
                    return true;
                }
            }else if(color[vertex] == GREY){
                return true;
            }
        }
        color[node] = BLACK;
        return false;
    }

    public static void main(String args[])
    {
        UnDirectedGraphDetectCycleUsingColors g1 = new UnDirectedGraphDetectCycleUsingColors(5);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(1, 2);
        g1.addEdge(2, 0);
        g1.addEdge(2, 3);
        g1.addEdge(3, 3);
        if (g1.isCyclic())
            System.out.println("Graph contains cycle");
        else
            System.out.println("Graph doesn't contain cycle");

        UnDirectedGraphDetectCycle g2 = new UnDirectedGraphDetectCycle(3);

        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        if (g2.isCyclic())
            System.out.println("Graph contains cycle");
        else
            System.out.println("Graph doesn't contains cycle");
    }
}
