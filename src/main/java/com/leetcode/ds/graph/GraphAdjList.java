package com.leetcode.ds.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphAdjList {

    List<List<Integer>> graph = null;

    public GraphAdjList(int V){
        graph = new ArrayList<>(V);
        for (int i = 0; i < V; i++)
            graph.add(new ArrayList<Integer>());
    }

    public static void main(String[] args) {
        // Creating a graph with 5 vertices
        int V = 5;
        GraphAdjList graph = new GraphAdjList(V);

        // Adding edges one by one
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        graph.printGraphEdges();
    }

    public void addEdge(int u, int v){
        if(u > graph.size() || u < 0 || v < 0) {
            System.out.println("Invalid Edge");
            return;
        }

        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    public void printGraphEdges(){
        for(int i=0; i<graph.size();i++){
            System.out.println("Edges for Node=" + i);
            System.out.print("head ");
            for(int j=0; j< graph.get(i).size();j++){
                    System.out.print(" -> " + graph.get(i).get(j));
            }
            System.out.println();
        }
    }
}
