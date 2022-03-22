package com.leetcode.ds.graph;

import java.util.LinkedList;
import java.util.List;

public class DirectedGraphDetectCycle {

    //Directed Graph
    List<Integer>[] graph;

    public DirectedGraphDetectCycle(Integer V){
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
        boolean[] onPathVisited = new boolean[graph.length];
        
        for(int i=0; i<graph.length;i++){
            if(detectCycle(i, visited, onPathVisited)){
                return true;
            }
        }
        return false;
    }

    private boolean detectCycle(int i, boolean[] visited, boolean[] onPathVisited) {
        if(onPathVisited[i]){
                return true;
        }
        if(visited[i]){
            return false;
        }

        visited[i] = true;
        onPathVisited[i] = true;

        for(int vertex : graph[i]){
            if(detectCycle(vertex, visited, onPathVisited)){
                return true;
            }
        }
        onPathVisited[i] = false;
        return false;
    }

    public static void main(String args[])
    {
        DirectedGraphDetectCycle graph = new DirectedGraphDetectCycle(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(2, 3);
        graph.addEdge(3, 3);

        if(graph.isCyclic())
            System.out.println("Graph contains cycle");
        else
            System.out.println("Graph doesn't "
                    + "contain cycle");
    }
}
